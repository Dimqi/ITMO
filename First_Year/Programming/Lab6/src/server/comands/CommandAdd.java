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
 * Класс, реализующий команду добавления нового фильма в коллекцию.
 */
public class CommandAdd extends Command<Map<String, Object>> {
    
    /**
     * Метод для выполнения команды добавления фильма.
     * @param data Map с данными для создания фильма.
     * @return Результат выполнения команды.
     */
    @Override
    public String command(Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            return "Ошибка: Данные отсутствуют.";
        }
        
        try {
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
                return "Ошибка: В введённых данных обнаружена ошибка, фильм не добавлен.";
            }
        } catch (Exception e) {
            return "Ошибка: Неверные данные. " + e.getMessage();
        }
    }
}
