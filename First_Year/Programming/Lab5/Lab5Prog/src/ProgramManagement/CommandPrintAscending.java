package ProgramManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DataStorage.Movie;
import DataStorage.MovieCollection;
/**
 * Класс {@code CommandPrintAscending} реализует команду вывода фильмов
 * в порядке возрастания по их естественному порядку сортировки.
 */
public class CommandPrintAscending implements Command<Void> {
	/**
     * Выполняет команду сортировки и вывода фильмов в порядке возрастания.
     * Сортировка основана на естественном порядке объектов {@code Movie},
     * что предполагает реализацию интерфейса {@code Comparable<Movie>}.
     */
	@Override
	public void command(Void arg) {
		List<Movie> sortedList = new ArrayList<>(MovieCollection.movies);
	    Collections.sort(sortedList);
	    // Выводим элементы
	    for (Movie movie : sortedList) {
	    	
	        System.out.println(movie.getName());
	    }
	    System.out.println("Вывод фильмов по возрастанию сборов");

	}

}
