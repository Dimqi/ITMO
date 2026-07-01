package ProgramManagement;

import CheckAndTransformation.ParseToXml;

/**
 * Класс {@code CommandSave} реализует команду сохранения коллекции фильмов в XML-файл.
 */
public class CommandSave implements Command<Void> {
	/**
     * Сохраняет текущую коллекцию фильмов в XML-файл "Files/output.xml".
     */
	@Override
	public void command(Void arg) {
		ParseToXml.saveMoviesToXML("Files/output.xml");
		System.out.println("коллекция сохранена в output.xml");

	}

}
