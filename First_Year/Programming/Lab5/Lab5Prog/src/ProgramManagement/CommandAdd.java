package ProgramManagement;

import java.time.ZonedDateTime;
import java.util.Map;

import CheckAndTransformation.CheckInput;
import DataStorage.IdGeneratoin;
import DataStorage.Movie;
import DataStorage.*;
import DataStorage.MovieCollection;
/**
 * Класс, реализующий команду добавления нового фильма в коллекцию.
 * Реализует интерфейс {@link CommandWithoutArg}, что означает, что команда не принимает аргументов.
 * 
 * @author Автор класса
 * @version 1.0
 */
public class CommandAdd implements Command<Void> {
	
	/**
     * Метод для выполнения команды добавления фильма.
     * Запрашивает данные у пользователя, создаёт объект фильма и добавляет его в коллекцию.
     */
	@Override
	public void command(Void arg) {
		IdGeneratoin id = new IdGeneratoin();
		CheckInput checkInput =new CheckInput();
		Map<String, Object> copyData = checkInput.checkInput();
		
		Movie movie = new Movie(id.UpdateId(), (String) copyData.get("Name"), (Coordinates) copyData.get("Coordinates"), ZonedDateTime.now(), (int) copyData.get("OscarsCount"), (double) copyData.get("TotalBoxOffice"), (double) copyData.get("UsaBoxOffice"), (MovieGenre) copyData.get("Genre"), (Person) copyData.get("Operator"));
		MovieCollection.movies.add(movie);
		System.out.println("Фильм добавлен в коллекцию");
		
	}

	
}
