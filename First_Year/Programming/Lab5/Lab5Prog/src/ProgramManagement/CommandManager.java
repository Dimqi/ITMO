package ProgramManagement;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	/**
     * Карта для хранения команд без аргументов.
     * Ключ — имя команды, значение — лямбда-выражение для выполнения команды.
     */
	 public static final Map<String, Command<?>> commands = new HashMap<>();

	 
	 static CommandHelp help = new CommandHelp();
	 static CommandHistory history = new CommandHistory();
	 static CommandClear clear = new CommandClear();
	 static CommandSave save = new CommandSave();
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
	 static CommandExecuteScript executeScript = new CommandExecuteScript();
	 static CommandExit exit = new CommandExit();
	 /**
	  * Парсер команд для разделения имени команды и её аргументов.
	  */
	 CommandParser commandParser = new CommandParser();
	 static {
		 commands.put("help", help);
		 commands.put("info", info);
		 commands.put("show", show);
		 commands.put("add", add);
		 commands.put("clear", clear);
		 commands.put("save", save);
	     commands.put("execute_script", executeScript);
	     commands.put("exit", exit);
		 commands.put("add_if_max", addIfMax);
		 commands.put("add_if_min", addIfMin); 
		 commands.put("history", history);
		 commands.put("min_by_genre", minByGenre);
		 commands.put("print_ascending", printAscending);
		 commands.put("print_field_descending_operator", printFieldDescendingOperator);
		 commands.put("update", update);
	     commands.put("remove_by_id", removeById);
	 }

	
}
