package controllerServlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String xStr = req.getParameter("x");
        String yStr = req.getParameter("y");
        String rStr = req.getParameter("r");
        
        String errorMessage = null;
        
        try {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            double r = Double.parseDouble(rStr);
            
            if (x < -5 || x > 3 || y < -3 || y > 5 || r < 1 || r > 5) {
                errorMessage = "Значения вне допустимого диапазона";
            } 
        } catch (NumberFormatException e) {
            errorMessage = "Некорректный формат чисел";
        }
        
        if (xStr == null || yStr == null || rStr == null) {
        	errorMessage = "Значения отсутствуют";
        }
        
        
        if (errorMessage != null) {
            req.setAttribute("error", errorMessage);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/areaCheck").forward(req, resp);
        }
        
        
        

    }
}


