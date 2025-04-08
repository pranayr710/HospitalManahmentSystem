package com.company;

import java.io.*;
import java.util.*;

public class Pharmacy {

    // Record-like class to hold each medicine
    static class Medicine {
        String name;
        double cost;
        String expiry;
        String usage;

        public Medicine(String name, double cost, String expiry, String usage) {
            this.name = name;
            this.cost = cost;
            this.expiry = expiry;
            this.usage = usage;
        }

        @Override
        public String toString() {
            return name + "\t" + cost + "\t" + expiry + "\t" + usage;
        }
    }

    // List of medicines and the current file path
    private List<Medicine> meds = new ArrayList<>();
    private String currentFilePath;

    public Pharmacy() {
        // Optional pre-loaded sample medicines
        meds.add(new Medicine("Blood_Thinners", 500, "25/6/2025", "Prevention"));
        meds.add(new Medicine("lisinopril", 500, "23/9/2022", "Improve_heart_functioning"));
        meds.add(new Medicine("B", 20, "13", "cold"));
    }

    // NEW: Setter to update file path and load medicines from that file.
    public void setFilePath(String filePath) {
        this.currentFilePath = filePath;
        loadMedicinesFromFile(this.currentFilePath);
    }

    // Load medicines from a given file.
    private void loadMedicinesFromFile(String filePath) {
        File file = new File(filePath);
        meds.clear();
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean headerSkipped = false;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    
                    // Skip header line if it contains "name" (case-insensitive)
                    if (!headerSkipped && line.toLowerCase().contains("name")) {
                        headerSkipped = true;
                        continue;
                    }
                    
                    // Remove any leading/trailing square brackets
                    if (line.startsWith("[") || line.startsWith("]")) {
                        line = line.replaceAll("^[\\[\\]]+", "").trim();
                    }
                    
                    // Split using one or more whitespace characters.
                    String[] parts = line.split("\\s+");
                    
                    // Check for an extra token (e.g., a leading comma) and adjust offset.
                    int offset = 0;
                    if (parts[0].equals(",") || parts[0].startsWith(",")) {
                        offset = 1;
                    }
                    
                    if (parts.length < offset + 4) {
                        System.out.println("Skipping line due to insufficient fields: " + line);
                        continue;
                    }
                    
                    try {
                        String name = parts[offset].trim();
                        String costStr = parts[offset + 1].trim();
                        if (costStr.isEmpty()) {
                            System.out.println("Skipping medicine with empty cost: " + name);
                            continue;
                        }
                        double cost = Double.parseDouble(costStr);
                        String expiry = parts[offset + 2].trim();
                        
                        // Join all remaining tokens for the usage field (in case it contains spaces)
                        StringBuilder usageBuilder = new StringBuilder();
                        for (int i = offset + 3; i < parts.length; i++) {
                            usageBuilder.append(parts[i]).append(" ");
                        }
                        String usage = usageBuilder.toString().trim();
                        
                        Medicine med = new Medicine(name, cost, expiry, usage);
                        meds.add(med);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing cost for medicine: " + parts[offset]);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }


    // Save the current list of medicines back to file.
    private void saveMedicinesToFile(String filePath) {
        File file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Write header
            bw.write("Name\tCost\tExpiry Date\tUsed For:");
            bw.newLine();
            for (Medicine m : meds) {
                bw.write(m.name + "\t" + m.cost + "\t" + m.expiry + "\t" + m.usage);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Main display method for pharmacy operations.
    public void display() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*******  PHARMACY  *********");
        System.out.println("1.Add New Medicine\t2.Search Medicine List\t3.View All Medicines\t4.Update Medicine\t5.Delete Medicine");
        System.out.print("Enter Option:\t");
        int opt = sc.nextInt();
        sc.nextLine();
        switch (opt) {
            case 1:
                addNewMedicine();
                break;
            case 2:
                System.out.println("View/Search Medicines of:");
                System.out.println("1.Cardiology");
                System.out.println("2.Neurology");
                System.out.println("3.Eye");
                System.out.println("4.Dental");
                System.out.println("5.ICU");
                int depChoice = sc.nextInt();
                sc.nextLine();
                searchMedicinesByDepartment(depChoice);
                break;
            case 3:
                viewAllMedicines();
                break;
            case 4:
                updateMedicine();
                break;
            case 5:
                deleteMedicine();
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }
        System.out.println("To Exit Pharmacy press 0");
        int exitVal = sc.nextInt();
        if (exitVal == 0) {
            System.out.println("Exiting Pharmacy...");
        }
    }

    private void addNewMedicine() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Medicine Name:");
        String name = sc.nextLine();
        System.out.println("Enter Cost:");
        double cost = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter Expiry Date (dd/mm/yyyy):");
        String expiry = sc.nextLine();
        System.out.println("Enter Usage (Used For):");
        String usage = sc.nextLine();
        Medicine newMed = new Medicine(name, cost, expiry, usage);
        meds.add(newMed);
        saveMedicinesToFile(currentFilePath);
        System.out.println("Medicine added successfully!");
    }

    private void searchMedicinesByDepartment(int depChoice) {
        System.out.println("[Name\tCost\tExpiry Date\tUsed For:]");
        for (Medicine m : meds) {
            // For example, for cardiology you might filter by "heart" or "blood"
            switch (depChoice) {
                case 1 -> { // Cardiology filter
                    if (m.usage.toLowerCase().contains("heart") || m.usage.toLowerCase().contains("blood"))
                        System.out.println(m);
                }
                default -> System.out.println(m);
            }
        }
    }

    public void viewAllMedicines() {
        loadMedicinesFromFile(currentFilePath);
        if (meds.isEmpty()) {
            System.out.println("No medicines in stock!");
            return;
        }
        System.out.println("List of all medicines:");
        for (Medicine m : meds) {
            System.out.println(m);
        }
    }

    public void updateMedicine() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of medicine to update:");
        String name = sc.nextLine();
        Medicine found = null;
        for (Medicine m : meds) {
            if (m.name.equalsIgnoreCase(name)) {
                found = m;
                break;
            }
        }
        if (found == null) {
            System.out.println("Medicine not found.");
            return;
        }
        System.out.println("Current cost: " + found.cost);
        System.out.println("Enter new cost (or -1 to skip):");
        double newCost = sc.nextDouble();
        sc.nextLine();
        if (newCost >= 0) {
            found.cost = newCost;
        }
        System.out.println("Current expiry date: " + found.expiry);
        System.out.println("Enter new expiry date (or blank to skip):");
        String newExp = sc.nextLine();
        if (!newExp.isBlank()) {
            found.expiry = newExp;
        }
        System.out.println("Current usage: " + found.usage);
        System.out.println("Enter new usage (or blank to skip):");
        String newUsage = sc.nextLine();
        if (!newUsage.isBlank()) {
            found.usage = newUsage;
        }
        saveMedicinesToFile(currentFilePath);
        System.out.println("Medicine updated successfully!");
    }

    public void deleteMedicine() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of medicine to delete:");
        String name = sc.nextLine();
        Medicine found = null;
        for (Medicine m : meds) {
            if (m.name.equalsIgnoreCase(name)) {
                found = m;
                break;
            }
        }
        if (found == null) {
            System.out.println("Medicine not found.");
            return;
        }
        meds.remove(found);
        saveMedicinesToFile(currentFilePath);
        System.out.println("Medicine removed successfully!");
    }
}
