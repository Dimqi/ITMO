package server.comands;

import java.time.ZonedDateTime;
import java.util.Map;

import client.dataValidation.IsValidMovie;
import server.dataStorage.*;

public class CommandAddIfMin extends Command<Map<String, Object>> {
    /**
     * Метод для выполнения команды добавления фильма, если его сборы являются минимальными в коллекции.
     * @return Сообщение о результате выполнения команды.
     */
    @Override
    public String command(Map<String, Object> data) {
        if (data == null) {
            return "Ошибка: данные фильма отсутствуют.";
        }

        // Находим минимальный TotalBoxOffice в коллекции
        double minTotalBoxOffice = MovieCollection.movies.stream()
                .mapToDouble(Movie::getTotalBoxOffice)
                .min()
                .orElse(Double.MAX_VALUE); // Если коллекция пуста, установим максимально возможное значение

        try {
            double newMovieTotalBoxOffice = (double) data.get("TotalBoxOffice");

            if (newMovieTotalBoxOffice < minTotalBoxOffice) {
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

                if (IsValidMovie.isValidMovie(movie)) {
                    MovieCollection.movies.add(movie);
                    return "Фильм успешно добавлен в коллекцию.";
                } else {
                    return "Ошибка: в введённых данных обнаружена ошибка, фильм не добавлен в коллекцию.";
                }
            } else {
                return "Фильм не добавлен, так как его сборы не являются минимальными.";
            }

        } catch (NullPointerException | ClassCastException e) {
            return "Ошибка: некорректные данные фильма.";
        }
    }
}

