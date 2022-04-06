package validator;

import exception.ValidatorException;

/**
 * Base Validator interface.
 *
 * @author Dani
 *
 *
 */

public interface IValidator <T> {
    void validate(T entity) throws ValidatorException;
}
