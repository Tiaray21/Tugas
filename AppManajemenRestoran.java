import java.io.*;
import java.util.*;


abstract class MenuItem {
    String nama;
    double harga;
    String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public abstract void tampilMenu();

    public double getPrice() {
        return 0;
    }

    public abstract void displayMenu();
}

// Kelas Makanan
class Makanan extends MenuItem {
    String jenisMakanan;

    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Nama: " + nama);
        System.out.println("Harga: " + harga);
        System.out.println("Kategori: " + kategori);
        System.out.println("Jenis Makanan: " + jenisMakanan);
        System.out.println("------------------------------");
    }

    @Override
    public double getPrice() {
        return harga;
    }

    @Override
    public void displayMenu() {
        tampilMenu();
    }
}

// Kelas Minuman
class Minuman extends MenuItem {
    String jenisMinuman;

    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Nama: " + nama);
        System.out.println("Harga: " + harga);
        System.out.println("Kategori: " + kategori);
        System.out.println("Jenis Minuman: " + jenisMinuman);
        System.out.println("------------------------------");
    }

    @Override
    public double getPrice() {
        return harga;
    }

    @Override
    public void displayMenu() {
        tampilMenu();
    }
}

// Kelas Diskon
class Diskon extends MenuItem {
    double diskon;

    public Diskon(String nama, double harga, String kategori, double diskon) {
        super(nama, harga, kategori);
        this.diskon = diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Nama: " + nama);
        System.out.println("Harga: " + harga);
        System.out.println("Kategori: " + kategori);
        System.out.println("Diskon: " + diskon + "%");
        System.out.println("Harga Setelah Diskon: " + (harga - (harga * diskon / 100)));
        System.out.println("------------------------------");
    }

    @Override
    public double getPrice() {
        return harga - (harga * diskon / 100);
    }

    @Override
    public void displayMenu() {
        tampilMenu();
    }
}

// Kelas Menu
class Menu {
    ArrayList<MenuItem> daftarMenu = new ArrayList<>();

    public void tambahMenu(MenuItem item) {
        daftarMenu.add(item);
    }

    public void tampilkanSemuaMenu() {
        for (MenuItem item : daftarMenu) {
            item.tampilMenu();
        }
    }

    public void simpanMenuKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("daftar_menu.txt"))) {
            for (MenuItem item : daftarMenu) {
                writer.write(item.nama + "," + item.harga + "," + item.kategori);
                writer.newLine();
            }
            System.out.println("Daftar menu berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan daftar menu: " + e.getMessage());
        }
    }

    public void muatMenuDariFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("daftar_menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String nama = parts[0];
                    double harga = Double.parseDouble(parts[1]);
                    String kategori = parts[2];

                    MenuItem newItem;
                    if (kategori.equalsIgnoreCase("makanan")) {
                        newItem = new Makanan(nama, harga, kategori, parts[3]); // parts[3] adalah jenis makanan
                    } else if (kategori.equalsIgnoreCase("minuman")) {
                        newItem = new Minuman(nama, harga, kategori, parts[3]); // parts[3] adalah jenis minuman
                    } else if (kategori.equalsIgnoreCase("diskon")) {
                        double diskon = Double.parseDouble(parts[1]);// parts[1] adalah infromasi harga
                        newItem = new Diskon(nama, harga, kategori, diskon);
                    } else {
                        // Kategori tidak valid
                        System.out.println("Kategori tidak dikenali: " + kategori);
                        continue; // atau tindakan lain sesuai logika aplikasi Anda
                    }

                    // Tambahkan item menu ke daftar menu
                    tambahMenu(newItem);
                }
            }
            System.out.println("Daftar menu berhasil dimuat dari file.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat memuat daftar menu: " + e.getMessage());
        }
    }


    public MenuItem getItemByName(String itemName) {
        return null;
    }
}

class MenuRestoran {
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

class Pesanan {
    private ArrayList<MenuItem> itemsOrdered;

    public Pesanan() {
        this.itemsOrdered = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        itemsOrdered.add(item);
    }

    // Metode untuk menambahkan item berdasarkan nama item
    public void tambahItem(String itemName, Menu menu) {
        MenuItem item = menu.getItemByName(itemName);
        if (item != null) {
            itemsOrdered.add(item);
            System.out.println("Item " + itemName + " telah ditambahkan ke pesanan.");
        } else {
            System.out.println("Item " + itemName + " tidak ditemukan dalam menu.");
        }
    }

    public void tampilkanPesanan() {
        System.out.println("----- Pesanan -----");
        for (MenuItem item : itemsOrdered) {
            item.displayMenu();
        }
    }

    public double hitungTotal() {
        double total = 0.0;
        for (MenuItem item : itemsOrdered) {
            total += item.getPrice();
        }
        return total;
    }
    public void simpanStrukKeFile(ArrayList<String> strukPesanan) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("struk_pesanan.txt"));
            for (String struk : strukPesanan) {
                writer.write(struk);
                writer.newLine();
            }
            writer.close();
            System.out.println("Struk pesanan berhasil disimpan.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan struk pesanan: " + e.getMessage());
        }
    }

    // Fungsi untuk membaca struk pesanan dari file teks
    public ArrayList<String> bacaStrukDariFile() {
        ArrayList<String> strukPesanan = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("struk_pesanan.txt"));
            while (scanner.hasNextLine()) {
                String struk = scanner.nextLine();
                strukPesanan.add(struk);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File struk pesanan tidak ditemukan.");
        }
        return strukPesanan;
    }

    public void tambahItem(String itemName) {
    }
}


public class AppManajemenRestoran {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuRestoran menuRestoran = new MenuRestoran();
        Map<String, Integer> order = new HashMap<>();
        ArrayList<String> strukPesanan = new ArrayList<>();

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
                    menuRestoran.displayMenu();
                    menuRestoran.getOrder(scanner, order);

                    double subtotal = menuRestoran.calculateTotal(order);
                    double tax = menuRestoran.calculateTax(subtotal);
                    double serviceFee = 20000.0;
                    double total = subtotal + tax + serviceFee;

                    String promotion = "";
                    if (total > 50000.0) {
                        promotion = menuRestoran.drinkPromo(order);
                    }

                    // Menghitung diskon
                    double discount = 0.0;
                    if (total > 100000.0) {
                        discount = 0.1 * total;
                        total -= discount;
                    }

                    // Mencetak struk pesanan
                    menuRestoran.displayBill(order, subtotal, tax, serviceFee, promotion, discount, total);

                    // Menyimpan struk pesanan ke dalam file
                    Pesanan pesanan = new Pesanan();
                    for (Map.Entry<String, Integer> entry : order.entrySet()) {
                        String itemName = entry.getKey();
                        int quantity = entry.getValue();
                        for (int i = 0; i < quantity; i++) {
                            strukPesanan.add(itemName);
                            pesanan.tambahItem(itemName); // Menambahkan item ke pesanan
                        }
                    }
                    pesanan.simpanStrukKeFile(strukPesanan);
                    break;

                case 2:
                    // Mengelola Menu
                    manageMenu(scanner, menuRestoran);
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

    private static void manageMenu(Scanner scanner, MenuRestoran menuRestoran) {
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
                    addMenu(scanner, menuRestoran);
                    break;

                case 2:
                    // Mengubah harga pada suatu menu
                    changePrice(scanner, menuRestoran);
                    break;

                case 3:
                    // Menghapus suatu menu
                    deleteMenu(scanner, menuRestoran);
                    break;

                case 4:
                    // Kembali ke menu utama
                    return;

                default:
                    System.out.println("Invalid");
            }
        }
    }

    private static void displayNumbering(MenuRestoran menuRestoran) {
        System.out.println("\n----- Menu dengan Nomor -----");
        System.out.println("Makanan:");
        String[] food = menuRestoran.getFood();
        double[] foodP = menuRestoran.getFoodP();
        for (int i = 0; i < food.length; i++) {
            String formattedPrice = menuRestoran.formatCurrency(foodP[i]);
            System.out.printf("%d. %-15s %s\n", i + 1, food[i], formattedPrice);
        }

        System.out.println("\nMinuman:");
        String[] drink = menuRestoran.getDrink();
        double[] drinkP = menuRestoran.getDrinkP();
        for (int i = 0; i < drink.length; i++) {
            String formattedPrice = menuRestoran.formatCurrency(drinkP[i]);
            System.out.printf("%d. %-15s %s\n", i + 1 + food.length, drink[i], formattedPrice);
        }
    }

    private static int getMenuIndexByNumber(int menuNumber, MenuRestoran menuRestoran) {
        if (menuNumber >= 1 && menuNumber <= (menuRestoran.getFood().length + menuRestoran.drink.length)) {
            if (menuNumber <= menuRestoran.getFood().length) {
                return menuNumber - 1;
            } else {
                return menuNumber - 1 - menuRestoran.getFood().length;
            }
        } else {
            return -1;
        }
    }

    private static void addMenu(Scanner scanner, MenuRestoran menuRestoran) {
        System.out.print("Masukkan nama menu baru: ");
        String newName = scanner.nextLine();

        System.out.print("Masukkan harga dari " + newName + ": ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        String[] newDrink = new String[menuRestoran.drink.length + 1];
        double[] newDrinkP = new double[menuRestoran.drinkP.length + 1];

        System.arraycopy(menuRestoran.drink, 0, newDrink, 0, menuRestoran.drink.length);
        System.arraycopy(menuRestoran.drinkP, 0, newDrinkP, 0, menuRestoran.drinkP.length);

        newDrink[newDrink.length - 1] = newName;
        newDrinkP[newDrinkP.length - 1] = newPrice;

        menuRestoran.drink = newDrink;
        menuRestoran.drinkP = newDrinkP;

        System.out.println("Menu telah ditambahkan");
    }

    private static void changePrice(Scanner scanner, MenuRestoran menuRestoran) {
        displayNumbering(menuRestoran);

        System.out.print("Masukkan nomor: ");
        int menuNumber = scanner.nextInt();
        scanner.nextLine();

        int menuIndex = getMenuIndexByNumber(menuNumber, menuRestoran);

        if (menuIndex != -1) {
            System.out.print("Masukkan harga baru: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Apakah perubahan selesai? (Ya/Tidak): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Ya")) {
                updatePrice(menuRestoran, menuIndex, newPrice);
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

    private static void deleteMenu(Scanner scanner, MenuRestoran menuRestoran) {
        displayNumbering(menuRestoran);

        System.out.print("Masukkan nomor: ");
        int menuNumber = scanner.nextInt();
        scanner.nextLine();

        int menuIndex = getMenuIndexByNumber(menuNumber, menuRestoran);

        if (menuIndex != -1) {
            System.out.print("Apakah Anda yakin ingin menghapus menu ini? (Ya/Tidak): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Ya")) {
                menuRestoran.removeMenuItem(menuIndex);
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

    private static void updatePrice(MenuRestoran menuRestoran, int menuIndex, double newPrice) {
        if (menuIndex < menuRestoran.getFood().length) {
            menuRestoran.getFoodP()[menuIndex] = newPrice;
        } else {
            menuRestoran.drinkP[menuIndex - menuRestoran.getFood().length] = newPrice;
        }
    }
}
