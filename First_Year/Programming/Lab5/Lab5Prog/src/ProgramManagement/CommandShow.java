package ProgramManagement;

import DataStorage.Movie;
import DataStorage.MovieCollection;

/**
 * Класс {@code CommandShow} реализует команду вывода всех элементов коллекции.
 */
public class CommandShow implements Command<Void> {
	/**
     * Выводит строковое представление каждого фильма в коллекции.
     */

	@Override
	public void command(Void arg) {
		System.out.println("строковое представление каждого movie :");
		for(Movie movie : MovieCollection.sortedMovie()) {
			System.out.println(movie.toString());
		}
		
	}

}
