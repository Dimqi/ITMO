package model;

import java.math.BigDecimal;

public class CheckArea {
	public static boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
		boolean hit = false;
		
		BigDecimal zeroBD = new BigDecimal(0);
		BigDecimal halfR = r.divide(new BigDecimal(2));
		BigDecimal yBoundaryTriangle = halfR.subtract(x.divide(new BigDecimal(2)));
		BigDecimal x2 = x.pow(2);
		BigDecimal y2 = y.pow(2);
		BigDecimal halfR2 = halfR.pow(2);
		
		
		if(y.compareTo(zeroBD)<=0 && x.compareTo(zeroBD)<=0 && x.compareTo(halfR.negate())>=0 && y.compareTo(r.negate())>=0) {
			hit = true;
		}
		
		if(y.compareTo(zeroBD)>=0 && x.compareTo(zeroBD)>=0 && y.compareTo(yBoundaryTriangle)<=0){
			hit = true;
		}
		
		if(y.compareTo(zeroBD)>=0 && x.compareTo(zeroBD)<=0 && x2.add(y2).compareTo(halfR2)<=0) {
			hit = true;
		}
		
		return hit;
	}
}
