package ProgramManagement;

public class CommandExit implements Command<Void> {

	@Override
	public void command(Void arg) {
			System.out.println("работа программы завершена");
			Main.programmWork =false;
		
	}	
	
}
