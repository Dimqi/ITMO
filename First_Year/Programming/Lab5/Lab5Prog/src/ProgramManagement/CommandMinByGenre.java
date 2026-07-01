package ProgramManagement;

import DataStorage.Movie;
import DataStorage.MovieCollection;
import DataStorage.MovieGenre;

/**
 * Класс {@code CommandMinByGenre} реализует команду поиска фильма с наименьшим жанром в коллекции.
 */
public class CommandMinByGenre implements Command<Void> {
	/**
     * Выполняет команду поиска фильма с наименьшим жанром.
     * Метод проходит по коллекции фильмов, сравнивая жанры по алфавиту, 
     * и находит фильм с минимальным жанром. Если коллекция пуста, выводится сообщение об ошибке.
     */
	@Override
	public void command(Void arg) {
		String minGenre = MovieGenre.values()[0].name();
		
		Movie copyMovie = null;
		for (Movie movie: MovieCollection.movies) {
			if (movie.getGenre()!=null && movie.getGenre().name().compareTo(minGenre) < 0) {
				minGenre = movie.getGenre().name();
				copyMovie = movie;
			}
			
		}
		if (copyMovie != null) {
			System.out.println("фильм с наименьшим жанром");
			System.out.println(copyMovie.toString());
		
		}else {
			System.out.println("ошибка: коллекция пуста");
		}

	}

}
