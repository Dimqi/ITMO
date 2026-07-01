package server.comands;

import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;
import java.util.stream.Collectors;

/**
 * Класс {@code CommandShow} реализует команду вывода всех элементов коллекции.
 */
public class CommandShow extends Command<Void> {
    /**
     * Выводит строковое представление каждого фильма в коллекции.
     */
    @Override
    public String command(Void arg) {
        return "строковое представление каждого movie :\n" +
                MovieCollection.sortedMovie().stream()
                        .map(Movie::toString)
                        .collect(Collectors.joining("\n")) + "\n";
    }
}
