package server.comands;

import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;
import java.util.Comparator;
import java.util.Optional;

/**
 * Класс {@code CommandMinByGenre} реализует команду поиска фильма с наименьшим жанром в коллекции.
 */
public class CommandMinByGenre extends Command<Void> {

    /**
     * Выполняет команду поиска фильма с наименьшим жанром.
     * Метод проходит по коллекции фильмов, сравнивая жанры по алфавиту, 
     * и находит фильм с минимальным жанром. Если коллекция пуста, выводится сообщение об ошибке.
     * @return 
     */
    @Override
    public String command(Void arg) {
        Optional<Movie> minMovie = MovieCollection.movies.stream()
                .filter(movie -> movie.getGenre() != null)
                .min(Comparator.comparing(movie -> movie.getGenre().name()));

        return minMovie
                .map(movie -> "фильм с наименьшим жанром\n" + movie)
                .orElse("ошибка: коллекция пуста\n");
    }
}
