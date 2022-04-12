import model.Client;
import model.Car;
import model.GasStation;
import model.Rental;
import repo.inMemoryRepo.InMemoryRepo;
import service.ClientService;
import service.CarService;
import service.GasStationService;
import service.RentalService;
import ui.UI;
import validator.ClientValidator;
import validator.CarValidator;
import validator.GasStationValidator;
import validator.RentalValidator;

public class Main {
    public static void main(String[] args) {
        UI ui = new UI(new CarService(new InMemoryRepo<Integer, Car>(new CarValidator())),
                new ClientService(new InMemoryRepo<Integer, Client>(new ClientValidator())),
                new RentalService(new InMemoryRepo<Integer, Rental>(new RentalValidator())),
                new GasStationService(new InMemoryRepo<Integer, GasStation>(new GasStationValidator())));
        ui.enterMainMenu();
    }
}