package ProgramManagement;

import DataStorage.MovieCollection;

/**
 * Класс, реализующий команду очистки коллекции фильмов.
 * Реализует интерфейс {@link CommandWithoutArg}, что означает, что команда не принимает аргументов.
 * 
 */
public class CommandClear implements Command<Void> {
	/**
     * Метод для выполнения команды очистки коллекции.
     * Удаляет все элементы из коллекции {@link MovieCollection#movies}.
     */
	
	@Override
	public void command(Void arg) {
		MovieCollection.movies.clear();
		System.out.println("коллекция очищена");

	}

}
