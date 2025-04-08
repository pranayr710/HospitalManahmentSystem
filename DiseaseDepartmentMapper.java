package com.hms.utils;

import java.util.ArrayList;
import java.util.List;

public class DiseaseDepartmentMapper {

    public static String getDepartment(String disease) {
        String lowerDisease = disease.toLowerCase();
        if(lowerDisease.contains("eye"))
            return "Eye";
        else if(lowerDisease.contains("heart"))
            return "Cardiology";
        else if(lowerDisease.contains("brain"))
            return "Neurology";
        else if(lowerDisease.contains("cold"))
            return "General";  // You can choose a different department if desired
        else
            return "Lab";      // Default department
    }

    public static List<String> getMedicines(String disease) {
        List<String> meds = new ArrayList<>();
        String lowerDisease = disease.toLowerCase();
        if(lowerDisease.contains("eye")) {
            meds.add("Eye_Ointment");
            meds.add("Azelstine");
            meds.add("Acetylzolamide");
        } else if(lowerDisease.contains("heart")) {
            meds.add("Loprin");
            meds.add("Statins");
        } else if(lowerDisease.contains("brain")) {
            meds.add("Diazepam");
            meds.add("Atomoxetine");
            meds.add("Alprazolam");
        } else if(lowerDisease.contains("cold")) {
            meds.add("Paracetamol");
            meds.add("Ibuprofen");
        } else {
            meds.add("DefaultMed");
        }
        return meds;
    }
}
