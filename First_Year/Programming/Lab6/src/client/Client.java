package client;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.*;

import client.dataInput.DataInput;
import client.dataValidation.CheckData;
import client.dataValidation.CheckInput;
import client.dataValidation.CommandParser;
import client.executeScript.ExecuteScript;
import client.executeScript.FileStack;
import server.dataStorage.Movie;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 2348;
    
    static DataInput dataInput = new DataInput();
    static CheckInput checkInput = new CheckInput();
    public static Map<String, Boolean> additionalInput = new LinkedHashMap<>();
    
    public static String[] colorNames;
    public static String[] countryNames;
    public static String[] genreNames;
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Ошибка: Укажите имя файла для загрузки.");
            return;
        }

        String fileName = args[0];
        CommandParser commandParser = new CommandParser();
        ExecuteScript executeScript = new ExecuteScript();

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {
        	//получение доп ввода
            additionalInput = (Map<String, Boolean>) in.readObject();
            
            //получение данных о enum
            Map<String, String[]> enumData = (Map<String, String[]>) in.readObject();
            colorNames = enumData.get("colors");
            countryNames = enumData.get("countries");
            genreNames = enumData.get("genres");


            out.writeObject(fileName);
            out.flush();

            Object response = in.readObject();

            if (response instanceof String) {
                System.out.println((String) response);
                return;
            }

            Set<Movie> movies = (Set<Movie>) response;
            System.out.println("Коллекция фильмов получена: " + movies);

            while (true) {
                System.out.print("> ");
                String input = dataInput.input();

                if (input.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Завершение работы клиента...");
                    break;
                }

                if (commandParser.parseCommandName(input)[0].equals("execute_script")) {
                    String scriptFileName = commandParser.parseCommandName(input)[1];
                    executeScript.executeScript(scriptFileName, out, in, additionalInput);
                    FileStack.fileStack.clear();
                    continue;
                }

                String[] commandParts = commandParser.parseCommandName(input);
                String commandName = commandParts[0];

                Object[] arg;
                boolean needsAdditionalInput = additionalInput.getOrDefault(commandName, false);
                boolean hasArgument = commandParts.length > 1;

                if (needsAdditionalInput && hasArgument) {
                	Long id =0L;
                	if(CheckData.isInteger(commandParts[1])) {
                		id = Long.parseLong(commandParts[1]);
                	}
                	else {
                		System.out.println("Ошибка: параметр должен быть числом");
                		continue;
                	}
                	
                Map<String, Object> movieData = new LinkedHashMap<>(checkInput.checkInput());
                    
                arg = new Object[]{id, movieData};
                } else if (needsAdditionalInput) {
                    Map<String, Object> movieData = new LinkedHashMap<>(checkInput.checkInput());
                    arg = new Object[]{movieData};
                } else if (hasArgument) {
                	if(CheckData.isInteger(commandParts[1])) {
                		arg = new Object[]{Long.parseLong(commandParts[1])};
                	}
                	else {
                		System.out.println("Ошибка: параметр должен быть числом");
                		continue;
                	}
                } else {
                    arg = new Object[]{};
                }

                out.writeObject(new Object[]{commandName, arg});
                out.flush();

                response = in.readObject();
                System.out.println("Ответ от сервера: " + response);
            }
        } 
        catch (ConnectException e) {
            System.out.println("Сервер занят, подождите немного.");
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}

