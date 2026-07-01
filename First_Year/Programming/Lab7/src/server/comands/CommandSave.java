package server.comands;



/**

public class CommandSave extends Command<Void> {



	@Override
	public String command(Void arg) {
		String output ="";
		ParseToXml.saveMoviesToXML("Files/output.xml");
		output += "коллекция сохранена в output.xml";
		return output;

	}
	
}*/
