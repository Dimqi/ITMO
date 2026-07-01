package CheckAndTransformation;

import java.util.HashMap;
import java.util.Map;

import DataStorage.Color;
import DataStorage.Coordinates;
import DataStorage.Country;
import DataStorage.Location;
import DataStorage.MovieGenre;
import DataStorage.Person;
import UserInteraction.DataInput;

/**
 * Класс для проверки и обработки пользовательского ввода.
 * Содержит методы для сбора данных о фильме, его координатах, операторе и локации.
 * Данные сохраняются в виде карты (Map), где ключи — это названия полей, а значения — введённые данные.
 */
public class CheckInput {
	DataInput dataInput = new DataInput();
	/**
     * Карта для хранения введённых данных.
     */
	 public static Map<String, Object> data = new HashMap<>();
	 /**
	  * Объект для хранения координат фильма.
	  */
	 Coordinates coordinates = new Coordinates();
	 /**
	  * Объект для хранения локации оператора.
	  */
	 Location location = new Location();
	 /**
	  * Объект для хранения данных об операторе.
	  */
	 Person operator = new Person();
	 
	 Color[] colors = Color.values();
	 Country[] country = Country.values();
	 MovieGenre[] movieGenre = MovieGenre.values();
	 /**
	  * Основной метод для сбора и проверки введённых данных.
	  * Запрашивает у пользователя ввод данных для фильма, его координат, оператора и локации.
	  * Проверяет корректность введённых данных и сохраняет их в карту {@link #data}.
	  * 
	  * @return Карта с введёнными данными
	  */
	public Map<String, Object> checkInput() {
		System.out.println("введите название фильма:");
		
		while(true) {
			System.out.print("> ");
            String inputName = dataInput.input();
            if(inputName == null|| inputName.isEmpty()) {
            	System.out.println("Название не может быть пустым.");
            }
            else {
            	data.put("Name",inputName);
            	break;
            }
            
		}
		System.out.println("введите координаты:");
		System.out.println("введите координату X:");
		while(true) {
			int x;
			System.out.print("> ");
            String inputX = dataInput.input();
            if(CheckData.isInteger(inputX)) {
            	x = Integer.parseInt(inputX);
            	if (x > -754) {
            		coordinates.setX(x);
                    break; 
                } else System.out.println("Ошибка: X должен быть больше -754. Попробуйте снова.");

            } else System.out.println("Ошибка: Введите целое число.");
		}
		
		System.out.println("введите координату Y:");
		while(true) {
			long y;
			System.out.print("> ");
            String inputY = dataInput.input();
            try {
                y = Long.parseLong(inputY);
                coordinates.setY(y);
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите число типа long.");
            }    
		}
		
		data.put("Coordinates", coordinates);
		
		System.out.println("введите колличество оскаров:");
		while(true) {
			int oscarsCount;
			System.out.print("> ");
            String inputOscasrs = dataInput.input();
            if(CheckData.isInteger(inputOscasrs)) {
            	oscarsCount = Integer.parseInt(inputOscasrs);
            	if (oscarsCount > 0) {
            		data.put("OscarsCount", oscarsCount);
                    break; 
                } else System.out.println("Ошибка: значение должено быть больше 0. Попробуйте снова.");
            	
            } else System.out.println("Ошибка: Введите целое число.");

		}
		
		System.out.println("введите общую кассу фильма:");
		while(true) {
			double boxOffice;
			System.out.print("> ");
            String inputTotalBoxOffice = dataInput.input();
            if(CheckData.isDouble(inputTotalBoxOffice)) {
            	boxOffice = Double.parseDouble(inputTotalBoxOffice);
            	if(boxOffice>0) {
            		data.put("TotalBoxOffice", boxOffice);
            		break;
            	}else System.out.println("Ошибка: сумма не может быть отрицательной.");
            	
            }
            else System.out.println("Ошибка: Введите число.");
      
		}
		
		System.out.println("введите сборы фильма в США:");
		while(true) {
			double usaOffice;
			System.out.print("> ");
            String inputUsaBoxOffice = dataInput.input();
            if(inputUsaBoxOffice== null || inputUsaBoxOffice.isEmpty()) {
            	System.out.println("Сборы не могут быть пустыми.");
			}
            else {
            	if(CheckData.isDouble(inputUsaBoxOffice)) {
            		usaOffice = Double.parseDouble(inputUsaBoxOffice);
            		if(usaOffice>0) {
            			data.put("UsaBoxOffice", usaOffice);
            			break;
            		}else System.out.println("Ошибка: сумма не может быть отрицательной.");
            	}
            	else System.out.println("Ошибка: Введите число.");   
            }      
		}
		
		System.out.println("введите жанр фильма из предложенного списка:");
		while(true) {
			MovieGenre genre =null;
			System.out.println("Список жанров: ");
			for(MovieGenre c: MovieGenre.values()) {
				System.out.println((c.ordinal() + 1) + ". " + c.name());
			}
			
			System.out.print("> ");
            String inputGenre = dataInput.input();
            if(CheckData.isInteger(inputGenre)) {
            	try {
            		genre = movieGenre[Integer.parseInt(inputGenre)-1];
            		data.put("Genre", genre); 
                    break;
            	}
            	catch(ArrayIndexOutOfBoundsException e) {
            		System.out.println("выбора с таким нмоером нет в списке");
            		continue;
            	}
            }
            
            for(MovieGenre genre1: MovieGenre.values()) {
            	if (genre1.name().equals(inputGenre.toUpperCase())) {
            		genre = genre1;
            	
            	}
            }
            if (inputGenre == null || inputGenre.trim().isEmpty()) {
                data.put("Genre", null); 
                break;
            }
            
           if(genre == null) {
            	System.out.println("ошибка: введён некоректный жанр");
            }
            else {
            	data.put("Genre", genre);
            	break;
            }
        }
		
		
		System.out.println("введите данные о операторе:");
		System.out.println("введите имя оператора:");
		while(true) {
			
			System.out.print("> ");
            String inputNameOp = dataInput.input();
			try {
				operator.setName(inputNameOp);
				break;
			}
			catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
			
        }
		
		System.out.println("Введите рост оператора:");
		while(true) {
			System.out.print("> ");
            String inputHeightOp = dataInput.input();
			try {
				operator.setHeight(Integer.parseInt(inputHeightOp));
				break;
			}
			catch(IllegalArgumentException e){
				System.out.println("Ошибка: введите число");
			}
			
        }
		
	
		System.out.println("Цвет глаз оператора из списка:");
		while(true) {
			System.out.println("список цветов:");
			for(Color c: Color.values()) {
				System.out.println((c.ordinal() + 1) + ". " + c.name());
			}
			System.out.print("> ");
            String inputEye = dataInput.input();
            if(CheckData.isInteger(inputEye)) {
            	try {
            		operator.setEyeColor(colors[Integer.parseInt(inputEye)-1].name());
                    break;
            	}
            	catch(ArrayIndexOutOfBoundsException e) {
            		System.out.println("выбора с таким нмоером нет в списке");
            	}
            }
            else {
				try {
					operator.setEyeColor(inputEye.toUpperCase());
					break;
				}
				catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
				}
            }
        }
		
		System.out.println("Цвет волос оператора из списка:");
		while(true) {
			System.out.println("список цветов:");
			for(Color c: Color.values()) {
				System.out.println((c.ordinal() + 1) + ". " + c.name());
			}
			System.out.print("> ");
            String inputHair = dataInput.input();
            
            if(CheckData.isInteger(inputHair)) {
            	try {
            		operator.setHairColor(colors[Integer.parseInt(inputHair)-1].name());
                    break;
            	}
            	catch(ArrayIndexOutOfBoundsException e) {
            		System.out.println("выбора с таким нмоером нет в списке");
            	}
            }
            else {
				try {
					operator.setHairColor(inputHair.toUpperCase());
					break;
				}
				catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
				}
            }
        }
		
		System.out.println("Введите национальность оператора из списка:");
		while(true) {
			System.out.println("список национальноестей:");
			for(Country c: Country.values()) {
				System.out.println((c.ordinal() + 1) + ". " + c.name());
			}
			System.out.print("> ");
            String inputNation = dataInput.input();
            
            if(CheckData.isInteger(inputNation)) {
            	try {
            		operator.setNationality(country[Integer.parseInt(inputNation)-1].name());
            		break;
            	}
            	catch(ArrayIndexOutOfBoundsException e) {
            		System.out.println("выбора с таким номером нет в списке");
            	}
            }
            else{
				try {
					operator.setNationality(inputNation.toUpperCase());
					break;
				}
				catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
				}
			}
        }
		
		System.out.println("Введите данные о локации оператора:");
		System.out.println("Введите координату x:");
		while(true) {
			System.out.print("> ");
            String inputX1 = dataInput.input();
            try {
                Integer.parseInt(inputX1);
                location.setX(Integer.parseInt(inputX1));
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
			
        }
		
		System.out.println("Введите координату y:");
		while(true) {
			System.out.print("> ");
            String inputY1 = dataInput.input();
            try {
                Long.parseLong(inputY1);
                location.setY(Long.parseLong(inputY1));
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
			
        }
		
		System.out.println("Введите координату z:");
		while(true) {
			System.out.print("> ");
            String inputZ1 = dataInput.input();
            try {
                Integer.parseInt(inputZ1);
                location.setZ(Integer.parseInt(inputZ1));
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
			
        }
		
		System.out.println("Введите имя:");
		while(true) {
			System.out.print("> ");
            String inputName1 = dataInput.input();
            if(inputName1 == null|| inputName1.isEmpty()) {
            	System.out.println("Название не может быть пустым.");
            }
            else {
            	location.setName(inputName1);;
            	break;
            }
			
        }
		operator.setLocation(location);
		data.put("Operator", operator);
		
		return data;
	}
	
}
