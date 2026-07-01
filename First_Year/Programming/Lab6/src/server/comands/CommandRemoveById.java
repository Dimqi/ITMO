package server.comands;

import server.dataStorage.MovieCollection;

/**
 * Класс {@code CommandRemoveById} реализует команду удаления фильма из коллекции по его ID.
 */
public class CommandRemoveById extends Command<Long> {
    /**
     * Удаляет фильм с заданным ID из коллекции.
     *
     * @param id ID фильма, который нужно удалить.
     */
    @Override
    public String command(Long id) {
        boolean removed = MovieCollection.movies.removeIf(movie -> movie.getId() == id);
        return removed ? "фильм удалён из коллекции" : "фильма с id = " + id + " нет в коллекции";
    }
}