package com.company;

import java.io.*;
import java.util.Scanner;

class inputMismatch extends Exception {
    public inputMismatch(String message) {
        super(message);
    }
}

public class Main {
    static final String USER_FILE = "C:\\Users\\prana\\OneDrive\\Desktop\\Hospital-Management-System-main\\Hospital_Management_System\\users.txt";

    static void inputWrong(int choice) throws inputMismatch {
        if (choice != 1 && choice != 2) {
            throw new inputMismatch("Enter valid option");
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int mainChoice, exitChoice;

        ensureUserFileExists();

        System.out.println("**********************************************************************");
        System.out.println("\t1. LOGIN");
        System.out.println("\t2. SIGN UP");
        System.out.println("**********************************************************************");
        System.out.print("Enter choice: ");
        int choice = input.nextInt();
        try {
            inputWrong(choice);
            input.nextLine();
        } catch (inputMismatch f) {
            System.out.println(f.getMessage());
            return;
        }

        String u_N = "", password = "";

        if (choice == 1) {
            System.out.print("\tUSER NAME:\t");
            u_N = input.nextLine();
            System.out.print("\tPASSWORD:\t");
            password = input.nextLine();
        } else if (choice == 2) {
            System.out.print("Choose a Username: ");
            u_N = input.nextLine();
            System.out.print("Choose a Password: ");
            password = input.nextLine();

            if (registerUser(u_N, password)) {
                System.out.println("Signup successful! Logging in now...");
            } else {
                System.out.println("Error: Username already exists.");
                return;
            }
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        if (authenticateUser(u_N, password)) {
            do {
                System.out.println("----------------------------------------------------------------------");
                System.out.println("          Welcome To Hospital Management System");
                System.out.println("----------------------------------------------------------------------");
                System.out.println("1.ADMIN\t2.DEPARTMENTS\t3.PHARMACY");
                System.out.print("Enter option: ");
                mainChoice = input.nextInt();

                switch (mainChoice) {
                    case 1:
                        Admin rec = new Admin();
                        rec.choose();
                        break;

                    case 2:
                        runDepartmentSection();
                        break;

                    case 3:
                        runPharmacySection();
                        break;

                    default:
                        System.out.println("Enter Valid Option From (1, 2, or 3)");
                        break;
                }

                System.out.println("To Exit System Press 1 (Any other number to return to Main Menu)");
                exitChoice = input.nextInt();
            } while (exitChoice != 1);
        } else {
            System.out.println("*** WRONG USERNAME OR PASSWORD ***");
        }
    }

    // Helper method clearly separating the Department logic
    public static void runDepartmentSection() {
        Scanner sc = new Scanner(System.in);
        Dep x = new Dep();
        DepartmentManager dm = new DepartmentManager();

        int deptChoice;
        do {
            System.out.println("\n==== DEPARTMENTS ====");
            System.out.println("1. Basic Department Info");
            System.out.println("2. Advanced Department Management (CRUD)");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choice: ");
            deptChoice = sc.nextInt();

            switch (deptChoice) {
                case 1:
                    x.Dep_Display();
                    break;
                case 2:
                    dm.runDepartmentManagerMenu();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Enter valid option (1,2,3)");
            }
        } while (true);
    }

    // Helper method clearly separating the Pharmacy logic
    public static void runPharmacySection() {
        Scanner sc = new Scanner(System.in);

        // Create a new Pharmacy object.
        Pharmacy p = new Pharmacy();
        // Create a PharmacyManager for advanced management.
        PharmacyManager pm = new PharmacyManager();
        // Create a Patient object that uses the Pharmacy.
        Patient patient = new Patient(p);

        int pharmChoice;
        do {
            System.out.println("\n==== PHARMACY ====");
            System.out.println("1. Basic Pharmacy Info");
            System.out.println("2. Advanced Pharmacy Management (CRUD)");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choice: ");
            pharmChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (pharmChoice) {
                case 1:
                    // Let the patient pick the department which sets the Pharmacy's file path.
                    patient.pickDepartmentAndLoadMeds();
                    // Then display the pharmacy data.
                    p.display();
                    break;
                case 2:
                    pm.runPharmacyManagerMenu();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Enter valid option (1,2,3)");
            }
        } while (true);
    }

    public static void ensureUserFileExists() {
        try {
            File file = new File(USER_FILE);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Failed to create user file: " + e.getMessage());
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] creds = line.split(",");
                if (creds.length == 2 && creds[0].equals(username) && creds[1].equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false;
    }

    public static boolean registerUser(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ",")) {
                    reader.close();
                    return false;
                }
            }
            reader.close();

            FileWriter fw = new FileWriter(USER_FILE, true);
            fw.write(username + "," + password + "\n");
            fw.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to user file: " + e.getMessage());
            return false;
        }
    }
}
