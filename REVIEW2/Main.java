import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Bike {
    private int id;
    private boolean isRented;

    public Bike(int id) {
        this.id = id;
        this.isRented = false;
    }

    public int getId() {
        return id;
    }

    public boolean isRented() {
        return isRented;
    }

    public void rentBike() {
        this.isRented = true;
    }

    public void returnBike() {
        this.isRented = false;
    }
}

class Customer {
    private String name;
    private int numberOfBikes;
    private Date rentalStart;
    private Date rentalEnd;

    public Customer(String name, int numberOfBikes, Date rentalStart) {
        this.name = name;
        this.numberOfBikes = numberOfBikes;
        this.rentalStart = rentalStart;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfBikes() {
        return numberOfBikes;
    }

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }
}

class BikeRentalSystem {
    private List<Bike> bikes = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private static final int RATE_PER_DAY = 100;

    public BikeRentalSystem(int totalBikes) {
        for (int i = 1; i <= totalBikes; i++) {
            bikes.add(new Bike(i));
        }
    }

    public void displayAvailableBikes() {
        System.out.println("Available Bikes:");
        for (Bike bike : bikes) {
            if (!bike.isRented()) {
                System.out.println("Bike ID: " + bike.getId());
            }
        }
    }

    public boolean rentBike(String customerName, int numberOfBikes) {
        int availableBikes = 0;
        for (Bike bike : bikes) {
            if (!bike.isRented()) {
                availableBikes++;
            }
        }

        if (numberOfBikes > availableBikes) {
            System.out.println("Not enough bikes available.");
            return false;
        }

        Date rentalStart = new Date();
        Customer customer = new Customer(customerName, numberOfBikes, rentalStart);
        customers.add(customer);

        int rentedBikes = 0;
        for (Bike bike : bikes) {
            if (!bike.isRented() && rentedBikes < numberOfBikes) {
                bike.rentBike();
                rentedBikes++;
            }
        }

        System.out.println("Rented " + numberOfBikes + " bikes to " + customerName);
        return true;
    }

    public double calculateBill(Customer customer) {
        if (customer.getRentalEnd() == null) {
            System.out.println("Rental period not ended.");
            return 0;
        }

        long diffInMillies = customer.getRentalEnd().getTime() - customer.getRentalStart().getTime();
        long rentalDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return rentalDays * RATE_PER_DAY * customer.getNumberOfBikes();
    }

    public void returnBikes(String customerName) {
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(customerName)) {
                customer = c;
                break;
            }
        }

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        Date rentalEnd = new Date();
        customer.setRentalEnd(rentalEnd);

        int returnedBikes = 0;
        for (Bike bike : bikes) {
            if (bike.isRented() && returnedBikes < customer.getNumberOfBikes()) {
                bike.returnBike();
                returnedBikes++;
            }
        }

        double bill = calculateBill(customer);
        System.out.println("Total bill for " + customerName + ": " + bill + " rupees.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BikeRentalSystem rentalSystem = new BikeRentalSystem(10); // Initialize with 10 bikes

        while (true) {
            System.out.println("\n1. Display Available Bikes");
            System.out.println("2. Rent Bikes");
            System.out.println("3. Return Bikes and Generate Bill");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    rentalSystem.displayAvailableBikes();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter number of bikes to rent: ");
                    int numBikes = scanner.nextInt();
                    rentalSystem.rentBike(name, numBikes);
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    String returnName = scanner.nextLine();
                    rentalSystem.returnBikes(returnName);
                    break;
                case 4:
                    System.out.println("Thank you for using the bike rental system!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}