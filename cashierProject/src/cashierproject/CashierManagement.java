/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cashierproject;

/**
 *
 * @author win11
 */
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

abstract class Product {
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

class RegularProduct extends Product {
    public RegularProduct(String name, int price) {
        super(name, price);
    }

    @Override
    public void displayDetails() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.printf("%-20s %s%n", getName(), currencyFormat.format(getPrice()));
    }
}

class Cashier {
    private List<Product> products;
    private int totalPrice;

    public Cashier() {
        products = new ArrayList<>();
        totalPrice = 0;
    }

    public void addProduct(Product product) {
        products.add(product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        totalPrice -= product.getPrice();
    }

    public void displayProducts() {
        System.out.println("Products:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s %s%n", "Product", "Price");
        System.out.println("--------------------------------------------------");
        for (Product product : products) {
            product.displayDetails();
        }
        System.out.println("--------------------------------------------------");
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}

public class CashierManagement {
    private static Map<Integer, Product> productMap = new HashMap<>();
    private static Cashier cashier = new Cashier();

    public static void updateProductList() {
        System.out.println("Products:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-27s %s%n", "Product", "Price");
        System.out.println("--------------------------------------------------");
        for (Map.Entry<Integer, Product> entry : productMap.entrySet()) {
            System.out.printf("%-5d ", entry.getKey());
            entry.getValue().displayDetails();
        }
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //produk dibawah ini adalah produk regular, jika toko ingin menambahkan stok, bisa di add pada admin access.
        productMap.put(1, new RegularProduct("Oreo Blackpink 171g", 15400));
        productMap.put(2, new RegularProduct("Greenfields 125ml", 3800));
        productMap.put(3, new RegularProduct("Ultramilk 200ml", 5000));
        productMap.put(4, new RegularProduct("Aqua 500ml", 4000));
        productMap.put(5, new RegularProduct("QTELA 55g", 8500));
        productMap.put(6, new RegularProduct("KUSUKA 60g", 6500));
        productMap.put(7, new RegularProduct("Tango 110g", 9000));
        productMap.put(8, new RegularProduct("Chocolatos 90g", 13200));
        productMap.put(9, new RegularProduct("Tic Tac 90g", 7300));
        productMap.put(10, new RegularProduct("PocariSweat 350ml", 4000));

        boolean isAdminMode = false;
        boolean backToMainMenu = false;

        do {
            System.out.println();
            System.out.print("Masuk ke admin/karyawan access (y/n)? ");
            String adminInput = scanner.nextLine();
            if (adminInput.equalsIgnoreCase("y")) {
                isAdminMode = true;
            }

            if (isAdminMode) {
                // Admin access
                do {
                    System.out.println();
                    System.out.println("--------------------------------------------------");
                    System.out.println("            Akses Admin dan Karyawan              ");
                    System.out.println("--------------------------------------------------");
                    System.out.println("1. Tambah Produk");
                    System.out.println("2. Hapus Produk");
                    System.out.println("3. Kembali ke Menu Utama");
                    
                    System.out.println();
                    System.out.print("Pilih aksi (1/2/3): ");
                    int adminChoice = scanner.nextInt();
                    scanner.nextLine(); 

                    switch (adminChoice) {
                        case 1:
                            boolean addMoreProducts = true;
                            while (addMoreProducts) {
                                System.out.println();
                                System.out.print("Masukkan nama produk baru: ");
                                String newProductName = scanner.nextLine();
                                System.out.print("Masukkan harga produk baru: ");
                                int newProductPrice = scanner.nextInt();
                                scanner.nextLine(); 

                                int newProductNumber = productMap.size() + 1;
                                productMap.put(newProductNumber, new RegularProduct(newProductName, newProductPrice));
                       
                                System.out.println("Produk baru berhasil ditambahkan dengan nomor: " + newProductNumber);
                                
                                System.out.println();
                                System.out.print("Apakah Anda ingin menambahkan produk lagi (y/n)? ");
                                String addMoreInput = scanner.nextLine();
                                if (!addMoreInput.equalsIgnoreCase("y")) {
                                    addMoreProducts = false;
                                }
                            }

                            // Memperbarui daftar produk dan menampilkan daftar produk
                            updateProductList();
                            break;
                        case 2:
                            System.out.println();
                            System.out.print("Masukkan nomor produk yang akan dihapus: ");
                            int productNumberToRemove = scanner.nextInt();
                            scanner.nextLine(); // consume newline character

                            Product productToRemove = productMap.get(productNumberToRemove);
                            if (productToRemove != null) {
                                productMap.remove(productNumberToRemove);
                                
                                System.out.println();
                                System.out.println("Produk berhasil dihapus!");

                                // Memperbarui daftar produk dan menampilkan daftar produk
                                updateProductList();
                            } else {
                                System.out.println();
                                System.out.println("Nomor produk tidak valid!");
                            }
                            break;
                        case 3:
                            backToMainMenu = true;
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                            break;
                    }
                } while (!backToMainMenu);
            } else {
                // Cashier access
                System.out.println();
                System.out.println("--------------------------------------------------");
                System.out.println("          Selamat Datang di Kasir Toko            ");
                System.out.println("--------------------------------------------------");
                
                System.out.println("Daftar Produk:");
                updateProductList();

                System.out.print("Berapa jumlah produk yang dibeli? ");//total jumlah produk yg dibeli
                int numProducts = scanner.nextInt();
                scanner.nextLine(); 

                for (int i = 0; i < numProducts; i++) {
                    System.out.println();
                    System.out.print("Masukkan nomor produk ke-" + (i + 1) + ": ");//nomor produk di list produknya
                    int productNumber = scanner.nextInt();
                    scanner.nextLine(); 

                    Product selectedProduct = productMap.get(productNumber);
                    if (selectedProduct != null) {
                        cashier.addProduct(selectedProduct);
                        System.out.println();
                        System.out.println("Produk berhasil ditambahkan ke keranjang!");
                    } else {
                        System.out.println();
                        System.out.println("Nomor produk tidak valid!");
                        i--; // decrement i untuk mengulang loop untuk produk yg sama
                    }
                }

                System.out.println();
                System.out.println("Struk Pembayaran:");

                LocalDateTime transactionTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");//waktu sekarang
                System.out.println();
                System.out.println("Waktu Transaksi: " + transactionTime.format(formatter));

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                cashier.displayProducts();
                int totalPrice = cashier.getTotalPrice();
                System.out.println("Total harga: " + currencyFormat.format(totalPrice));

                System.out.print("Total yang dibayarkan: ");
                int amountPaid = scanner.nextInt();
                scanner.nextLine();
                
                if (amountPaid >= totalPrice) {
                    int change = amountPaid - totalPrice;
                    System.out.println("Kembalian: " + currencyFormat.format(change));

                    System.out.println();
                    System.out.println("Terima kasih telah berbelanja!");
                }else {
                    System.out.println();
                    System.out.println("Maaf, uang Anda tidak mencukupi. Silahkan bayar dengan uang pas!");
                }

                // Reset cashier untuk transaksi selanjutnya
                cashier = new Cashier();
            }

            // Reset mode variables for the next run
            isAdminMode = false;
            backToMainMenu = false;
            
            System.out.println();
            System.out.print("Kembali ke menu utama (y/n)? ");
            String continueInput = scanner.nextLine();
            if (!continueInput.equalsIgnoreCase("y")) {
                break;
            }
        } while (true);
    }
}

