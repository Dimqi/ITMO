package ProgramManagement;

import CheckAndTransformation.CheckData;

public class ExecuteCommand {
	
	@SuppressWarnings("unchecked")
	public static void executeCommand(String str) {
		if (str == null || str.isEmpty()) {
		    return; 
		}
		
		CommandParser commandParser = new CommandParser();
		String[] parseCommand = commandParser.parseCommandName(str.trim());
		if (parseCommand.length != 0 && !parseCommand[0].isEmpty()) {
			
			Command<?> action = CommandManager.commands.get(parseCommand[0]);
			if(action!= null) {
				if (parseCommand.length == 2) {
					if(CheckData.isLong(parseCommand[1]) ) {
						try {
							((Command<Long>) action).command(Long.parseLong(parseCommand[1]));
						}
						catch(NumberFormatException|ClassCastException e) {
							System.out.println("вы ввели некоректный аргумент для: " +parseCommand[0]);
						}
					}
					else{
						try {
							((Command<String>) action).command(parseCommand[1]);
						}
						catch(ClassCastException e) {
							System.out.println("вы ввели некоректный аргумент для: " +parseCommand[0]);
						}
					}
					
				}
				else if(parseCommand.length >2) {
					System.out.println("вы ввели слишком много аргументов: "+ str);
				}
				else action.command(null);
			}
			else System.out.println("введена неизвестная команда " + str);
			
		}
		
		
	    
	}
}
