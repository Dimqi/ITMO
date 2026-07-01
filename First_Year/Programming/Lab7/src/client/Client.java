package client;

import java.io.*;
import java.net.ConnectException;
import java.util.*;

import client.dataInput.DataInput;
import client.dataStorage.CurrentClient;
import client.dataStorage.DataForMovie;
import client.dataValidation.CheckData;
import client.dataValidation.CheckInput;
import client.dataValidation.CommandParser;
import client.executeScript.ExecuteScript;
import client.executeScript.FileStack;


public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 2348;
    
    static DataInput dataInput = new DataInput();
    static CheckInput checkInput = new CheckInput();
    public static CurrentClient currentClient;
    
    public static void main(String[] args) {



        CommandParser commandParser = new CommandParser();
        ExecuteScript executeScript = new ExecuteScript();

        try {
        	 ClientConnection connection = new ClientConnection();
        	 connection.connect(SERVER_HOST, SERVER_PORT);
        	 ClientRequestSender sender = new ClientRequestSender(connection.getOut());
             ClientResponseReceiver receiver = new ClientResponseReceiver(connection.getIn());
             String input;
             
             while (true) {
            	 System.out.println("Зарегистрируйтесь или авторизуйтесь в системе введите \"join\" или \"register\" ");
            	 input = dataInput.input();
            	 if (input.equalsIgnoreCase("join") || input.equalsIgnoreCase("register")) {
            		    sender.send(input); 
            		    receiver.getResponce(); 

            		    System.out.print("Логин: ");
            		    String login = dataInput.input();

            		    System.out.print("Пароль: ");
            		    String password = dataInput.input();

            		    sender.send(new String[]{login, password}); 
            		    
            		    String responce = (String) receiver.getData();
            		    
            		    if(responce.equals("OK")) {
            		    	System.out.println("Welcome");
            		    	currentClient = new CurrentClient(login, password);
            		    	break;
            		    }
            		    else {
            		    	System.out.println(responce);
            		    }
            		    
            	 }
            	 else {
            		 System.out.println("Введена некорректная команда");
            	 }
            	 
             }
             
             new DataForMovie(receiver);

            

            while (true) {
                System.out.println("> ");
                
                input = dataInput.input();

                if (input.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Завершение работы клиента...");
                    break;
                }

                if (commandParser.parseCommandName(input)[0].equals("execute_script")) {
                    String scriptFileName = commandParser.parseCommandName(input)[1];
                    executeScript.executeScript(scriptFileName, connection.getOut(), connection.getIn(), DataForMovie.additionalInput);
                    FileStack.fileStack.clear();
                    continue;
                }

                String[] commandParts = commandParser.parseCommandName(input);
                String commandName = commandParts[0];

                Object[] arg;
                boolean needsAdditionalInput = DataForMovie.additionalInput.getOrDefault(commandName, false);
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
                
                String login = currentClient.getUserName();
                String password = currentClient.getUserPassword();
                sender.send(new Object[]{commandName, arg, login, password});
                receiver.getResponce();


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

