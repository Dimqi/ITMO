package ProgramManagement;

import DataStorage.MovieCollection;

/**
 * Класс {@code CommandInfo} реализует команду вывода информации о коллекции.
 */
public class CommandInfo implements Command<Void> {
	/**
     * Выполняет команду вывода информации о коллекции.
     * Выводит дату инициализации, тип коллекции и количество элементов.
     */
	@Override
	public void command(Void arg) {
		System.out.println("информация о коллекции:");
		System.out.println("дата инициализации: " + MovieCollection.getInitializationdate());
		System.out.println("тип коллекции :" + MovieCollection.movies.getClass().getSimpleName());
		System.out.println("колличество элементов :" + MovieCollection.movies.size());

	}

}
