package servletContext;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResultBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private double x;
    private BigDecimal y;
    private double r;
    private boolean hit;


    public ResultBean(double x, BigDecimal y, double r, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }


    public double getX() { 
    	return x; 
    }
    
    public BigDecimal getY() { 
    	return y; 
    }
    
    public double getR() { 
    	return r; 
    }
    
    public boolean isHit() { 
    	return hit; 
    }
    
    
}
