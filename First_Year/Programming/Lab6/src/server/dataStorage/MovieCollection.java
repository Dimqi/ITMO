package server.dataStorage;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Класс, представляющий коллекцию фильмов. 
 * Он предоставляет функциональность для хранения фильмов в виде множества, 
 * а также методы для сортировки и получения даты инициализации коллекции.
 */
public class MovieCollection {
	
	public static HashSet<Movie> movies = new HashSet<>();
	private static final LocalDateTime initializationDate = LocalDateTime.now();
	/**
     * Сортирует коллекцию фильмов по общим кассовым сборам в порядке возрастания.
     * 
     * @return Отсортированный список фильмов.
     */
	public static ArrayList<Movie> sortedMovie(){
		ArrayList<Movie> sortedList = new ArrayList<>(movies);
		Collections.sort(sortedList);
		
		return sortedList;
	}
	
	



	public static LocalDateTime getInitializationdate() {
		return initializationDate;
	}
  
	
}
