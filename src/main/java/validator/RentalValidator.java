package validator;

import exception.ValidatorException;
import model.Rental;

import java.util.Optional;

/**
 * Rental Validator interface.
 *
 * @author Dani
 *
 *
 */

public class RentalValidator implements IValidator<Rental> {
    @Override
    public void validate(Rental rental) throws ValidatorException {
        Optional.of(rental.getId())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental!"); });

        Optional.of(rental.getClientID())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental client!"); });

        Optional.of(rental.getCarID())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental car!"); });

        Optional.of(rental.getId())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental!"); });

        Optional.of(rental.getRentDate())
                .filter(x -> x.after(rental.getDeadlineDate()))
                .ifPresent(s -> { throw new ValidatorException("Error: Rent date must be after deadline date!"); });
    }
}
