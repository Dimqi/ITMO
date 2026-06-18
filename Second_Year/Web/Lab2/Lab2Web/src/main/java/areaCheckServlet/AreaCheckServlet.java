package areaCheckServlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servletContext.HitHistoryBean;
import servletContext.ResultBean;

@SuppressWarnings("serial")
@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet {

	private boolean checkHit(double x, BigDecimal y, double r) {
	    boolean hit = false;

	    BigDecimal zero = BigDecimal.ZERO;
	    BigDecimal minusR = BigDecimal.valueOf(-r);
	    BigDecimal halfR = BigDecimal.valueOf(r / 2.0);
	    BigDecimal yBoundaryTriangle = halfR.subtract(BigDecimal.valueOf(x / 2.0));

	    if (x >= 0 && x <= r && y.compareTo(zero) <= 0 && y.compareTo(minusR) >= 0) {
	        hit = true;
	    }

	    if (x >= 0 && x <= r && y.compareTo(zero) >= 0 && y.compareTo(yBoundaryTriangle) <= 0) {
	        hit = true;
	    }


	    BigDecimal xBD = BigDecimal.valueOf(x);
	    BigDecimal x2 = xBD.multiply(xBD);
	    BigDecimal y2 = y.multiply(y);
	    BigDecimal r2 = BigDecimal.valueOf(r * r);

	    if (x <= 0 && x >= -r &&
	        y.compareTo(zero) <= 0 &&
	        y.compareTo(minusR) >= 0 &&
	        x2.add(y2).compareTo(r2) <= 0) {
	        hit = true;
	    }

	    return hit;
	}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    	String xStr = req.getParameter("x");
        String yStr = req.getParameter("y");
        String rStr = req.getParameter("r");
    	
        double x = Double.parseDouble(xStr);
        BigDecimal y = new BigDecimal(yStr);
        double r = Double.parseDouble(rStr);

        boolean result = checkHit(x, y, r);
        
        
        HitHistoryBean history = (HitHistoryBean) req.getSession().getAttribute("hitHistory");
        if (history == null) {
            history = new HitHistoryBean();
            req.getSession().setAttribute("hitHistory", history);
        }
        ResultBean resultBean = new ResultBean(x, y, r, result);
        history.addHit(resultBean);
        
        
        req.setAttribute("x", x);
        req.setAttribute("y", y);
        req.setAttribute("r", r);
        req.setAttribute("result", result);

        req.getRequestDispatcher("/result.jsp").forward(req, resp);
        
    }
}


