package ui;

import model.Car;
import model.Client;
import model.Rental;
import service.ClientService;
import service.CarService;
import service.GasStationService;
import service.RentalService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

abstract class UICommand {
    private final String key;
    private final String description;
    public UICommand(String key, String description) {
        this.key = key;
        this.description = description;
    }
    public abstract void execute();
    public String getKey() {
        return key;
    }
    public String getDescription() {
        return description;
    }
}
class ExitCommand extends UICommand {
    public ExitCommand(String key, String description) {
        super(key, description);
    }
    @Override
    public void execute() {
        System.exit(0);
    }
}
class RunCommand extends UICommand {
    private final Runnable runanble;
    public RunCommand(String key, String description, Runnable runnable) {
        super(key, description);
        this.runanble = runnable;
    }
    @Override
    public void execute() {
        try {
            runanble.run();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


public class UI {
    private final CarService carService;
    private final ClientService clientService;
    private final RentalService rentalService;
    private final GasStationService gasStationService;

    UICommand[] mainCommands = {
            new RunCommand("1", "enter client service", this::enterClientService),
            new RunCommand("2", "enter car service", this::enterCarService),
            new RunCommand("3", "enter rental service", this::enterRentalService),
            new RunCommand("4", "enter gas station service", this::enterRentalService),
    };
    UICommand[] clientCommands = {
            new RunCommand("0", "back", this::enterMainMenu),
            new RunCommand("1", "add", this::addClient),
            new RunCommand("2", "remove", this::removeClient),
            new RunCommand("3", "update", this::updateClient),
            new RunCommand("4", "show all", this::showClients),
    };

    UICommand[] rentalCommands = {
            new RunCommand("0", "back", this::enterMainMenu),
            new RunCommand("1", "add", this::addRental),
            new RunCommand("2", "remove", this::removeRental),
            new RunCommand("3", "update", this::updateRental),
            new RunCommand("4", "show all", this::showRentals),
    };

    UICommand[] carCommands = {
            new RunCommand("0", "back", this::enterMainMenu),
            new RunCommand("1", "add", this::addCar),
            new RunCommand("2", "remove", this::removeCar),
            new RunCommand("3", "update", this::updateCar),
            new RunCommand("4", "show all", this::showCars),
    };

    public void enterClientService() {
        showCommandList(clientCommands);
    }

    public void enterRentalService() {
        showCommandList(rentalCommands);
    }

    public void enterCarService() {
        showCommandList(carCommands);
    }

    public void enterMainMenu() {
        showCommandList(mainCommands);
    }

    int readInt(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        return scanner.nextInt();
    }

    String readString(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        return scanner.nextLine();
    }

    Date readDate(String msg) {
        String str = readString(msg);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            return formatter.parse(str);
        }
        catch (ParseException ex) {
            System.out.println("Invalid Date!");
            return readDate(msg);
        }
    }

    private void addClient() {
        Integer id = readInt("ID:");
        String firstName = readString("First name:");
        String lastName = readString("Last name:");
        String email = readString("Email:");
        Date date = readDate("Birth date:");
        Client client = new Client(firstName, lastName, date, email);
        client.setId(id);
        try {
            this.clientService.save(client);
            System.out.println("Added id");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void removeClient() {
        Integer id = readInt("ID:");
        try {
            this.clientService.delete(id);
            System.out.println("Removed by id");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void updateClient() {
        Integer id = readInt("ID:");
        String firstName = readString("First name:");
        String lastName = readString("Last name:");
        String email = readString("Email:");
        Date date = readDate("Birth date:");
        Client client = new Client(firstName, lastName, date, email);
        client.setId(id);
        try {
            this.clientService.update(client);
            System.out.println("Updated client");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void showClients() {
        System.out.println(StreamSupport.stream(clientService.findAll().spliterator(), false)
                .map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void addRental() {
        Integer id = readInt("ID:");
        Integer CarID = readInt("Car ID:");
        Integer ClientID = readInt("Client ID:");
        Date rentDate = readDate("Rent date:");
        Date deadlineDate = readDate("Deadline date:");
//        Boolean isRented = readBoolean("Is rented: ");
        Rental rental = new Rental(CarID, ClientID, rentDate, deadlineDate, true);
        rental.setId(id);

        this.rentalService.save(rental);
        System.out.println("Added id");
        try {

        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void removeRental() {
        Integer id = readInt("ID:");
        try {
            this.rentalService.delete(id);
            System.out.println("Removed by id");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void updateRental() {
        Integer id = readInt("ID:");
        Integer CarID = readInt("Car ID:");
        Integer ClientID = readInt("Client ID:");
        Date rentDate = readDate("Rent date:");
        Date deadlineDate = readDate("Deadline date:");
//        Boolean isRented = readBoolean("Is rented: ");
        Rental rental = new Rental(CarID, ClientID, rentDate, deadlineDate, true);
        rental.setId(id);
        try {
            this.rentalService.update(rental);
            System.out.println("Added id");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void showRentals() {
        System.out.println(StreamSupport.stream(rentalService.findAll().spliterator(), false)
                .map(Object::toString).collect(Collectors.joining("\n")));
    }

    private void addCar() {
        Integer id = readInt("ID:");
        String brand = readString("Brand:");
        String model = readString("Model:");
        Integer year = readInt("Year:");
        Car car = new Car(brand, model, year);
        car.setId(id);

        this.carService.save(car);
        System.out.println("Added id");
        try {

        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void removeCar() {
        Integer id = readInt("ID:");
        try {
            this.carService.delete(id);
            System.out.println("Removed by id");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void updateCar() {
        Integer id = readInt("ID:");
        String brand = readString("Brand:");
        String model = readString("Model:");
        Integer year = readInt("Year:");
        Car car = new Car(brand, model, year);
        car.setId(id);
        try {
            this.carService.update(car);
            System.out.println("Added id");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }
    private void showCars() {
        System.out.println(StreamSupport.stream(carService.sortByYear().spliterator(), false)
                .map(Object::toString).collect(Collectors.joining("\n")));
    }

    public UI(CarService carService, ClientService clientService, RentalService rentalService, GasStationService gasStationService) {
        this.carService = carService;
        this.clientService = clientService;
        this.rentalService  = rentalService;
        this.gasStationService = gasStationService;
    }

    private void showCommandList(UICommand[] cmdList) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            for (UICommand command : cmdList) {
                String line=String.format("%4s : %s", command.getKey(), command.getDescription());
                System.out.println(line);
            }
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            if (key.equals("0")) return;
            for (UICommand command : cmdList) {
                if (command.getKey().equals(key)) {
                    command.execute();
                }
            }
        }
    }
}
