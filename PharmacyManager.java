package com.company;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class PharmacyManager {

    // Map to store medicine data, key: medicine name, value: [stock, price]
    private final Map<String, double[]> medicines = new LinkedHashMap<>();

    public PharmacyManager() {
        // Sample data for initialization
        medicines.put("Paracetamol", new double[]{50, 10.0});
        medicines.put("Ibuprofen", new double[]{30, 15.0});
        medicines.put("Cough Syrup", new double[]{20, 40.0});
        medicines.put("Antibiotic", new double[]{25, 30.0});
    }

    // Method to view the list of medicines
    public void viewMedicines() {
        if (medicines.isEmpty()) {
            System.out.println("No medicines found!");
            return;
        }
        System.out.println("===== Pharmacy Inventory =====");
        for (Map.Entry<String, double[]> entry : medicines.entrySet()) {
            String medName = entry.getKey();
            double[] data = entry.getValue();
            System.out.println("Medicine: " + medName + ", Stock: " + (int) data[0] + ", Price: Rs." + data[1]);
        }
    }

    // Method to add or update a medicine
    public void addOrUpdateMedicine() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter medicine name:");
        String medName = sc.nextLine();
        System.out.println("Enter quantity:");
        int qty = sc.nextInt();
        System.out.println("Enter price:");
        double price = sc.nextDouble();
        sc.nextLine(); // consume newline

        if (medicines.containsKey(medName)) {
            double[] oldData = medicines.get(medName);
            oldData[0] += qty;  // Add to existing stock
            oldData[1] = price; // Update price
            medicines.put(medName, oldData);
            System.out.println("Updated existing medicine: " + medName);
        } else {
            medicines.put(medName, new double[]{qty, price});
            System.out.println("Added new medicine: " + medName);
        }
    }

    // Method to sell medicine
    public void sellMedicine() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter medicine name:");
        String medName = sc.nextLine();
        if (!medicines.containsKey(medName)) {
            System.out.println("Medicine not found!");
            return;
        }
        System.out.println("Enter quantity to sell:");
        int qty = sc.nextInt();
        sc.nextLine(); // consume newline

        double[] data = medicines.get(medName);
        int currentStock = (int) data[0];
        double price = data[1];

        if (currentStock < qty) {
            System.out.println("Insufficient stock! Only " + currentStock + " available.");
            return;
        }

        // Process the sale
        data[0] = currentStock - qty; // Update stock
        medicines.put(medName, data);

        double totalCost = qty * price;
        System.out.println("Sold " + qty + " of " + medName + " for Rs." + totalCost);
    }

    // Pharmacy Manager Menu
    public void runPharmacyManagerMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== Pharmacy Manager ====");
            System.out.println("1. View Medicines");
            System.out.println("2. Add/Update Medicine");
            System.out.println("3. Sell Medicine");
            System.out.println("4. Back");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewMedicines();
                    break;
                case 2:
                    addOrUpdateMedicine();
                    break;
                case 3:
                    sellMedicine();
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    return; // Exit to the main menu
                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }
}