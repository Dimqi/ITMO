package server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.logging.*;

public class Server {
    private static final int PORT = 2348;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public static void main(String[] args) {
        try {
        	setupLogger();
            logger.info("Сервер запускается...");
            
            ConnectionHandler connectionHandler = new ConnectionHandler(PORT);

            while (true) {
                SocketChannel clientChannel = connectionHandler.acceptClient();
                if (clientChannel != null) {
                    System.out.println("Подключился новый клиент.");
                    logger.info("Подключился новый клиент: " + clientChannel.getRemoteAddress());
                    RequestHandler requestHandler = new RequestHandler(clientChannel);
                    requestHandler.handleClient();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void setupLogger() throws IOException {
    	ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Устанавливаем файл для логирования
        FileHandler fileHandler = new FileHandler("Files/server.log", true);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);

        // Устанавливаем формат логов
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

        // Устанавливаем уровень логирования для самого логгера
        logger.setLevel(Level.ALL);
    }
}


