package ProgramManagement;

import java.util.Iterator;

import DataStorage.Movie;
import DataStorage.MovieCollection;

/**
 * Класс {@code CommandRemoveById} реализует команду удаления фильма из коллекции по его ID.
 */
public class CommandRemoveById implements Command<Long> {

	/**
     * Удаляет фильм с заданным ID из коллекции.
     *
     * @param id ID фильма, который нужно удалить.
     */
	@Override
	public void command(Long id) {
	    Movie copyMovie = null;
	    Iterator<Movie> iterator = MovieCollection.movies.iterator();  // Получаем итератор
	    while (iterator.hasNext()) {
	        Movie item = iterator.next();
	        if (item.getId() == id) {
	            copyMovie = item;
	            iterator.remove();  // Удаляем элемент через итератор
	            System.out.println("фильм удалён из коллекции");
	        }
	    }
	    if (copyMovie == null) {
	        System.out.println("фильма с id = " + id + " нет в коллекции");
	    }
	}

}
