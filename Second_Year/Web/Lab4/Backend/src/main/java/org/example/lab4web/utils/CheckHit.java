package org.example.lab4web.utils;

import java.math.BigDecimal;

public class CheckHit {

    public static boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r){

        boolean hit = false;

        BigDecimal zeroBD = new BigDecimal(0);
        BigDecimal halfR = r.divide(new BigDecimal(2));

        BigDecimal slope = new BigDecimal(2); // наклон
        BigDecimal yBoundaryTriangle = slope.multiply(x).subtract(r);

        BigDecimal x2 = x.pow(2);
        BigDecimal y2 = y.pow(2);
        BigDecimal r2 = r.pow(2);
        BigDecimal halfR2 = halfR.pow(2);


        if(y.compareTo(zeroBD)<=0 && x.compareTo(zeroBD)<=0 && x.compareTo(r.negate())>=0 && y.compareTo(halfR.negate())>=0) {
            hit = true;
        }


        if (y.compareTo(BigDecimal.ZERO) <= 0 && x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(yBoundaryTriangle) >= 0) {
            hit = true;
        }

        if(y.compareTo(zeroBD)>=0 && x.compareTo(zeroBD)>=0 && x2.add(y2).compareTo(r2)<=0) {
            hit = true;
        }

        return hit;
    }

}
