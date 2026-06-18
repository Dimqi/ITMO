package beans;

import java.io.Serializable;
import java.math.BigDecimal;

import Entity.HitEntity;
import database.DatabaseManager;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.CheckArea;

@Named
@SessionScoped
public class PointDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BigDecimal x;
	private BigDecimal y;
	private BigDecimal r;
	private boolean hit;
	
	
    @Inject
    private HitHistoryBean hitHistoryBean;
    
    @Inject
    private DatabaseManager databaseManager;
    
    @PostConstruct
    public void init() {
    	setX(new BigDecimal(0));
        setR(new BigDecimal(1));
        
    }
    
    
	public void check() {
		
		hit = CheckArea.checkHit(x, y, r);
		HitEntity hitEntity = new HitEntity(x, y, r, hit);
		databaseManager.save(hitEntity);
		hitHistoryBean.addHit(hitEntity);
	}
	
	
	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public BigDecimal getR() {
		return r;
	}

	public void setR(BigDecimal r) {
		this.r = r;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
}
