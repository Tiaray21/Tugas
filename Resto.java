import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Menu {
    // berisikan daftar menu (makanan, dan minuman) beserta harganya
    String[] food = { "Bubur Ayam", "Kerupuk", "Nasi Kuning", "Soto" };
    String[] drink = { "Air Mineral", "Es Jeruk Kecil", "Es Teh", "Kopi" };

    double[] foodP = { 15000.0, 10000.0, 18000.0, 18000.0 };
    double[] drinkP = { 7000.0, 10000.0, 7000.0, 8000.0 };
}

public class Resto {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu();

        // membuat objek map yang menyimpan pesanan dan menggabungkan item pada pesanan dengan jumlahnya
        Map<String, Integer> order = new HashMap<>();

        // memanggil method menu
        displayMenu(menu);

        // memanggil method getOrder
        getOrder(scanner, order, menu);

        // mendeklarasi perhitungan yang diperlukan
        double subtotal = calculateTotal(order, menu);
        double tax = calculateTax(subtotal);
        double serviceFee = 20000.0;
        double total = subtotal + tax + serviceFee;

        List<String> promotions = new ArrayList<>();

        // menentukan apakah total order memenuhi total biaya minimal untuk mendapatkan promosi minuman gratis
        if (subtotal > 50000.0) {
            promotions = drinkPromo(order, menu);
        }

        // menentukan apakah total keseluruhan melebihi total biaya minimal untuk mendapatkan diskon 10%
        double discount = 0.0;
        if (total > 100000.0) {
            discount = 0.1 * total;
            total -= discount;
        }

        // menentukan apa saja yang akan ditampilkan pada struk pesanan
        displayBill(order, total, promotions, discount, menu);

        scanner.close();
    }

    public static void displayMenu(Menu menu) {
        // method menu untuk menampilkan isinya (daftar menu)
        System.out.println("Menu:");
        System.out.println("Makanan:");
        // mencetak daftar menu sesuai kategori
        for (int i = 0; i < menu.food.length; i++) {
            String formattedPrice = formatCurrency(menu.foodP[i]);
            System.out.printf("%-20s %-15s%n", menu.food[i], formattedPrice);
        }

        System.out.println("Minuman:");
        for (int i = 0; i < menu.drink.length; i++) {
            String formattedPrice = formatCurrency(menu.drinkP[i]);
            System.out.printf("%-20s %-15s%n", menu.drink[i], formattedPrice);
        }
    }

    public static void getOrder(Scanner scanner, Map<String, Integer> order, Menu menu) {
        // mengumpulkan data pesanan dari input, memasukannya ke order, memastikan bahwa input ada pada menu dan
        // diteruskan ke order
        int maxOrderQt = 4;// menentukan maksimal 4 menu

        // menggunakan loop untuk meneruskan input pesanan hingga pengguna menggunakan keyword 'selesai'
        // untuk menyelesaikan pesanan
        while (true) {
            System.out.print("Silahkan masukkan pesanan (atau ketik 'selesai' untuk menyelesaikan pesanan): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("selesai")) {
                break;
            }

            int itemIndex = -1;
            for (int i = 0; i < menu.food.length; i++) {
                if (choice.equalsIgnoreCase(menu.food[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                System.out.print("Jumlah: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity > maxOrderQt) {
                    quantity = maxOrderQt;
                }
                order.put(menu.food[itemIndex], order.getOrDefault(menu.food[itemIndex], 0) + quantity);
            } else {
                itemIndex = -1;
                for (int i = 0; i < menu.drink.length; i++) {
                    if (choice.equalsIgnoreCase(menu.drink[i])) {
                        itemIndex = i;
                        break;
                    }
                }
                if (itemIndex >= 0) {
                    System.out.print("Jumlah: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    if (quantity > maxOrderQt) {
                        quantity = maxOrderQt;
                    }
                    order.put(menu.drink[itemIndex], order.getOrDefault(menu.drink[itemIndex], 0) + quantity);
                } else {
                    // menampilkan pesan tertentu apabila input tidak tersedia di daftar menu
                    System.out.println("Tidak tersedia");
                }
            }
        }
    }

    public static double calculateTotal(Map<String, Integer> order, Menu menu) {
        double total = 0.0;

        // melakukan eksekusi harga sesuai dengan order dan mengambil harga dari daftar menu
        for (String item : order.keySet()) {
            int quantity = order.get(item);
            int itemIndex = -1;
            for (int i = 0; i < menu.food.length; i++) {
                if (item.equals(menu.food[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                total += menu.foodP[itemIndex] * quantity;
            }
            itemIndex = -1;
            for (int i = 0; i < menu.drink.length; i++) {
                if (item.equals(menu.drink[i])) {
                    itemIndex = i;
                    break;
                }
            }
            if (itemIndex >= 0) {
                total += menu.drinkP[itemIndex] * quantity;
            }
        }
        return total;
    }

    public static double calculateTax(double subtotal) {
        // menghitung pajak sebesar 10%
        return 0.1 * subtotal;
    }

    public static List<String> drinkPromo(Map<String, Integer> order, Menu menu) {
        // deklarasi promotions dengan tipe data koleksi
        List<String> promotions = new ArrayList<>();
        Map<String, Integer> claimedDrinks = new HashMap<>();

        for (String category : menu.drink) {
            int quantity = order.getOrDefault(category, 0);
            if (quantity > 0) {
                int claimed = claimedDrinks.getOrDefault(category, 0);
                int freeQt = quantity / 2; // pembagian dua untuk menentukan jumlah minuman gratis
                claimedDrinks.put(category, claimed + freeQt);
                if (freeQt > 0) {
                    promotions.add("Promo: Buy One Get One " + category + " (" + freeQt + " gratis)");
                }
            }
        }

        return promotions;
    }

    public static void displayBill(Map<String, Integer> order, double total, List<String> promotions, double discount, Menu menu) {
        // melakukan pencetakan struk pesanan dengan mendeklarasi displayBill dengan daftar parameternya
        System.out.println("-------- Bill --------");
        System.out.printf("%-20s %-15s %-10s %-15s%n", "Nama Menu", "Harga", "Jumlah", "Total Harga");

        for (String item : order.keySet()) {
            int quantity = order.get(item);
            double itemPrice = 0.0;
            String itemName = "";

            int itemIndex = -1;
            for (int i = 0; i < menu.food.length; i++) {
                if (item.equals(menu.food[i])) {
                    itemIndex = i;
                    itemPrice = menu.foodP[i];
                    itemName = item;
                    break;
                }
            }

            itemIndex = -1;
            for (int i = 0; i < menu.drink.length; i++) {
                if (item.equals(menu.drink[i])) {
                    itemIndex = i;
                    itemPrice = menu.drinkP[i];
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

        // melakukan input data perhitungan dengan nilai yang sudah diformat ke dalam Rupiah
        String subtotal = formatCurrency(calculateTotal(order, menu));
        String tax = formatCurrency(calculateTax(calculateTotal(order, menu)));
        String serviceFee = formatCurrency(20000.0);
        String formattedTotal = formatCurrency(total);

        System.out.println("");
        System.out.printf("%-45s %s%n", "Subtotal:", subtotal);
        System.out.printf("%-45s %s%n", "Pajak (10%):", tax);
        System.out.printf("%-45s %s%n", "Biaya Pelayanan:", serviceFee);
        System.out.println("");
        System.out.printf("%-45s %s%n", "Total:", formattedTotal);

        // mencetak promosi apabila didapatkan
        for (String promotion : promotions) {
            System.out.println(promotion);
        }

        // mencetak diskon apabila nilai diskon lebih dari 0
        if (discount > 0) {
            System.out.printf("%-45s %s%n", "Discount (10%):", formatCurrency(discount));
            total -= discount;
            String formattedTotalAfterDiscount = formatCurrency(total);
            System.out.printf("%-45s %s%n", "Total akhir:", formattedTotalAfterDiscount);
        }
    }

    public static String formatCurrency(double amount) {
        // melakukan format terhadap nilai berupa angka/harga
        return "Rp " + (long) amount;
    }
}

