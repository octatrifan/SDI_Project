package validator;

import exception.ValidatorException;
import model.RentalFirm;

import java.util.Optional;

public class RentalFirmValidator implements IValidator<RentalFirm>
{
    @Override
    public void validate(RentalFirm rentalFirm) throws ValidatorException
    {
        Optional.of(rentalFirm.getId())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rentalFirm!"); });

        Optional.of(rentalFirm.getRentalFirmName())
                .filter(String::isEmpty)
                .ifPresent(s -> { throw new ValidatorException("Error: Found invalid name for rentalFirm!"); });

        Optional.of(rentalFirm.getAddress())
                .filter(String::isEmpty)
                .ifPresent(s -> { throw new ValidatorException("Error: Found invalid address for rentalFirm!"); });

        Optional.of(rentalFirm.getAvailableCars())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative number of cars for rentalFirm!"); });
    }
}
