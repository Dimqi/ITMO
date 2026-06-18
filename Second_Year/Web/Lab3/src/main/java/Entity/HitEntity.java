package Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "results")
public class HitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "x", nullable = false)
	private BigDecimal x;

	@Column(name = "y", nullable = false)
	private BigDecimal y;

	@Column(name = "r", nullable = false)
	private BigDecimal r;

	@Column(name = "hit", nullable = false)
	private boolean hit;
	
    public HitEntity() {
    }
	
	public HitEntity(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.hit = hit;
	}
	
	public int getId() {
	    return id;
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
