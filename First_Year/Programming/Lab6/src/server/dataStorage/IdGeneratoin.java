package server.dataStorage;

public class IdGeneratoin {
	
	private long id;
	
	/**
     * Метод для обновления ID.
     * Генерирует новый ID, основываясь на максимальном ID в коллекции.
     *
     * @return новый уникальный ID
     */
	public long UpdateId() {
		if (MovieCollection.movies.isEmpty()) {
            id = 1;
        } else {
            long maxId = 0;
            for (Movie movie : MovieCollection.movies) {
                if (movie.getId() > maxId) {
                    maxId = movie.getId();
                }
            }
            id = maxId + 1;
        }
        return id;
	}
	
	public long getId() {
		return this.id;
	}
	
	 public void setId(int id) {
		 this.id =id;
	 }
	
}
