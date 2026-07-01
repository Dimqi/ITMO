package server;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

import server.comands.CommandSave;
import server.comands.ExecuteCommand;
import server.dataStorage.CommandRegistry;
import server.dataStorage.MovieCollection;
import server.parseToXml.ParseToXml;
import server.dataStorage.DataFactory;
import server.dataStorage.EnumDataFactory;

public class RequestHandler {
    private ObjectInputStream in;
    private ResponseSender responseSender;
    private Map<String, Boolean> additionalInputForCommand;
    CommandSave save = new CommandSave();
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public RequestHandler(SocketChannel clientChannel) throws IOException {
    	
        ObjectOutputStream out = new ObjectOutputStream(clientChannel.socket().getOutputStream());
        in = new ObjectInputStream(clientChannel.socket().getInputStream());
        responseSender = new ResponseSender(out);

        // Отправляем клиенту список команд с дополнительным вводом
        
        additionalInputForCommand = new CommandRegistry().getCommandsWithInputRequirement();
        responseSender.sendResponse(additionalInputForCommand);
        logger.info("Список команд отправлен клиенту.");
        
        DataFactory enumFactory = new EnumDataFactory();
        responseSender.sendResponse(enumFactory.createData());
        logger.info("Фабрика данных отправлена клиенту.");
    }

    public void handleClient() {
        try {
            String fileName = (String) in.readObject();
            File file = new File("Files", fileName);

            if (!file.exists() || file.isDirectory()) {
                responseSender.sendResponse("Ошибка: Файл не найден.");
                logger.warning("Файл не найден: " + fileName);
                return;
            }

            // Загружаем коллекцию фильмов
            loadMoviesFromXML(file.getAbsolutePath());

            // Отправляем клиенту загруженные фильмы
            responseSender.sendResponse(MovieCollection.movies);
            logger.info("Фильмы отправлены клиенту.");
            
            while (true) {
                Object received = in.readObject();
                if (received instanceof Object[]) {
                    Object[] receivedArray = (Object[]) received;

                    if (receivedArray.length != 2 || !(receivedArray[0] instanceof String) || !(receivedArray[1] instanceof Object[])) {
                        responseSender.sendResponse("Ошибка: Некорректный формат команды.");
                        logger.warning("Некорректный формат команды от клиента.");
                        continue;
                    }

                    String commandName = (String) receivedArray[0];
                    Object[] commandArgs = (Object[]) receivedArray[1];

                    System.out.println("Получена команда: " + commandName);
                    logger.info("Получена команда от клиента: " + commandName);
                    
                    // Выполняем команду
                    String response = ExecuteCommand.executeCommand(commandName, commandArgs);
                    logger.info("Ответ на команду отправлен клиенту.");
                    // Отправляем результат
                    responseSender.sendResponse(response);
                } else {
                    System.out.println("Ошибка: получен некорректный объект.");
                    logger.warning("Получен некорректный объект от клиента.");
                }
            }
        } catch (EOFException e) {
            System.out.println("Клиент отключился.");
            logger.info("Клиент отключился.");
            save.command(null);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при обработке клиента: " + e.getMessage());
            logger.severe("Ошибка при обработке клиента: " + e.getMessage());
        }
    }

    private static void loadMoviesFromXML(String fileName) {
        try {
            ParseToXml.loadMoviesFromXML(fileName);
            System.out.println("Коллекция фильмов загружена из файла: " + fileName);
            logger.info("Коллекция фильмов загружена из файла: " + fileName);
        } catch (Exception e) {
            System.out.println("Ошибка загрузки коллекции: " + fileName);
            logger.severe("Ошибка загрузки коллекции из файла: " + fileName + " Ошибка: " + e.getMessage());
        }
    }
}
