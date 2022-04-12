package validator;

import exception.ValidatorException;
import model.GasStation;
import model.Rental;

import java.util.Optional;

public class GasStationValidator implements IValidator<GasStation> {
    @Override
    public void validate(GasStation gasStation) throws ValidatorException {
        Optional.of(gasStation.getId())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental!"); });

        Optional.of(gasStation.getGasolinePrice())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental client!"); });

        Optional.of(gasStation.getDieselPrice())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental movie!"); });

        Optional.of(gasStation.getId())
                .filter(x -> x <= 0)
                .ifPresent(s -> { throw new ValidatorException("Error: Found negative ID for rental!"); });
    }
}