package server.dataStorage;

import java.util.HashMap;
import java.util.Map;

import server.comands.Command;
import server.comands.CommandAdd;
import server.comands.CommandAddIfMax;
import server.comands.CommandAddIfMin;
import server.comands.CommandClear;
import server.comands.CommandExit;
import server.comands.CommandHelp;
import server.comands.CommandHistory;
import server.comands.CommandInfo;
import server.comands.CommandMinByGenre;
import server.comands.CommandPrintAscending;
import server.comands.CommandPrintFieldDescendingOperator;
import server.comands.CommandRemoveById;
import server.comands.CommandShow;
import server.comands.CommandUpdate;

public class CommandRegistry {
	 public static final Map<String, CommandWrapper> commands = new HashMap<>();
	 static CommandHelp help = new CommandHelp();
	 static CommandHistory history = new CommandHistory();
	 static CommandClear clear = new CommandClear();
	 //static CommandSave save = new CommandSave();
	 static CommandShow show = new CommandShow();
	 static CommandInfo info = new CommandInfo();
	 static CommandAdd add = new CommandAdd();
	 static CommandAddIfMax addIfMax = new CommandAddIfMax();
	 static CommandAddIfMin addIfMin = new CommandAddIfMin();
	 static CommandPrintAscending printAscending = new CommandPrintAscending();
	 static CommandMinByGenre minByGenre = new CommandMinByGenre();
	 static CommandPrintFieldDescendingOperator printFieldDescendingOperator = new CommandPrintFieldDescendingOperator();
	 static CommandRemoveById removeById = new CommandRemoveById();
	 static CommandUpdate update = new CommandUpdate();
	 static CommandExit exit = new CommandExit();
	 
	    static {
	        commands.put("help", new CommandWrapper(help, false));
	        commands.put("info", new CommandWrapper(info, false));
	        commands.put("show", new CommandWrapper(show, false));
	        commands.put("add", new CommandWrapper(add, true));
	        commands.put("clear", new CommandWrapper(clear, false));
	        commands.put("exit", new CommandWrapper(exit, false));
	        commands.put("add_if_max", new CommandWrapper(addIfMax, true));
	        commands.put("add_if_min", new CommandWrapper(addIfMin, true));
	        commands.put("history", new CommandWrapper(history, false));
	        commands.put("min_by_genre", new CommandWrapper(minByGenre, false));
	        commands.put("print_ascending", new CommandWrapper(printAscending, false));
	        commands.put("print_field_descending_operator", new CommandWrapper(printFieldDescendingOperator, false));
	        commands.put("update", new CommandWrapper(update, true));
	        commands.put("remove_by_id", new CommandWrapper(removeById, false));
	    }
	    
	    
	    public Map<String, Boolean> getCommandsWithInputRequirement() {
	        Map<String, Boolean> inputRequirements = new HashMap<>();
//	        for (Map.Entry<String, CommandWrapper> entry : commands.entrySet()) {
//	            inputRequirements.put(entry.getKey(), entry.getValue().requiresInput());
//	        }
	        commands.entrySet()
	        	.stream()
	        	.forEach(command -> inputRequirements.put(command.getKey(), command.getValue().requiresInput()) );
	        return inputRequirements;
	    }
	    
	    
	    public Map<String, Command<?>> getCommands() {
	        Map<String, Command<?>> commandMap = new HashMap<>();
	        for (Map.Entry<String, CommandWrapper> entry : commands.entrySet()) {
	            commandMap.put(entry.getKey(), entry.getValue().getCommand());
	        }
	        return commandMap;
	    }
}
