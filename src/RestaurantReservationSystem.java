import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ReservationSystem {
    private static final int MAX_MEALS = 10;
    private static final int MAX_TABLES = 10;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Menu menu = new Menu();
    private static final List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nRestaurant Reservation System");
            System.out.println("1. Customer Interface");
            System.out.println("2. Staff Interface");
            System.out.println("3. Exit");

            int choice = getUserChoice();

            switch (choice) {
                case 1 -> customerInterface();
                case 2 -> staffInterface();
                case 3 -> {
                    exit = true;
                    System.out.println("Exiting Restaurant Reservation System. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        scanner.close();
    }

    private static void customerInterface() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nCustomer Interface");
            System.out.println("1. View Menu");
            System.out.println("2. Make Reservation");
            System.out.println("3. View Reservation");
            System.out.println("4. Update Reservation");
            System.out.println("5. Cancel Reservation");
            System.out.println("6. Back to Main Menu");

            int choice = getUserChoice();

            switch (choice) {
                case 1 -> menu.displayMenu();
                case 2 -> makeReservation();
                case 3 -> viewCustomerReservations();
                case 4 -> updateReservation();
                case 5 -> cancelReservation();
                case 6 -> exit = true;
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void staffInterface() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nStaff Interface");
            System.out.println("1. Menu Management");
            System.out.println("2. Reservation Management");
            System.out.println("3. Back to Main Menu");

            int choice = getUserChoice();

            switch (choice) {
                case 1 -> menuManagement();
                case 2 -> reservationManagement();
                case 3 -> exit = true;
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void makeReservation() {
        System.out.println("\nMake Reservation");
        System.out.print("Enter your name: ");
        String customerName = scanner.next();

        String reservationDate = getValidInput("Enter the date for the reservation (e.g., DD/MM/YYYY):",
                "\\d{2}/\\d{2}/\\d{4}");

        String reservationTime = getValidInput("Enter the time for the reservation (e.g., HH:mm):", "\\d{2}:\\d{2}");

        Reservation reservation = new Reservation(customerName, reservationDate, reservationTime);

        boolean validMealSelection = false;
        while (!validMealSelection) {
            System.out.print("Enter the number of meals you want to book (1-10): ");
            int numOfMeals = getUserChoice();

            if (numOfMeals >= 1 && numOfMeals <= MAX_MEALS) {
                for (int i = 0; i < numOfMeals; i++) {
                    menu.displayMenu();
                    String mealName = getValidInput("Enter the name of meal " + (i + 1) + ":",
                            "[A-Za-z]+");

                    MenuItem selectedMeal = findMenuItem(mealName);
                    if (selectedMeal != null) {
                        reservation.addMeal(selectedMeal);
                    } else {
                        System.out.println("Invalid meal name. Please select from the fixed menu.");
                        i--; // Decrement i to re-enter the current meal selection
                    }
                }
                validMealSelection = true;
            } else {
                System.out.println("Invalid number of meals. Please select between 1 and " + MAX_MEALS + " meals.");
            }
        }

        reservations.add(reservation);
        System.out.println("Reservation successful!");
        displayReservationDetails(reservation);
    }

    private static void viewCustomerReservations() {
        System.out.println("\nView Reservation");
        if (reservations.isEmpty()) {
            System.out.println("No reservations available.");
        } else {
            for (int i = 0; i < reservations.size(); i++) {
                System.out.println("Reservation " + (i + 1) + ":");
                displayReservationDetails1(reservations.get(i));
            }
        }
    }

    private static void updateReservation() {
        System.out.println("\nUpdate Reservation");
        if (reservations.isEmpty()) {
            System.out.println("No reservations available to update.");
            return;
        }

        System.out.print("Enter the reservation number to update: ");
        int reservationNumber = getUserChoice();

        if (reservationNumber < 1 || reservationNumber > reservations.size()) {
            System.out.println("Invalid reservation number. Please enter a valid reservation number.");
            return;
        }

        Reservation reservationToUpdate = reservations.get(reservationNumber - 1);
        displayReservationDetails(reservationToUpdate);

        String newName = getValidInput("Enter new name (press Enter to keep the existing name):", "[A-Za-z]*");
        String newDate = getValidInput("Enter new date (press Enter to keep the existing date):", "\\d{2}/\\d{2}/\\d{4}");
        String newTime = getValidInput("Enter new time (press Enter to keep the existing time):", "\\d{2}:\\d{2}");

        if (!newName.isEmpty()) {
            reservationToUpdate.setCustomerName(newName);
        }
        if (!newDate.isEmpty()) {
            reservationToUpdate.setDate(newDate);
        }
        if (!newTime.isEmpty()) {
            reservationToUpdate.setTime(newTime);
        }

        System.out.println("Reservation updated successfully!");
        displayReservationDetails(reservationToUpdate);
    }

    private static void cancelReservation() {
        System.out.println("\nCancel Reservation");
        if (reservations.isEmpty()) {
            System.out.println("No reservations available to cancel.");
            return;
        }

        System.out.print("Enter the reservation number to cancel: ");
        int reservationNumber = getUserChoice();

        if (reservationNumber < 1 || reservationNumber > reservations.size()) {
            System.out.println("Invalid reservation number. Please enter a valid reservation number.");
            return;
        }

        Reservation canceledReservation = reservations.remove(reservationNumber - 1);
        System.out.println("Reservation canceled successfully!");
        displayReservationDetails(canceledReservation);
    }

    private static void menuManagement() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMenu Management");
            System.out.println("1. Add MenuItem");
            System.out.println("2. Edit Menu");
            System.out.println("3. Delete Menu");
            System.out.println("4. View Menu");
            System.out.println("5. Search menu items by name");
            System.out.println("6. Back to Staff Interface");

            int choice = getUserChoice();

            switch (choice) {
                case 1 -> addMenuItem();
                case 2 -> editMenu();
                case 3 -> deleteMenu();
                case 4 -> menu.displayMenu();
                case 5 -> searchMenuItemByName();
                case 6 -> exit = true;
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void reservationManagement() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nReservation Management");
            System.out.println("1. Edit Reservation");
            System.out.println("2. Delete Reservation");
            System.out.println("3. View Reservation");
            System.out.println("4. Search Reservation by customer name");
            System.out.println("5. Back to Staff Interface");

            int choice = getUserChoice();

            switch (choice) {
                case 1 -> editReservation();
                case 2 -> deleteReservation();
                case 3 -> viewStaffReservations();
                case 4 -> searchReservationByCustomerName();
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addMenuItem() {
        System.out.println("\nAdd MenuItem");
        if (menu.getFixedMenu().size() < MAX_MEALS) {
            String newMealName = getValidInput("Enter the name of the new meal:", "[A-Za-z]+");
            double newMealPrice = getValidDoubleInput("Enter the price of the new meal:");

            menu.getFixedMenu().add(new MenuItem(newMealName, newMealPrice));
            System.out.println("New meal added successfully!");
        } else {
            System.out.println("Cannot add more items. Maximum menu items reached.");
        }
    }

    private static void editMenu() {
        System.out.println("\nEdit Menu");
        menu.displayMenu();

        String mealToEdit = getValidInput("Enter the name of the meal to edit:", "[A-Za-z]+");

        MenuItem existingMeal = findMenuItem(mealToEdit);
        if (existingMeal != null) {
            double newPrice = getValidDoubleInput("Enter the new price for " + existingMeal.getName() + ":");
            existingMeal.setPrice(newPrice);
            System.out.println("Menu item updated successfully!");
        } else {
            System.out.println("Meal not found in the menu.");
        }
    }


    private static void deleteMenu() {
        System.out.println("\nDelete Menu");
        menu.displayMenu();

        String mealToDelete = getValidInput("Enter the name of the meal to delete:", "[A-Za-z]+");

        MenuItem mealToRemove = findMenuItem(mealToDelete);
        if (mealToRemove != null) {
            menu.getFixedMenu().remove(mealToRemove);
            System.out.println("Menu item deleted successfully!");
        } else {
            System.out.println("Meal not found in the menu.");
        }
    }

    private static void searchMenuItemByName() {
        System.out.println("\nSearch Menu Items by Name");
        String searchQuery = getValidInput("Enter the name to search for:", "[A-Za-z]+");

        List<MenuItem> searchResults = new ArrayList<>();
        for (MenuItem menuItem : menu.getFixedMenu()) {
            if (menuItem.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(menuItem);
            }
        }

        if (!searchResults.isEmpty()) {
            System.out.println("Search Results:");
            for (MenuItem item : searchResults) {
                System.out.println(item.getName() + " = $" + item.getPrice());
            }
        } else {
            System.out.println("No items found for the given search query.");
        }
    }

    private static void editReservation() {
        System.out.println("\nEdit Reservation");
        if (reservations.isEmpty()) {
            System.out.println("No reservations available to edit.");
            return;
        }

        System.out.print("Enter the reservation number to edit: ");
        int reservationNumber = getUserChoice();

        if (reservationNumber < 1 || reservationNumber > reservations.size()) {
            System.out.println("Invalid reservation number. Please enter a valid reservation number.");
            return;
        }

        Reservation reservationToEdit = reservations.get(reservationNumber - 1);
        displayReservationDetails(reservationToEdit);

        String newName = getValidInput("Enter new name (press Enter to keep the existing name):", "[A-Za-z]*");
        String newDate = getValidInput("Enter new date (press Enter to keep the existing date):","\\d{2}/\\d{2}/\\d{4}");
        String newTime = getValidInput("Enter new time (press Enter to keep the existing time):", "\\d{2}:\\d{2}");

        if (!newName.isEmpty()) {
            reservationToEdit.setCustomerName(newName);
        }
        if (!newDate.isEmpty()) {
            reservationToEdit.setDate(newDate);
        }
        if (!newTime.isEmpty()) {
            reservationToEdit.setTime(newTime);
        }

        System.out.println("Reservation updated successfully!");
        displayReservationDetails(reservationToEdit);
    }

    private static void deleteReservation() {
        System.out.println("\nDelete Reservation");
        if (reservations.isEmpty()) {
            System.out.println("No reservations available to delete.");
            return;
        }

        System.out.print("Enter the reservation number to delete: ");
        int reservationNumber = getUserChoice();

        if (reservationNumber < 1 || reservationNumber > reservations.size()) {
            System.out.println("Invalid reservation number. Please enter a valid reservation number.");
            return;
        }

        Reservation canceledReservation = reservations.remove(reservationNumber - 1);
        System.out.println("Reservation deleted successfully!");
        displayReservationDetails(canceledReservation);
    }

    private static void viewStaffReservations() {
        System.out.println("\nView Reservations");
        if (reservations.isEmpty()) {
            System.out.println("No reservations available.");
        } else {
            for (int i = 0; i < reservations.size(); i++) {
                System.out.println("Reservation " + (i + 1) + ":");
                displayReservationDetails(reservations.get(i));
            }
        }
    }

    private static void searchReservationByCustomerName() {
        System.out.println("\nSearch Reservations by Customer Name");
        String searchQuery = getValidInput("Enter the customer name to search for:", "[A-Za-z]+");

        List<Reservation> searchResults = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(reservation);
            }
        }

        if (!searchResults.isEmpty()) {
            System.out.println("Search Results:");
            for (Reservation reservation : searchResults) {
                displayReservationDetails(reservation);
            }
        } else {
            System.out.println("No reservations found for the given customer name.");
        }
    }

    private static MenuItem findMenuItem(String mealName) {
        for (MenuItem menuItem : menu.getFixedMenu()) {
            if (menuItem.getName().equalsIgnoreCase(mealName)) {
                return menuItem;
            }
        }
        return null;
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static String getValidInput(String prompt, String regex) {
        String userInput;
        do {
            System.out.print(prompt + " ");
            userInput = scanner.next().trim();
        } while (!userInput.matches(regex));
        return userInput;
    }

    private static double getValidDoubleInput(String prompt) {
        double userInput;
        while (true) {
            try {
                System.out.print(prompt + " ");
                userInput = Double.parseDouble(scanner.next().trim());
                ////////////////////////////////////////////////////////
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return userInput;
    }

    private static void displayReservationDetails(Reservation reservation) {
        System.out.println("Customer Name: " + reservation.getCustomerName());
        System.out.println("Reservation Date: " + reservation.getDate());
        System.out.println("Reservation Time: " + reservation.getTime());
        System.out.println("Selected Meals:");

        double sumOfPrice=0;
        for (MenuItem item : reservation.getSelectedMeals()) {
            System.out.println(item.getName() + " = $" + item.getPrice());
            sumOfPrice+= item.getPrice();
        }
        System.out.println("Total Bill = $" + sumOfPrice);
    }

    private static void displayReservationDetails1(Reservation reservation) {
        System.out.println("Customer Name: " + reservation.getCustomerName());
        System.out.println("Reservation Date: " + reservation.getDate());
        System.out.println("Reservation Time: " + reservation.getTime());
    }

}

