package com.hms.abstracts;

public abstract class HospitalMember {
    protected String name;
    protected int id;
    protected String department;

    public HospitalMember(String name, int id, String department) {
        this.name = name;
        this.id = id;
        this.department = department;
    }

    public abstract void displayInfo();

    public void commonDuty() {
        System.out.println(name + " is performing routine hospital duties in " + department + ".");
    }

    public void checkIn() {
        System.out.println(name + " has checked in.");
    }

    public void checkOut() {
        System.out.println(name + " has checked out.");
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void updateProfile(String newName, String newDepartment) {
        this.name = newName;
        this.department = newDepartment;
        System.out.println("Profile updated for ID: " + id);
    }
}
