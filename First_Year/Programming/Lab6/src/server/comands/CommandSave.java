package server.comands;

import server.parseToXml.ParseToXml;

/**
 * Класс {@code CommandSave} реализует команду сохранения коллекции фильмов в XML-файл.
 */
public class CommandSave extends Command<Void> {
	/**
     * Сохраняет текущую коллекцию фильмов в XML-файл "Files/output.xml".
	 * @return 
     */
	@Override
	public String command(Void arg) {
		String output ="";
		ParseToXml.saveMoviesToXML("Files/output.xml");
		output += "коллекция сохранена в output.xml";
		return output;

	}

}
