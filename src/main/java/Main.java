import model.*;
import repo.XMLRepo.*;
import repo.inMemoryRepo.InMemoryRepo;
import service.*;
import ui.UI;
import validator.*;

import java.util.Date;



public class Main {
    public static void main(String[] args) {
//        var testController = new GasStationService(new GasStationXMLRepo(new GasStationValidator(), "src/main/java/repo/XMLRepo/data/gasstation.xml"));
//        System.out.println(testController.findAll());


//        UI ui = new UI(new CarService(new InMemoryRepo<Integer, Car>(new CarValidator())),
//                new ClientService(new InMemoryRepo<Integer, Client>(new ClientValidator())),
//                new RentalService(new InMemoryRepo<Integer, Rental>(new RentalValidator())),
//                new GasStationService(new InMemoryRepo<Integer, GasStation>(new GasStationValidator())),
//                new RentalFirmService(new InMemoryRepo<Integer, RentalFirm>(new RentalFirmValidator())),
//                new FuelingService(new InMemoryRepo<Integer, Fueling>(new FuelingValidator())),
//                new EmployeeService(new InMemoryRepo<Integer, Employee>(new EmployeeValidator())));

        UI ui = new UI(new SortCarService(new CarXMLRepo(new CarValidator(), "src/main/java/repo/XMLRepo/data/car.xml")),
                new ClientService(new ClientXMLRepo(new ClientValidator(), "src/main/java/repo/XMLRepo/data/client.xml")),
                new RentalService(new RentalXMLRepo(new RentalValidator(), "src/main/java/repo/XMLRepo/data/rental.xml")),
                new GasStationService(new GasStationXMLRepo(new GasStationValidator(), "src/main/java/repo/XMLRepo/data/gasstation.xml")),
                new RentalFirmService(new RentalFirmXMLRepo(new RentalFirmValidator(), "src/main/java/repo/XMLRepo/data/rentalfirm.xml")),
                new FuelingService(new FuelingXMLRepo(new FuelingValidator(), "src/main/java/repo/XMLRepo/data/fueling.xml")),
                new EmployeeService(new EmployeeXMLRepo(new EmployeeValidator(), "src/main/java/repo/XMLRepo/data/employee.xml")));

        ui.enterMainMenu();
    }
}