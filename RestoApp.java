import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Arrays;

class RestoMenu {
    private String[] food = {"Bubur Ayam", "Kerupuk", "Nasi Kuning", "Soto"};
    public String[] drink = {"Air Mineral", "Es Jeruk Kecil", "Es Teh", "Kopi"};

    private double[] foodP = {15000.0, 10000.0, 18000.0, 18000.0};
    public double[] drinkP = {7000.0, 10000.0, 7000.0, 8000.0};

    public String[] getFood() {
        return food;
    }

    public double[] getFoodP() {
        return foodP;
    }

    public String[] getDrink() {
        return drink;
    }

    public double[] getDrinkP() {
        return drinkP;
    }

    public void removeMenuItem(int menuIndex) {
        if (menuIndex < food.length) {
            String[] newFood = new String[food.length - 1];
            double[] newFoodP = new double[foodP.length - 1];

            int newIndex = 0;
            for (int i = 0; i < food.length; i++) {
                if (i != menuIndex) {
                    newFood[newIndex] = food[i];
                    newFoodP[newIndex] = foodP[i];
                    newIndex++;
                }
            }

            food = newFood;
            foodP = newFoodP;
        } else {
            String[] newDrink = new String[drink.length - 1];
            double[] newDrinkP = new double[drinkP.length - 1];

            int newIndex = 0;
            for (int i = 0; i < drink.length; i++) {
                if (i != (menuIndex - food.length)) {
                    newDrink[newIndex] = drink[i];
                    newDrinkP[newIndex] = drinkP[i];
                    newIndex++;
                }
            }

            drink = newDrink;
            drinkP = newDrinkP;
        }
    }
    public String formatCurrency(double amount) {
        return "Rp " + (long) amount;
    }

    public void displayMenu() {
        System.out.println("\n----- Menu -----");
        System.out.println("Makanan:");
        for (int i = 0; i < food.length; i++) {
            String formattedPrice = formatCurrency(foodP[i]);
            System.out.printf("%d. %-15s %s\n", i + 1, food[i], formattedPrice);
        }

        System.out.println("\nMinuman:");
        for (int i = 0; i < drink.length; i++) {
            String formattedPrice = formatCurrency(drinkP[i]);
            System.out.printf("%d. %-15s %s\n", i + 1 + food.length, drink[i], formattedPrice);
        }
    }

    public void getOrder(Scanner scanner, Map<String, Integer> order) {
        while (true) {
            System.out.print("Silahkan masukan pesanan (atau ketik 'selesai' untuk menyelesaikan pesanan): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("selesai")) {
                break;
            }

            int itemIndex = -1;
            for (int i = 0; i < food.length; i++) {
                if (choice.equalsIgnoreCase(food[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                System.out.print("Jumlah: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                order.put(food[itemIndex], order.getOrDefault(food[itemIndex], 0) + quantity);
            } else {
                itemIndex = -1;
                for (int i = 0; i < drink.length; i++) {
                    if (choice.equalsIgnoreCase(drink[i])) {
                        itemIndex = i;
                        break;
                    }
                }
                if (itemIndex >= 0) {
                    System.out.print("Jumlah: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    order.put(drink[itemIndex], order.getOrDefault(drink[itemIndex], 0) + quantity);
                } else {
                    System.out.println("Invalid");
                }
            }
        }
    }

    public double calculateTotal(Map<String, Integer> order) {
        double total = 0.0;
        for (String item : order.keySet()) {
            int quantity = order.get(item);
            int itemIndex = -1;
            for (int i = 0; i < food.length; i++) {
                if (item.equals(food[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                total += foodP[itemIndex] * quantity;
            }
            itemIndex = -1;
            for (int i = 0; i < drink.length; i++) {
                if (item.equals(drink[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                total += drinkP[itemIndex] * quantity;
            }
        }
        return total;
    }

    public double calculateTax(double subtotal) {
        return 0.1 * subtotal;
    }

    public String drinkPromo(Map<String, Integer> order) {
        String promotion = "";
        Map<String, Integer> eligibleDrinks = new HashMap<>();

        // Iterate over the keys in the order map
        for (String category : order.keySet()) {
            // Check if the category is a drink
            if (Arrays.asList(drink).contains(category)) {
                int quantity = order.getOrDefault(category, 0);
                eligibleDrinks.put(category, quantity);
            }
        }

        int totalEligibleDrinks = eligibleDrinks.values().stream().mapToInt(Integer::intValue).sum();

        if (totalEligibleDrinks >= 2) {
            // Iterate over the eligible drinks
            for (String category : eligibleDrinks.keySet()) {
                int quantity = eligibleDrinks.getOrDefault(category, 0);
                int freeQuantity = quantity / 2;
                order.put(category, quantity - freeQuantity);
                promotion = "Promo: Buy One Get One Minuman (" + freeQuantity + ") " + category;
            }
        }
        return promotion;
    }

    public void displayBill(Map<String, Integer> order, double subtotal, double tax, double serviceFee, String promotion, double discount, double total) {
        System.out.println("\n----- Bill -----");
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Menu Item", "Price", "Order Quantity", "Total Price");

        for (String item : food) {
            int quantity = order.getOrDefault(item, 0);
            if (quantity > 0) {
                int itemIndex = -1;
                for (int i = 0; i < food.length; i++) {
                    if (item.equals(food[i])) {
                        itemIndex = i;
                        break;
                    }
                }
                if (itemIndex >= 0) {
                    String itemName = item;
                    String formattedPrice = formatCurrency(foodP[itemIndex]);
                    double totalItemPrice = foodP[itemIndex] * quantity;
                    String formattedTotalItemPrice = formatCurrency(totalItemPrice);
                    System.out.printf("%-15s %-15s %-15d %-15s\n", itemName, formattedPrice, quantity, formattedTotalItemPrice);
                }
            }
        }

        for (String item : drink) {
            int quantity = order.getOrDefault(item, 0);
            if (quantity > 0) {
                int itemIndex = -1;
                for (int i = 0; i < drink.length; i++) {
                    if (item.equals(drink[i])) {
                        itemIndex = i;
                        break;
                    }
                }
                if (itemIndex >= 0) {
                    String itemName = item;
                    String formattedPrice = formatCurrency(drinkP[itemIndex]);
                    double totalItemPrice = drinkP[itemIndex] * quantity;
                    String formattedTotalItemPrice = formatCurrency(totalItemPrice);
                    System.out.printf("%-15s %-15s %-15d %-15s\n", itemName, formattedPrice, quantity, formattedTotalItemPrice);
                }
            }
        }

        // Mencetak data-data yang dibutuhkan dalam struk pesanan
        System.out.println(" ");
        System.out.printf("%-15s %-15s\n", "Subtotal:", formatCurrency(subtotal));
        System.out.printf("%-15s %-15s\n", "Tax (10%):", formatCurrency(tax));
        System.out.printf("%-15s %-15s\n", "Service Fee:", formatCurrency(serviceFee));
        System.out.printf("%-15s %-15s\n", "Promotion:", promotion);
        System.out.printf("%-15s %-15s\n", "Discount:", formatCurrency(discount));
        System.out.printf("%-15s %-15s\n", "Total:", formatCurrency(total));
    }
}

public class RestoApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Membuat instance untuk kelas RestoMenu dan menampilkan menu
        RestoMenu restoMenu = new RestoMenu();

        Map<String, Integer> order = new HashMap<>();

        while (true) {
            System.out.println("\n----- Menu Utama -----");
            System.out.println("1. Pemesanan");
            System.out.println("2. Pengelolaan Menu");
            System.out.println("3. Keluar");

            System.out.print("Masukkan pilihan: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Membuat pesanan
                    displayNumbering(restoMenu);
                    restoMenu.getOrder(scanner, order);

                    double subtotal = restoMenu.calculateTotal(order);
                    double tax = restoMenu.calculateTax(subtotal);
                    double serviceFee = 20000.0;
                    double total = subtotal + tax + serviceFee;

                    String promotion = "";
                    if (total > 50000.0) {
                        promotion = restoMenu.drinkPromo(order);
                    }

                    // Menghitung diskon
                    double discount = 0.0;
                    if (total > 100000.0) {
                        discount = 0.1 * total;
                        total -= discount;
                    }

                    // Mencetak struk pesanan
                    restoMenu.displayBill(order, subtotal, tax, serviceFee, promotion, discount, total);
                    break;

                case 2:
                    // Mengelola Menu
                    manageMenu(scanner, restoMenu);
                    break;

                case 3:
                    // Keluar
                    System.out.println("Sistem ditutup");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid");
            }
        }
    }

    private static void manageMenu(Scanner scanner, RestoMenu restoMenu) {
        while (true) {
            System.out.println("\n----- Pengelolaan Menu -----");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali ke Menu Utama");

            System.out.print("Masukkan pilihan: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Menambahkan menu baru
                    addMenu(scanner, restoMenu);
                    break;

                case 2:
                    // Mengubah harga pada suatu menu
                    changePrice(scanner, restoMenu);
                    break;

                case 3:
                    // Menghapus suatu menu
                    deleteMenu(scanner, restoMenu);
                    break;

                case 4:
                    // Kembali ke menu utama
                    return;

                default:
                    System.out.println("Invalid");
            }
        }
    }

    private static void displayNumbering(RestoMenu restoMenu) {
        System.out.println("\n----- Menu dengan Nomor -----");
        System.out.println("Makanan:");
        String[] food = restoMenu.getFood();
        double[] foodP = restoMenu.getFoodP();
        for (int i = 0; i < food.length; i++) {
            String formattedPrice = restoMenu.formatCurrency(foodP[i]);
            System.out.printf("%d. %-15s %s\n", i + 1, food[i], formattedPrice);
        }

        System.out.println("\nMinuman:");
        String[] drink = restoMenu.getDrink();
        double[] drinkP = restoMenu.getDrinkP();
        for (int i = 0; i < drink.length; i++) {
            String formattedPrice = restoMenu.formatCurrency(drinkP[i]);
            System.out.printf("%d. %-15s %s\n", i + 1 + food.length, drink[i], formattedPrice);
        }
    }

    private static int getMenuIndexByNumber(int menuNumber, RestoMenu restoMenu) {
        if (menuNumber >= 1 && menuNumber <= (restoMenu.getFood().length + restoMenu.drink.length)) {
            if (menuNumber <= restoMenu.getFood().length) {
                return menuNumber - 1;
            } else {
                return menuNumber - 1 - restoMenu.getFood().length;
            }
        } else {
            return -1;
        }
    }

    private static void addMenu(Scanner scanner, RestoMenu restoMenu) {
        System.out.print("Masukkan nama menu baru: ");
        String newName = scanner.nextLine();

        System.out.print("Masukkan harga dari " + newName + ": ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        String[] newDrink = new String[restoMenu.drink.length + 1];
        double[] newDrinkP = new double[restoMenu.drinkP.length + 1];

        System.arraycopy(restoMenu.drink, 0, newDrink, 0, restoMenu.drink.length);
        System.arraycopy(restoMenu.drinkP, 0, newDrinkP, 0, restoMenu.drinkP.length);

        newDrink[newDrink.length - 1] = newName;
        newDrinkP[newDrinkP.length - 1] = newPrice;

        restoMenu.drink = newDrink;
        restoMenu.drinkP = newDrinkP;

        System.out.println("Menu telah ditambahkan");
    }

    private static void changePrice(Scanner scanner, RestoMenu restoMenu) {
        System.out.println("\n----- Daftar Menu -----");
        displayNumbering(restoMenu);

        System.out.print("Masukkan nomor: ");
        int menuNumber = scanner.nextInt();
        scanner.nextLine();

        int menuIndex = getMenuIndexByNumber(menuNumber, restoMenu);

        if (menuIndex != -1) {
            System.out.print("Masukkan harga baru: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Apakah perubahan selesai? (Ya/Tidak): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Ya")) {
                updatePrice(restoMenu, menuIndex, newPrice);
                System.out.println("Harga telah diperbarui");
            } else if (confirmation.equalsIgnoreCase("Tidak")) {
                System.out.println("Perubahan dibatalkan");
            } else {
                System.out.println("Invalid");
            }
        } else {
            System.out.println("Menu tidak tersedia.");
        }
    }

    private static void deleteMenu(Scanner scanner, RestoMenu restoMenu) {
        System.out.println("\n----- Daftar Menu -----");
        displayNumbering(restoMenu);

        System.out.print("Masukkan nomor: ");
        int menuNumber = scanner.nextInt();
        scanner.nextLine();

        int menuIndex = getMenuIndexByNumber(menuNumber, restoMenu);

        if (menuIndex != -1) {
            System.out.print("Apakah Anda yakin ingin menghapus menu ini? (Ya/Tidak): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Ya")) {
                restoMenu.removeMenuItem(menuIndex);
                System.out.println("Menu telah dihapus");
            } else if (confirmation.equalsIgnoreCase("Tidak")) {
                System.out.println("Penghapusan dibatalkan");
            } else {
                System.out.println("Invalid");
            }
        } else {
            System.out.println("Menu tidak sesuai.");
        }
    }

    private static void updatePrice(RestoMenu restoMenu, int menuIndex, double newPrice) {
        if (menuIndex < restoMenu.getFood().length) {
            restoMenu.getFoodP()[menuIndex] = newPrice;
        } else {
            restoMenu.drinkP[menuIndex - restoMenu.getFood().length] = newPrice;
        }
    }
}
