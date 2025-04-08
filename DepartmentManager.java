package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartmentManager {

    // Inner class to hold department information
    private static class DepartmentRecord {
        String name;
        String headDoctor;
        int availableSlots;

        public DepartmentRecord(String name, String headDoctor, int availableSlots) {
            this.name = name;
            this.headDoctor = headDoctor;
            this.availableSlots = availableSlots;
        }

        @Override
        public String toString() {
            return "Department: " + name + ", Head Doctor: " + headDoctor + ", Available Slots: " + availableSlots;
        }
    }

    private List<DepartmentRecord> departments;

    // Constructor preloads some default departments.
    public DepartmentManager() {
        departments = new ArrayList<>();
        departments.add(new DepartmentRecord("Cardiology", "Dr. Heart", 10));
        departments.add(new DepartmentRecord("Neurology", "Dr. Brain", 5));
        departments.add(new DepartmentRecord("Eye", "Dr. Vision", 8));
        departments.add(new DepartmentRecord("Dental", "Dr. Teeth", 6));
        departments.add(new DepartmentRecord("Lab", "Dr. Analyst", 12));
    }

    public void runDepartmentManagerMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n==== Department Manager ====");
            System.out.println("1. View Departments");
            System.out.println("2. Add Department");
            System.out.println("3. Remove Department");
            System.out.println("4. Edit Department");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewDepartments();
                    break;
                case 2:
                    addDepartment();
                    break;
                case 3:
                    removeDepartment();
                    break;
                case 4:
                    editDepartment();
                    break;
                case 5:
                    System.out.println("Returning to previous menu...");
                    break;
                default:
                    System.out.println("Enter valid option (1-5)");
            }
        } while (choice != 5);
    }

    private void viewDepartments() {
        if (departments.isEmpty()) {
            System.out.println("No departments available.");
        } else {
            System.out.println("===== All Departments =====");
            int index = 1;
            for (DepartmentRecord dr : departments) {
                System.out.println(index + ". " + dr.toString());
                index++;
            }
        }
    }

    private void addDepartment() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new department name:");
        String name = sc.nextLine();
        System.out.println("Enter head doctor name:");
        String headDoctor = sc.nextLine();
        System.out.println("Enter available slots:");
        int slots = sc.nextInt();
        sc.nextLine(); // Consume newline
        departments.add(new DepartmentRecord(name, headDoctor, slots));
        System.out.println("Department '" + name + "' added successfully!");
    }

    private void removeDepartment() {
        viewDepartments();
        if (departments.isEmpty()) {
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the department to remove:");
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 1 || index > departments.size()) {
            System.out.println("Invalid department number.");
        } else {
            DepartmentRecord removed = departments.remove(index - 1);
            System.out.println("Department '" + removed.name + "' removed successfully!");
        }
    }

    private void editDepartment() {
        viewDepartments();
        if (departments.isEmpty()) {
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the department to edit:");
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 1 || index > departments.size()) {
            System.out.println("Invalid department number.");
        } else {
            DepartmentRecord dr = departments.get(index - 1);
            System.out.println("Editing department: " + dr.toString());
            System.out.println("Enter new department name (or press Enter to keep [" + dr.name + "]):");
            String newName = sc.nextLine();
            if (!newName.isBlank()) {
                dr.name = newName;
            }
            System.out.println("Enter new head doctor name (or press Enter to keep [" + dr.headDoctor + "]):");
            String newHead = sc.nextLine();
            if (!newHead.isBlank()) {
                dr.headDoctor = newHead;
            }
            System.out.println("Enter new available slots (or press Enter to keep [" + dr.availableSlots + "]):");
            String slotsStr = sc.nextLine();
            if (!slotsStr.isBlank()) {
                try {
                    int newSlots = Integer.parseInt(slotsStr);
                    dr.availableSlots = newSlots;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for slots. Keeping previous value.");
                }
            }
            System.out.println("Department updated successfully: " + dr.toString());
        }
    }
}
