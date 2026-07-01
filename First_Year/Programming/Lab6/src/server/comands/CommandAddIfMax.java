package server.comands;

import java.time.ZonedDateTime;
import java.util.Map;

import client.dataValidation.IsValidMovie;
import server.dataStorage.Color;
import server.dataStorage.Coordinates;
import server.dataStorage.Country;
import server.dataStorage.IdGeneratoin;
import server.dataStorage.Location;
import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;
import server.dataStorage.MovieGenre;
import server.dataStorage.Person;

/**
 * Класс, реализующий команду добавления нового фильма в коллекцию, если его сборы являются максимальными.
 */
public class CommandAddIfMax extends Command<Map<String, Object>> {

    @Override
    public String command(Map<String, Object> data) {
        if (data == null) {
            return "Ошибка: данные о фильме отсутствуют.";
        }

        double maxTotalBoxOffice = MovieCollection.movies.stream()
                .mapToDouble(Movie::getTotalBoxOffice)
                .max()
                .orElse(0); // Если коллекция пуста, считаем 0

        try {
            double totalBoxOffice = (double) data.get("TotalBoxOffice");

            if (totalBoxOffice <= maxTotalBoxOffice) {
                return "Ошибка: Сборы фильма не являются максимальными.";
            }

            IdGeneratoin idGen = new IdGeneratoin();
            Coordinates coordinates = new Coordinates((int) data.get("Coordinates_X"), (long) data.get("Coordinates_Y"));
            Location location = new Location((Integer) data.get("Location_X"), (Long) data.get("Location_Y"), (int) data.get("Location_Z"), (String) data.get("Location_Name"));
            Person operator = new Person((String) data.get("Operator_Name"), (int) data.get("Operator_Height"), (Color) Color.valueOf((String) data.get("Operator_Eye")), (Color) Color.valueOf((String)data.get("Operator_Hair")), (Country) Country.valueOf((String) data.get("Operator_Nation")), location);
            Movie movie = new Movie(
                idGen.UpdateId(),
                (String) data.get("Name"),
                coordinates,
                ZonedDateTime.now(),
                (int) data.get("OscarsCount"),
                (double) data.get("TotalBoxOffice"),
                (double) data.get("UsaBoxOffice"),
                (MovieGenre) MovieGenre.valueOf((String) data.get("Genre")),
                operator
            );

            if (!IsValidMovie.isValidMovie(movie)) {
                return "Ошибка: В введённых данных обнаружена ошибка, фильм не добавлен.";
            }

            MovieCollection.movies.add(movie);
            return "Фильм успешно добавлен в коллекцию.";

        } catch (NullPointerException | ClassCastException e) {
            return "Ошибка: Некорректные данные о фильме.";
        }
    }
}

