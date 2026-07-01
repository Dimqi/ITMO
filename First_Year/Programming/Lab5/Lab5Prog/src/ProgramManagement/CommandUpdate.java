package ProgramManagement;

import java.util.Map;

import CheckAndTransformation.CheckInput;
import DataStorage.Movie;
import DataStorage.*;
import DataStorage.MovieCollection;
/**
 * Класс {@code CommandUpdate} реализует команду обновления данных фильма по его ID.
 */
public class CommandUpdate implements Command<Long> {
	Movie copyMovie = null;
	/**
     * Обновляет данные фильма в коллекции, если фильм с заданным ID найден.
     * @param id идентификатор фильма, который требуется обновить
     */
	@Override
	public void command(Long id) {
		
		for (Movie item: MovieCollection.movies) {
			if(item.getId() == id) {
				copyMovie = item;
			}
		}
		
		
		if(copyMovie!= null) {
			CheckInput checkInput =new CheckInput();
			Map<String, Object> copyData = checkInput.checkInput();
				copyMovie.setName((String) copyData.get("Name"));
				copyMovie.setCoordinates((Coordinates) copyData.get("Coordinates"));
				copyMovie.setOscarsCount((int) copyData.get("OscarsCount"));
				copyMovie.setTotalBoxOffice((double) copyData.get("TotalBoxOffice"));
				copyMovie.setUsaBoxOffice((double) copyData.get("UsaBoxOffice"));
				copyMovie.setGenre((MovieGenre) copyData.get("Genre"));
				copyMovie.setOperator((Person) copyData.get("Operator"));
			System.out.println("данные о фильме с id = " + id  + " обновлены");
		}
		else {
			System.out.println("фильма с id = " + id + " нет в коллекции");
		}
	}



}
