package validator;

import exception.ValidatorException;
import model.Client;

import java.util.Optional;

/**
 * Client Validator interface.
 *
 * @author Dani
 *
 *
 */

public class ClientValidator implements IValidator <Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        Optional.of(entity.getId())
                .filter(x -> x<=0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for client!"); });

        Optional.of(entity.getFirstName())
                .filter(String::isEmpty)
                .ifPresent(s -> { throw new ValidatorException("Error: Found empty first name for client!"); });

        Optional.of(entity.getLastName())
                .filter(String::isEmpty)
                .ifPresent(s -> { throw new ValidatorException("Error: Found empty last name for client!"); });

        Optional.of(entity.getEmail())
                .filter(x -> x.length() <= 5 || !x.contains("@"))
                .ifPresent(s -> { throw new ValidatorException("Error: Invalid email! (must be more than 5 characters and contain @"); });

//        Optional.of(entity.getBirthDate())
//                .filter(x -> x.getYear() < 1900 || x.getYear() > 2022)
//                .ifPresent(s -> { throw new ValidatorException("Error: Found invalid year for client birthdate!"); });
    }
}
