package server.comands;

import server.dataStorage.*;
import client.dataValidation.*;

import java.time.ZonedDateTime;
import java.util.Map;

public class CommandUpdate extends Command<Object[]> {

    @Override
    public String command(Object[] args) {    	 
        if (args.length != 2 || !(args[0] instanceof Number) || !(args[1] instanceof Map)) {
            return "Ошибка: Некорректный формат аргументов для команды.";
        }

        long id = ((Number) args[0]).longValue();
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) args[1];

        // Ищем фильм по ID
        Movie movieToUpdate = MovieCollection.movies.stream()
            .filter(movie -> movie.getId() == id)
            .findFirst()
            .orElse(null);
        

        if (movieToUpdate == null) {
            return "Фильм с ID = " + id + " не найден.";
        }

        try {
        	
        	Coordinates coordinates = new Coordinates((int) data.get("Coordinates_X"), (long) data.get("Coordinates_Y"));
            Location location = new Location((Integer) data.get("Location_X"), (Long) data.get("Location_Y"), (int) data.get("Location_Z"), (String) data.get("Location_Name"));
            Person operator = new Person((String) data.get("Operator_Name"), (int) data.get("Operator_Height"), (Color) Color.valueOf((String) data.get("Operator_Eye")), (Color) Color.valueOf((String)data.get("Operator_Hair")), (Country) Country.valueOf((String) data.get("Operator_Nation")), location);
            Movie updatedMovie = new Movie(
            	movieToUpdate.getId(),
                (String) data.get("Name"),
                coordinates,
                ZonedDateTime.now(),
                (int) data.get("OscarsCount"),
                (double) data.get("TotalBoxOffice"),
                (double) data.get("UsaBoxOffice"),
                (MovieGenre) MovieGenre.valueOf((String) data.get("Genre")),
                operator
            );

            // Проверяем корректность нового фильма
            if (!IsValidMovie.isValidMovie(updatedMovie)) {
                return "Ошибка: Введенные данные фильма некорректны.";
            }

            // Удаляем старый объект и добавляем новый
            MovieCollection.movies.remove(movieToUpdate);
            MovieCollection.movies.add(updatedMovie);

            return "Фильм с ID = " + id + " успешно обновлён.";

        } catch (Exception e) {
            return "Ошибка при обновлении данных фильма: " + e.getMessage();
        }
    }
}

