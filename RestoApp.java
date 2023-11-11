import java.util.*;

class RestoMenu {
    String[] food = { "Bubur Ayam", "Kerupuk", "Nasi Kuning", "Soto" };
    String[] drink = { "Air Mineral", "Es Jeruk Kecil", "Es Teh", "Kopi" };

    double[] foodP = { 15000.0, 10000.0, 18000.0, 18000.0 };
    double[] drinkP = { 7000.0, 10000.0, 7000.0, 8000.0 };
}

public class RestoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestoMenu restoMenu = new RestoMenu();

        while (true) {
            System.out.println("Silahkan pilih opsi yang diinginkan:");
            System.out.println("1. Pemesanan");
            System.out.println("2. Pengelolaan Menu");
            System.out.println("3. Keluar");

            System.out.print("Masukan pilihan: ");
            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    custOrder(scanner, restoMenu);
                    break;
                case 2:
                    setMenu(scanner, restoMenu);
                    break;
                case 3:
                    System.out.println("Sistem ditutup");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid");
            }
        }
    }

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void setMenu(Scanner scanner, RestoMenu restoMenu) {
        while (true) {
            System.out.println("\nPengaturan Menu:");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali ke Menu Utama");

            System.out.print("Masukkan opsi: ");
            int ownerChoice = getIntInput(scanner);

            switch (ownerChoice) {
                case 1:
                    addMenu(scanner, restoMenu);
                    break;
                case 2:
                    changePrice(scanner, restoMenu);
                    break;
                case 3:
                    deleteMenu(scanner, restoMenu);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid");
            }
        }
    }

    private static void addMenu(Scanner scanner, RestoMenu restoMenu) {
        System.out.print("Masukan Menu Baru: ");
        String newItem = scanner.nextLine();

        System.out.print("Masukan harga untuk " + newItem + ": ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Tambah " + newItem + " ke dalam menu, dengan harga " + newPrice);

        confirm(scanner, restoMenu);
    }

    private static void changePrice(Scanner scanner, RestoMenu restoMenu) {
        System.out.println("Daftar Menu:");
        displayMenu(restoMenu);

        System.out.print("Masukan nomor menu yang harganya akan diubah: ");
        int menuNumber = getIntInput(scanner);
        scanner.nextLine();

        int itemIndex = menuNumber - 1;

        if (itemIndex >= 0 && itemIndex < restoMenu.food.length + restoMenu.drink.length) {
            String menuItem = (itemIndex < restoMenu.food.length) ? restoMenu.food[itemIndex] : restoMenu.drink[itemIndex - restoMenu.food.length];

            System.out.println("Pilihan: " + menuItem);
            System.out.print("Masukkan harga baru untuk " + menuItem + ": ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();

            if (itemIndex < restoMenu.food.length) {
                restoMenu.foodP[itemIndex] = newPrice;
            } else {
                restoMenu.drinkP[itemIndex - restoMenu.food.length] = newPrice;
            }

            confirm(scanner, restoMenu);
        } else {
            System.out.println("Invalid");
        }
    }

    private static void deleteMenu(Scanner scanner, RestoMenu restoMenu) {
        System.out.println("Daftar Menu:");
        displayMenu(restoMenu);

        System.out.print("Masukkan nomor menu yang akan dihapus: ");
        int menuNumber = getIntInput(scanner);
        scanner.nextLine();

        int itemIndex = menuNumber - 1;

        if (itemIndex >= 0 && itemIndex < restoMenu.food.length + restoMenu.drink.length) {
            String menuItem = (itemIndex < restoMenu.food.length) ? restoMenu.food[itemIndex] : restoMenu.drink[itemIndex - restoMenu.food.length];

            System.out.println("Pilihan: " + menuItem);

            if (itemIndex < restoMenu.food.length) {
                restoMenu.food = removeItem(restoMenu.food, itemIndex);
                restoMenu.foodP = removeItem(restoMenu.foodP, itemIndex);
            } else {
                restoMenu.drink = removeItem(restoMenu.drink, itemIndex - restoMenu.food.length);
                restoMenu.drinkP = removeItem(restoMenu.drinkP, itemIndex - restoMenu.food.length);
            }

            confirm(scanner, restoMenu);
        } else {
            System.out.println("Invalid");
        }
    }

    private static void confirm(Scanner scanner, RestoMenu restoMenu) {
        System.out.print("Apakah perubahan selesai? (Ya/Tidak): ");
        String confirmation = scanner.nextLine().toLowerCase();

        while (!confirmation.equals("ya") && !confirmation.equals("tidak")) {
            System.out.println("Invalid");
            System.out.print("Apakah perubahan selesai? (Ya/Tidak): ");
            confirmation = scanner.nextLine().toLowerCase();
        }

        if (confirmation.equals("ya")) {
            System.out.println("Perubahan berhasil diterapkan");
        } else {
            restoMenu = new RestoMenu();
            System.out.println("Perubahan dibatalkan");
        }
    }

    private static String[] removeItem(String[] array, int index) {
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }

    private static double[] removeItem(double[] array, int index) {
        double[] newArray = new double[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }

    public static void custOrder(Scanner scanner, RestoMenu restoMenu) {
        Map<String, Integer> order = new HashMap<>();

        while (true) {
            displayMenu(restoMenu);
            System.out.print("Masukkan pesanan anda (ketik 'selesai' untuk menyelesaikan pesanan): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("selesai")) {
                break;
            }

            if (!validChoice(choice, restoMenu)) {
                System.out.println("Invalid");
                continue;
            }

            System.out.print("Masukkan jumlah: ");
            int quantity = getIntInput(scanner);
            scanner.nextLine();

            order.put(choice, order.getOrDefault(choice, 0) + quantity);
        }

        double subtotal = calculateTotal(order, restoMenu);
        double tax = calculateTax(subtotal);
        double serviceFee = 20000.0;
        double total = subtotal + tax + serviceFee;

        List<String> promotions = new ArrayList<>();

        if (subtotal > 50000.0) {
            promotions = drinkPromo(order, restoMenu);
        }

        double discount = 0.0;
        if (total > 100000.0) {
            discount = 0.1 * total;
            total -= discount;
        }

        displayBill(order, total, promotions, discount, restoMenu);
    }


    private static boolean validChoice(String choice, RestoMenu restoMenu) {
        for (String foodItem : restoMenu.food) {
            if (choice.equalsIgnoreCase(foodItem)) {
                return true;
            }
        }
        for (String drinkItem : restoMenu.drink) {
            if (choice.equalsIgnoreCase(drinkItem)) {
                return true;
            }
        }
        return false;
    }

    private static void displayMenu(RestoMenu restoMenu) {
        System.out.println("Makanan:");
        for (int i = 0; i < restoMenu.food.length; i++) {
            String formattedPrice = formatCurrency(restoMenu.foodP[i]);
            System.out.printf("%d. %-20s %-15s%n", i + 1, restoMenu.food[i], formattedPrice);
        }

        System.out.println("Minuman:");
        for (int i = 0; i < restoMenu.drink.length; i++) {
            String formattedPrice = formatCurrency(restoMenu.drinkP[i]);
            System.out.printf("%d. %-20s %-15s%n", i + 1 + restoMenu.food.length, restoMenu.drink[i], formattedPrice);
        }
    }

    private static String formatCurrency(double amount) {
        return "Rp " + (long) amount;
    }

    private static double calculateTotal(Map<String, Integer> order, RestoMenu restoMenu) {
        double total = 0.0;

        for (String item : order.keySet()) {
            int quantity = order.get(item);
            int itemIndex = -1;
            for (int i = 0; i < restoMenu.food.length; i++) {
                if (item.equals(restoMenu.food[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                total += restoMenu.foodP[itemIndex] * quantity;
            }
            itemIndex = -1;
            for (int i = 0; i < restoMenu.drink.length; i++) {
                if (item.equals(restoMenu.drink[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                total += restoMenu.drinkP[itemIndex] * quantity;
            }
        }
        return total;
    }

    private static double calculateTax(double subtotal) {
        return 0.1 * subtotal;
    }

    private static List<String> drinkPromo(Map<String, Integer> order, RestoMenu restoMenu) {
        List<String> promotions = new ArrayList<>();
        Map<String, Integer> claimedDrinks = new HashMap<>();

        for (String category : restoMenu.drink) {
            int quantity = order.getOrDefault(category, 0);
            if (quantity > 0) {
                int claimed = claimedDrinks.getOrDefault(category, 0);
                int freeQt = quantity / 2;
                claimedDrinks.put(category, claimed + freeQt);
                if (freeQt > 0) {
                    promotions.add("Promo: Buy One Get One " + category + " (" + freeQt + " gratis)");
                }
            }
        }

        return promotions;
    }

    private static void displayBill(Map<String, Integer> order, double total, List<String> promotions, double discount, RestoMenu restoMenu) {
        System.out.println("-------- Bill --------");
        System.out.printf("%-20s %-15s %-10s %-15s%n", "Nama Menu", "Harga", "Jumlah", "Total Harga");

        for (String item : order.keySet()) {
            int quantity = order.get(item);
            double itemPrice = 0.0;
            String itemName = "";

            int itemIndex = -1;
            for (int i = 0; i < restoMenu.food.length; i++) {
                if (item.equals(restoMenu.food[i])) {
                    itemIndex = i;
                    itemPrice = restoMenu.foodP[i];
                    itemName = item;
                    break;
                }
            }

            itemIndex = -1;
            for (int i = 0; i < restoMenu.drink.length; i++) {
                if (item.equals(restoMenu.drink[i])) {
                    itemIndex = i;
                    itemPrice = restoMenu.drinkP[i];
                    itemName = item;
                    break;
                }
            }

            if (quantity > 0) {
                String formattedPrice = formatCurrency(itemPrice);
                double totalItemPrice = itemPrice * quantity;
                String formattedTotalItemPrice = formatCurrency(totalItemPrice);
                System.out.printf("%-20s %-15s %-10d %-15s%n", itemName, formattedPrice, quantity, formattedTotalItemPrice);
            }
        }

        String subtotal = formatCurrency(calculateTotal(order, restoMenu));
        String tax = formatCurrency(calculateTax(calculateTotal(order, restoMenu)));
        String serviceFee = formatCurrency(20000.0);
        String formattedTotal = formatCurrency(total);

        System.out.println("");
        System.out.printf("%-45s %s%n", "Subtotal:", subtotal);
        System.out.printf("%-45s %s%n", "Pajak (10%):", tax);
        System.out.printf("%-45s %s%n", "Biaya Pelayanan:", serviceFee);
        System.out.println("");
        System.out.printf("%-45s %s%n", "Total:", formattedTotal);

        for (String promotion : promotions) {
            System.out.println(promotion);
        }

        if (discount > 0) {
            System.out.printf("%-45s %s%n", "Discount (10%):", formatCurrency(discount));
            total -= discount;
            String formattedTotalAfterDiscount = formatCurrency(total);
            System.out.printf("%-45s %s%n", "Total:", formattedTotalAfterDiscount);
        }
    }
}