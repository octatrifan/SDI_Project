package ui;

import model.Client;
import model.RentalFirm;
import service.*;

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
    private final RentalFirmService rentalFirmService;

    UICommand[] mainCommands = {
            new ExitCommand("0", "exit"),
            new RunCommand("1", "enter client service", this::enterClientService),
            new RunCommand("2", "enter car service", this::enterMovieService),
            new RunCommand("3", "enter rental service", this::enterRentalService),
            new RunCommand("4", "enter gas station service", this::enterRentalService),
            new RunCommand("5", "enter rental firm service", this::enterRentalFirmService)
    };

    UICommand[] clientCommands = {
            new RunCommand("0", "back", this::enterMainMenu),
            new RunCommand("1", "add", this::addClient),
            new RunCommand("2", "remove", this::removeClient),
            new RunCommand("3", "update", this::updateClient),
            new RunCommand("4", "show all", this::showClients),
    };

    UICommand[] rentalFirmCommands = {
            new RunCommand("0", "back", this::enterMainMenu),
            new RunCommand("1", "add", this::addRentalFirm),
            new RunCommand("2", "remove", this::removeRentalFirm),
            new RunCommand("3", "update", this::updateRentalFirm),
            new RunCommand("4", "show all", this::showRentalFirms),
    };

    public void enterClientService() {
        showCommandList(clientCommands);
    }

    public void enterMovieService() {

    }

    public void enterRentalService() {

    }

    private void enterRentalFirmService()
    {
        showCommandList(rentalFirmCommands);
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
        System.out.println("Removed client");
    }
    private void updateClient() {
        System.out.println("Updated client");
    }
    private void showClients() {
        System.out.println(StreamSupport.stream(clientService.findAll().spliterator(), false)
                .map(Object::toString).collect(Collectors.joining("\n")));
    }

    // RentalFirm UI part

    private void addRentalFirm()
    {
        Integer id = readInt("ID:");
        String name = readString("Name:");
        String address = readString("Address:");
        int noCars = readInt("Number of available cars:");
        RentalFirm rentalFirm = new RentalFirm(name, address, noCars);
        rentalFirm.setId(id);
        try {
            this.rentalFirmService.save(rentalFirm);
            System.out.println("Added rental firm");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }

    private void removeRentalFirm()
    {
        Integer id = readInt("ID:");
        try {
            this.rentalFirmService.delete(id);
            System.out.println("Deleted rental firm");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Rental firm cannot be deleted!");
        }
    }

    private void updateRentalFirm()
    {
        Integer id = readInt("ID:");
        String name = readString("New name:");
        String address = readString("New address:");
        int noCars = readInt("New number of available cars:");
        RentalFirm rentalFirm = new RentalFirm(name, address, noCars);
        rentalFirm.setId(id);
        try {
            this.rentalFirmService.update(rentalFirm);
            System.out.println("Updated rental firm");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Please try again!");
        }
    }

    private void showRentalFirms()
    {
        System.out.println(StreamSupport.stream(rentalFirmService.findAll().spliterator(), false)
                .map(Object::toString).collect(Collectors.joining("\n")));
    }

    public UI(CarService carService, ClientService clientService, RentalService rentalService, GasStationService gasStationService, RentalFirmService rentalFirmService) {
        this.carService = carService;
        this.clientService = clientService;
        this.rentalService  = rentalService;
        this.gasStationService = gasStationService;
        this.rentalFirmService = rentalFirmService;
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
