package ProgramManagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import DataStorage.Movie;
import DataStorage.MovieCollection;
import DataStorage.Person;
/**
 * Класс {@code CommandPrintFieldDescendingOperator} реализует команду вывода всех операторов
 * фильмов в порядке убывания по их имени.
 */
public class CommandPrintFieldDescendingOperator implements Command<Void> {
	/**
     * Выполняет команду сортировки и вывода операторов фильмов в порядке убывания.
     * Если у фильма нет оператора (поле {@code operator} равно {@code null}),
     * он не включается в список.
     */
	@Override
	public void command(Void arg) {
		List<Person> sortedList = new ArrayList<>();	
		
		for(Movie movie: MovieCollection.movies) {
			sortedList.add(movie.getOperator());
		}
		sortedList.sort(Comparator.comparing(Person::getName));
		
		System.out.println("Вывод всех операторов в поряден убывания");
		for(Person operator: sortedList) {
			if(operator!= null) {
				System.out.println(operator.toString());
			}
		}

	}

}
