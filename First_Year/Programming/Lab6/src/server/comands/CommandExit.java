package server.comands;

import client.Client;

public class CommandExit extends Command<Void> {

	@Override
	public String command(Void arg) {
			CommandSave save = new CommandSave();
			save.command(null);
			/*System.out.println("работа программы завершена");
			Client.programmWork =false;*/
			return null;
			
	}	
	
}
