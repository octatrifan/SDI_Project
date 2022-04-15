import model.*;
import repo.XMLRepo.CarXMLRepo;
import repo.XMLRepo.ClientXMLRepo;
import repo.XMLRepo.EmployeeXMLRepo;
import repo.inMemoryRepo.InMemoryRepo;
import service.*;
import ui.UI;
import validator.*;

public class Main {
    public static void main(String[] args) {
//        UI ui = new UI(new CarService(new InMemoryRepo<Integer, Car>(new CarValidator())),
//                new ClientService(new InMemoryRepo<Integer, Client>(new ClientValidator())),
//                new RentalService(new InMemoryRepo<Integer, Rental>(new RentalValidator())),
//                new GasStationService(new InMemoryRepo<Integer, GasStation>(new GasStationValidator())),
//                new RentalFirmService(new InMemoryRepo<Integer, RentalFirm>(new RentalFirmValidator())),
//                new FuelingService(new InMemoryRepo<Integer, Fueling>(new FuelingValidator())),
//                new EmployeeService(new InMemoryRepo<Integer, Employee>(new EmployeeValidator())));

        UI ui = new UI( new CarService(new CarXMLRepo(new CarValidator(), "src/main/java/repo/XMLRepo/data/car.xml")),
                new ClientService(new ClientXMLRepo(new ClientValidator(), "src/main/java/repo/XMLRepo/data/client.xml")),
                new RentalService(new InMemoryRepo<Integer, Rental>(new RentalValidator())),
                new GasStationService(new InMemoryRepo<Integer, GasStation>(new GasStationValidator())),
                new RentalFirmService(new InMemoryRepo<Integer, RentalFirm>(new RentalFirmValidator())),
                new FuelingService(new InMemoryRepo<Integer, Fueling>(new FuelingValidator())),
                new EmployeeService(new EmployeeXMLRepo(new EmployeeValidator(), "src/main/java/repo/XMLRepo/data/employee.xml")));

        ui.enterMainMenu();
    }
}