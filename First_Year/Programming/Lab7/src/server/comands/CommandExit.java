package server.comands;

import client.Client;

public class CommandExit extends Command<Void> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
	@Override
	public String command(Void arg, String login, String password) {
		
			//CommandSave save = new CommandSave();
			//save.command(null);
			/*System.out.println("работа программы завершена");
			Client.programmWork =false;*/
			return null;
			
	}	
	
}
