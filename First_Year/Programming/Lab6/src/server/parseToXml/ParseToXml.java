package server.parseToXml;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;

/**
 * Класс для работы с сериализацией и десериализацией коллекции фильмов в формате XML.
 * Предоставляет методы для загрузки и сохранения коллекции фильмов в файл.
 */
public class ParseToXml {
	/**
     * Загружает коллекцию фильмов из XML-файла.
     * 
     * @param fileName имя файла, из которого будет загружена коллекция фильмов.
     */
	public static void loadMoviesFromXML(String fileName) {
	    try (BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(fileName))) {
	        JAXBContext jaxbContext = JAXBContext.newInstance(MovieCollectionWrapper.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

	        MovieCollectionWrapper wrapper = (MovieCollectionWrapper) unmarshaller.unmarshal(fileInputStream);
	        MovieCollection.movies = new HashSet<>(wrapper.getMovies());
	    } catch (JAXBException | IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	 public static void saveMoviesToXML(String fileName) {
	        try {
	            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
	            JAXBContext jaxbContext = JAXBContext.newInstance(MovieCollectionWrapper.class);
	            Marshaller marshaller = jaxbContext.createMarshaller();
	            
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	            MovieCollectionWrapper wrapper = new MovieCollectionWrapper(MovieCollection.movies);
	            marshaller.marshal(wrapper, fileOutputStream);
	        } catch (JAXBException | IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	 
	 
	 // аннотация, что класс является корневым элементом
	    @XmlRootElement
	    // аннотация, что 
	    @XmlAccessorType(XmlAccessType.FIELD)
	    public static class MovieCollectionWrapper {
	        @XmlElement(name = "movie")
	        
	        //(локальная) коллекция фильмов
	        private Set<Movie> movies;
	        
	        // конструктор без параметров для загрузки пустой коллекции HashSet(обязательный при работе с JAXB)
	        public MovieCollectionWrapper() {
	            this.movies = new HashSet<>();
	        }

	        // конструктор для загрузки коллекции
	        public MovieCollectionWrapper(Set<Movie> movies) {
	            this.movies = movies;
	        }
	        
	        public Set<Movie> getMovies() {
	            return movies;
	        }

	        public void setMovies(Set<Movie> movies) {
	            this.movies = movies;
	        }
	    }
	
}
