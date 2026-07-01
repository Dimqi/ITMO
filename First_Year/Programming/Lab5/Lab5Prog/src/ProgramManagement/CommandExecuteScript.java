package ProgramManagement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongConsumer;

import DataStorage.CollectionCommandsInFile;
import DataStorage.FileStack;
/**
 * Класс, реализующий команду выполнения скрипта из файла.
 * Реализует интерфейс {@link CommandWithArgFile}, что означает, что команда принимает аргумент — имя файла.
 * 
 */
public class CommandExecuteScript implements Command<String>{
	 /**
	   * Метод для выполнения команды выполнения скрипта из файла.
	   * Читает команды из файла и выполняет их.
	   * 
	   * @param file Имя файла, из которого будут прочитаны команды.
	   */

	@Override 
	public void command(String file) {
		CollectionCommandsInFile collectionCommandInFile = new CollectionCommandsInFile();

		try {
			
	    	ArrayList<String> fileCommands = collectionCommandInFile.readComands(file);
	    	for(String oldFile: FileStack.fileStack) {
	    		if(file.equals(oldFile)){
	    			System.out.println("команда execute_script не выполнена из-за рекрсии в ваших файлах:");
	    				return;
	    			}
	    		}
	    	FileStack.fileStack.add(file);
	    	
	    	for(String command: fileCommands) {
	    		
	    		
	    		ExecuteCommand.executeCommand(command);
	    	}
		
		}catch(RuntimeException e){
			System.out.println("ошибка: файл не найден");
		}
    	
	}
}
