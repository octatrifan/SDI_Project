package validator;

import exception.ValidatorException;
import model.Car;

import java.util.Optional;

/**
 * Car Validator interface.
 *
 * @author Dani
 *
 *
 */

public class CarValidator implements IValidator<Car> {
    @Override
    public void validate(Car entity) throws ValidatorException {
        Optional.of(entity.getId())
                .filter(x -> x<=0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for car!"); });

        Optional.of(entity.getModel())
                .filter(String::isEmpty)
                .ifPresent(s -> { throw new ValidatorException("Error: Found empty model for car!"); });

        Optional.of(entity.getBrand())
                .filter(String::isEmpty)
                .ifPresent(s -> { throw new ValidatorException("Error: Found empty brand for car!"); });

        Optional.of(entity.getMakeYear())
                .filter(x -> x < 1900 || x > 2022)
                .ifPresent(s -> { throw new ValidatorException("Error: Found invalid year for car!"); });
    }
}
