package DataStorage;

import javax.xml.bind.annotation.*;
/**
 * Класс, представляющий персонажа, с аттрибутами, такими как имя, рост, цвет глаз, цвет волос, национальность и местоположение.
 * Этот класс используется для хранения информации о человеке и может быть сериализован в XML.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    private String name; 
    private int height; 
    @XmlElement(nillable = true)
    private Color eyeColor; 
    @XmlElement(nillable = true)
    private Color hairColor; 
    @XmlElement(nillable = true)
    private Country nationality; 
    @XmlElement(nillable = true)
    private Location location; 
    /**
     * Конструктор по умолчанию.
     * Необходим для создания пустого объекта персонажа.
     */
    public Person() {
    }
    
    /**
     * Получает имя персонажа.
     * 
     * @return имя персонажа.
     */
 	public String getName() {
		return name;
	}
 	/**
     * Устанавливает имя персонажа.
     * 
     * @param name имя персонажа.
     * @throws IllegalArgumentException если имя не указано или пусто.
     */
	public void setName(String name) {
		if(name == null|| name.isEmpty()) {
			throw new IllegalArgumentException("У person должно быть имя");
		}
		this.name = name;
	}
	
	/**
     * Получает рост персонажа.
     * 
     * @return рост персонажа.
     */
	public int getHeight() {
		return height;
	}
	/**
     * Устанавливает рост персонажа.
     * 
     * @param height рост персонажа.
     * @throws IllegalArgumentException если рост меньше или равен нулю.
     */
	public void setHeight(int height) {
		if (height<=0) {
			throw new IllegalArgumentException("Рост не может быть отрицательным!");
		}
		else {
			this.height = height;
		}
	}
	
	/**
     * Получает цвет глаз персонажа.
     * 
     * @return цвет глаз.
     */
	public Color getEyeColor() {
		return eyeColor;
	}
	/**
     * Устанавливает цвет глаз персонажа.
     * 
     * @param eyeColor строковое представление цвета глаз.
     * @throws IllegalArgumentException если введен некорректный цвет.
     */
	public void setEyeColor(String eyeColor) {
		if(eyeColor==null||eyeColor.isEmpty()) {
			this.eyeColor =null;
			return;
		}
		for (Color color: Color.values()) {
			if (color.name().equals(eyeColor)) {
				this.eyeColor = color;
			}
			
		}
		if(this.eyeColor == null) {
			throw new IllegalArgumentException("Введена некорректный цвет");
		}
	}
	
	/**
     * Получает цвет волос персонажа.
     * 
     * @return цвет волос.
     */
	public Color getHairColor() {
		return hairColor;
	}
	/**
     * Устанавливает цвет волос персонажа.
     * 
     * @param hairColor строковое представление цвета волос.
     * @throws IllegalArgumentException если введен некорректный цвет.
     */
	public void setHairColor(String hairColor) {
		if(hairColor==null||hairColor.isEmpty()) {
			this.hairColor =null;
			return;
		}
		for (Color color: Color.values()) {
			if (color.name().equals(hairColor)) {
				this.hairColor = color;
			}
		}
		if(this.hairColor == null) {
			throw new IllegalArgumentException("Введена некорректный цвет");
		}
	}
	
	/**
     * Получает национальность персонажа.
     * 
     * @return национальность.
     */
	public Country getNationality() {
		return nationality;
	}
	/**
     * Устанавливает национальность персонажа.
     * 
     * @param nationality строковое представление страны.
     * @throws IllegalArgumentException если введена некорректная страна.
     */
	public void setNationality(String nationality) {
		for (Country country: Country.values()) {
			if(nationality==null||nationality.isEmpty()) {
				this.nationality =null;
				return;
			}
			if (country.name().equals(nationality)) {
				this.nationality = country;
			}
		}
		if(this.nationality == null) {
			throw new IllegalArgumentException("Введена некорректая страна");
		}

		
	}
	
	/**
     * Получает местоположение персонажа.
     * 
     * @return местоположение персонажа.
     */
	public Location getLocation() {
		return location;
	}
	/**
     * Устанавливает местоположение персонажа.
     * 
     */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
     * Возвращает строковое представление локации оператора.
     * 
     */
	@Override
	public String toString() {
		try {
		return "X= " + location.getX() + " , " + "Y= " + location.getY() + " , " +"Z= " + location.getZ() + " , " + "Name= " + location.getName();
		}
		catch(NullPointerException e) {
			return "";
		}
		
		}
	
	
}
