package validation;

import java.math.BigDecimal;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("rangeValidator")
public class RangeValidator implements Validator<Object> {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {

    	String componentId = component.getId();

        BigDecimal min = new BigDecimal("-5");
        BigDecimal max = new BigDecimal("5");

        BigDecimal number;
        try {
            number = new BigDecimal(value.toString().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new ValidatorException(
                new FacesMessage("Некорректный формат числа")
            );
        }
        
        if (componentId != null && componentId.equals("change_x")) {
            if (number.compareTo(min) < 0 || number.compareTo(max) > 0) {
                throw new ValidatorException(
                    new FacesMessage("X должен быть от -5 до 5")
                );
            }
        } else if (componentId != null && componentId.equals("change_y")) {
            if (number.compareTo(min) < 0 || number.compareTo(max) > 0) {
                throw new ValidatorException(
                    new FacesMessage("Y должен быть от -5 до 5")
                );
            }
        } else if (componentId != null && componentId.equals("change_r")) {
            if (number.compareTo(new BigDecimal(1)) < 0 || number.compareTo(new BigDecimal(4)) > 0) {
                throw new ValidatorException(
                    new FacesMessage("R должен быть от 1 до 4")
                );
            }
        }
    }
}