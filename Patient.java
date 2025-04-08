package com.company;

import com.hms.utils.DiseaseDepartmentMapper;
import com.hms.threads.ThreadManager;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient {

    // Reference to the Pharmacy instance
    private Pharmacy pharmacy;

    // Constructor to inject the Pharmacy
    public Patient(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public void addPatient() {
        Scanner input = new Scanner(System.in);

        // 1) Collect Patient Info
        System.out.println("Enter Name:");
        String name = input.nextLine();

        System.out.println("Enter Age:");
        int age = input.nextInt();
        input.nextLine(); // consume leftover newline

        System.out.println("Enter Gender (M/F):");
        char gender = input.next().charAt(0);
        input.nextLine();

        System.out.println("Enter Disease:");
        String disease = input.nextLine();

        // 2) Determine Department & Recommended Medicines
        String department = DiseaseDepartmentMapper.getDepartment(disease);
        List<String> recommendedMeds = DiseaseDepartmentMapper.getMedicines(disease);

        System.out.println("Recommended department: " + department);
        System.out.println("Recommended medicines: " + recommendedMeds);

        // 3) Prompt User to Confirm or Override Department
        System.out.println("Do you want to assign this department? (Y/N)");
        char confirm = input.next().charAt(0);
        input.nextLine();

        if (confirm == 'N' || confirm == 'n') {
            System.out.println("Select Department:\n1. Cardiology\n2. Neurology\n3. Eye\n4. Dental\n5. Lab");
            int choice = input.nextInt();
            input.nextLine();
            department = switch (choice) {
                case 1 -> "Cardiology";
                case 2 -> "Neurology";
                case 3 -> "Eye";
                case 4 -> "Dental";
                case 5 -> "Lab";
                default -> department; // fallback to recommended
            };
        }

        // 4) Set the corresponding pharmacy file based on department
        setPharmacyDepartment(department);

        // 5) Department Services
        List<String> deptOptions = getDeptOptions(department);
        List<Integer> selectedOptions = new ArrayList<>();

        if (!deptOptions.isEmpty()) {
            System.out.println("Available services in " + department + ":");
            for (int i = 0; i < deptOptions.size(); i++) {
                System.out.println((i + 1) + ". " + deptOptions.get(i));
            }

            System.out.println("Select service(s) by number (comma separated):");
            String[] services = input.nextLine().split(",");
            for (String s : services) {
                try {
                    selectedOptions.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, skipping: " + s);
                }
            }
        }

        // 6) Calculate Bill
        double bill = calculateBill(department, selectedOptions);
        System.out.println("Estimated Bill: Rs. " + bill);

        // 7) Write Patient Record to File
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String patientFile = department + "_Patients.txt";
        try (FileWriter fw = new FileWriter(patientFile, true)) {
            fw.write("[" + timestamp + "] Name: " + name + ", Age: " + age + ", Gender: " + gender
                     + ", Disease: " + disease + ", Medicines: " + recommendedMeds
                     + ", Bill: Rs." + bill + "\n");
            System.out.println("Patient record stored in " + patientFile);
        } catch (IOException e) {
            System.out.println("Failed to write patient record.");
        }

        // 8) Define Parallel Tasks
        final String finalDept = department;
        Runnable saveLogTask = () -> {
            System.out.println("[TASK START] Save Log for " + name);
            try {
                Thread.sleep(1200);
                System.out.println("[TASK COMPLETE] Save Log for " + name);
            } catch (InterruptedException e) {
                System.err.println("[TASK INTERRUPTED] Save Log for " + name);
            }
        };

        Runnable notifyDeptTask = () -> {
            System.out.println("[TASK START] Notify " + finalDept + " Department");
            try {
                Thread.sleep(1500);
                System.out.println("[TASK COMPLETE] Notify " + finalDept + " Department");
            } catch (InterruptedException e) {
                System.err.println("[TASK INTERRUPTED] Notify " + finalDept + " Department");
            }
        };

        Runnable createBackupTask = () -> {
            System.out.println("[TASK START] Create Backup Entry for " + name);
            try {
                Thread.sleep(1000);
                System.out.println("[TASK COMPLETE] Create Backup Entry for " + name);
            } catch (InterruptedException e) {
                System.err.println("[TASK INTERRUPTED] Create Backup Entry for " + name);
            }
        };

        // 9) Run all tasks in parallel using our ThreadManager stub.
        ThreadManager.runInParallel(saveLogTask, notifyDeptTask, createBackupTask);

        System.out.println("Returning to main menu...\n");
    }

    // Helper: set the Pharmacy's file based on the department
    private void setPharmacyDepartment(String department) {
        switch (department.toLowerCase()) {
            case "neurology", "brain" -> {
                pharmacy.setFilePath("C:\\Users\\prana\\Music\\Hospital_Management_System\\Brain_Medicines.txt");
                System.out.println("Brain medicines loaded from file!");
            }
            case "cardiology", "cardiac" -> {
                pharmacy.setFilePath("C:\\Users\\prana\\Music\\Hospital_Management_System\\Cardiac_Medicines.txt");
                System.out.println("Cardiac medicines loaded from file!");
            }
            case "eye" -> {
                pharmacy.setFilePath("C:\\Users\\prana\\Music\\Hospital_Management_System\\Eye_Medicines.txt");
                System.out.println("Eye medicines loaded from file!");
            }
            case "dental" -> {
                pharmacy.setFilePath("C:\\Users\\prana\\Music\\Hospital_Management_System\\Dental_Medicines.txt");
                System.out.println("Dental medicines loaded from file!");
            }
            default -> System.out.println("No corresponding medicine file for department: " + department);
        }
    }

    // Public method to allow external calls (e.g., from Main) for department selection.
    public void pickDepartmentAndLoadMeds() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select Department for the patient:");
        System.out.println("1. Brain");
        System.out.println("2. Cardiac");
        System.out.println("3. Eye");
        System.out.println("4. Dental");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        switch (choice) {
            case 1:
                pharmacy.setFilePath("C:\\Users\\prana\\Videos\\Hospital_Management_System\\Brain_Medicines.txt");
                System.out.println("Brain medicines loaded from file!");
                break;
            case 2:
                pharmacy.setFilePath("C:\\Users\\prana\\Videos\\Hospital_Management_System\\Cardiac_Medicines.txt");
                System.out.println("Cardiac medicines loaded from file!");
                break;
            case 3:
                pharmacy.setFilePath("C:\\Users\\prana\\Videos\\Hospital_Management_System\\Eye_Medicines.txt");
                System.out.println("Eye medicines loaded from file!");
                break;
            case 4:
                pharmacy.setFilePath("C:\\Users\\prana\\Videos\\Hospital_Management_System\\Dental_Medicines.txt");
                System.out.println("Dental medicines loaded from file!");
                break;
            default:
                System.out.println("Invalid choice; no file path set!");
                break;
        }
    }

    private List<String> getDeptOptions(String department) {
        return switch (department.toLowerCase()) {
            case "lab" -> Arrays.asList("Blood Test", "X-Ray", "Covid Test", "Urine Test");
            case "dental" -> Arrays.asList("Cleaning", "Extraction", "Braces", "Whitening");
            case "eye" -> Arrays.asList("Eye Test", "Laser Correction", "Prescription Glasses", "Cataract Surgery");
            default -> new ArrayList<>();
        };
    }

    private double calculateBill(String department, List<Integer> services) {
        double total = 0;
        for (int index : services) {
            switch (department.toLowerCase()) {
                case "lab" -> total += switch (index) {
                    case 1 -> 300;
                    case 2 -> 800;
                    case 3 -> 500;
                    case 4 -> 200;
                    default -> 0;
                };
                case "dental" -> total += switch (index) {
                    case 1 -> 600;
                    case 2 -> 1000;
                    case 3 -> 2000;
                    case 4 -> 1200;
                    default -> 0;
                };
                case "eye" -> total += switch (index) {
                    case 1 -> 200;
                    case 2 -> 1500;
                    case 3 -> 700;
                    case 4 -> 2500;
                    default -> 0;
                };
                default -> total += 0;
            }
        }
        return total;
    }

    // Inner class to view patient records.
    public class ViewPatients {
        Scanner input = new Scanner(System.in);

        public void viewPatient() {
            System.out.println("View Patient list of:\n1. Cardiology\n2. Neurology\n3. Eye\n4. Dental\n5. Lab");
            int s = input.nextInt();
            input.nextLine();
            String fileName;
            switch (s) {
                case 1 -> fileName = "Cardiology_Patients.txt";
                case 2 -> fileName = "Neurology_Patients.txt";
                case 3 -> fileName = "Eye_Patients.txt";
                case 4 -> fileName = "Dental_Patients.txt";
                case 5 -> fileName = "Lab_Patients.txt";
                default -> {
                    System.out.println("Enter a valid option (1-5).");
                    return;
                }
            }
            try {
                File myObj = new File(fileName);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Patient file not found: " + fileName);
            }
        }
    }
}
