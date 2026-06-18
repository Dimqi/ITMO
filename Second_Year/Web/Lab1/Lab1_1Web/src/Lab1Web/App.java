package Lab1Web;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.fastcgi.FCGIInterface;
import com.google.gson.Gson;



public class App {

    static List<String> results = new ArrayList<>();
    static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        System.setProperty("FCGI_PORT", "25311");
        var fcgiInterface = new FCGIInterface();

        while (fcgiInterface.FCGIaccept() >= 0) {
        	
        	
            if (FCGIInterface.request == null || FCGIInterface.request.params == null) {
                sendJson(Map.of("error", "No request params"));
                continue;
            }

            try {
                var method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
                String query = FCGIInterface.request.params.getProperty("QUERY_STRING");

                if (!"GET".equals(method)) {
                    sendJson(Map.of("error", "Ожидался метод GET"));
                    continue;
                }
                
                if (query.startsWith("action")) {
                	if (query.startsWith("action")) {
                	    String numberStr = query.split("=")[1].split("&")[0];
                	    int number = Integer.parseInt(numberStr);
                	    sendNextPage(number);
                	    continue;
                	}
                }
                else {
                	hitRequest(query);
                	continue;
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
                sendJson(Map.of("error", e.getMessage()));
            }
        }
    }

    public static void hitRequest(String query) {
    	long  startTime = System.nanoTime();
    	
    	LocalDateTime requestTime = LocalDateTime.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	String requestTimeForm = requestTime.format(formatter);
    	
    	
    	String xStr = null, yStr = null, rStr = null;
        if (query != null) {
            for (String pair : query.split("&")) {
                String[] kv = pair.split("=");
                if (kv.length == 2) {
                	if ("change_x".equals(kv[0])) {
                	    xStr = kv[1];
                	} else if ("change_y".equals(kv[0])) {
                	    yStr = kv[1];
                	} else if ("change_r".equals(kv[0])) {
                	    rStr = kv[1];
                	}
                }
            }
        }

        double x, r;
        BigDecimal y;
        try {
            x = Double.parseDouble(xStr);
            y = new BigDecimal(yStr);
            r = Double.parseDouble(rStr);
        } catch (NumberFormatException | NullPointerException e) {
            sendJson(Map.of("error", "Ожидались числовые данные"));
            return;
        }

        BigDecimal limitDownY = BigDecimal.valueOf(-5);
        BigDecimal limitUpY = BigDecimal.valueOf(5);

        if (r < 1 || r > 3 || x < -2 || x > 2 || y.compareTo(limitDownY) < 0 || y.compareTo(limitUpY) > 0) {
            sendJson(Map.of("error", "Значения вне допустимого диапазона"));
            return;
        }

        boolean hit = hitRegistration(x, y, r);
        
        long endTime = System.nanoTime();
        double execTimeMs = (endTime - startTime) / 1000000.0;
        
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("x", x);
        resultMap.put("y", y.stripTrailingZeros().toPlainString());     
        resultMap.put("r", r);
        resultMap.put("hit", hit);
        resultMap.put("currentTime", requestTimeForm);
        resultMap.put("execTime",String.format("%.3f", execTimeMs) );
        
        sendJson(resultMap);

    }
    
    private static void sendJson(Map<String, ?> map) {
    	DatabaseManager.saveResult(gson.toJson(map));
    	
        System.out.println("Content-Type: application/json\r\n\r\n");
        System.out.println(gson.toJson(map));
    }


    public static boolean hitRegistration(double x, BigDecimal y, double r) {
        boolean hit = false;

        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal rBD = BigDecimal.valueOf(r);
        BigDecimal minusR = rBD.negate();
        BigDecimal halfR = rBD.divide(BigDecimal.valueOf(2));
        BigDecimal minusHalfR = halfR.negate();
        
        if (x <= 0 && y.compareTo(zero) >= 0 && x >= -r && y.compareTo(halfR) <= 0) hit = true;
        
        if (x <= 0 && y.compareTo(zero) <= 0 && x >= -r && y.compareTo(minusHalfR) >= 0) hit = true;
        	
        BigDecimal xBD = BigDecimal.valueOf(x);
        BigDecimal rSquared = rBD.multiply(rBD);
        BigDecimal sumSquares = xBD.multiply(xBD).add(y.multiply(y));

        if (x >= 0 && y.compareTo(zero) <= 0 && sumSquares.compareTo(rSquared) <= 0) hit = true;

        return hit;
    }
    
    
    public static void sendAllResults() {
        List<String> allJson = DatabaseManager.getAllJson(); 
        
        String jsonArray = gson.toJson(allJson);

        System.out.println("Content-Type: application/json\r\n\r\n");
        System.out.println(jsonArray);
    }
    
    
    public static void sendNextPage(int number) {
        List<String> allJson = DatabaseManager.loadNextPage(number); 
        
        String jsonArray = gson.toJson(allJson);

        System.out.println("Content-Type: application/json\r\n\r\n");
        System.out.println(jsonArray);
    }
    
}

