package server.comands;

import server.dataStorage.MovieCollection;

/**
 * Класс, реализующий команду очистки коллекции фильмов.
 * Реализует интерфейс {@link CommandWithoutArg}, что означает, что команда не принимает аргументов.
 * 
 */
public class CommandClear extends Command<Void> {
	/**
     * Метод для выполнения команды очистки коллекции.
     * Удаляет все элементы из коллекции {@link MovieCollection#movies}.
	 * @return 
     */
	
	@Override
	public String command(Void arg) {
		MovieCollection.movies.clear();
		return "коллекция очищена";

	}

}
