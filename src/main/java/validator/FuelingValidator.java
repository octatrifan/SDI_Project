package validator;

import exception.ValidatorException;
import model.Fueling;

import java.util.Optional;
// TODO

public class FuelingValidator implements IValidator <Fueling>{
    public void validate(Fueling entity) throws ValidatorException {
        Optional.of(entity.getId())
                .filter(x -> x<=0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for fueling!"); });
//        Optional.of(entity.getBirthDate())
//                .filter(x -> x.getYear() < 1900 || x.getYear() > 2022)
//                .ifPresent(s -> { throw new ValidatorException("Error: Found invalid year for client birthdate!"); });
    }
}
