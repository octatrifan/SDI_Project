import main.java.model.Client;
import main.java.model.Car;
import main.java.model.Rental;
import main.java.repo.inMemoryRepo.InMemoryRepo;
import main.java.service.ClientService;
import main.java.service.CarService;
import main.java.service.RentalService;
import main.java.ui.UI;
import main.java.validator.ClientValidator;
import main.java.validator.CarValidator;
import main.java.validator.RentalValidator;

public class Main {
    public static void main(String[] args) {
        UI ui = new UI(new CarService(new InMemoryRepo<Integer, Car>(new CarValidator())),
                new ClientService(new InMemoryRepo<Integer, Client>(new ClientValidator())),
                new RentalService(new InMemoryRepo<Integer, Rental>(new RentalValidator())));
        ui.enterMainMenu();
    }
}