package org.me.gcu.medconnect.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TextExtractionUtils {

    public static String extractMedicationName(String text) {
        String[] lines = text.split("\n");
        return lines.length > 0 ? lines[0].trim() : "MedicationName";
    }

    public static String extractDosage(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.matches("\\d+X\\d+")) {
                return line.split("X")[0].trim();
            }
        }
        return "Dosage";
    }

    public static String extractFrequency(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.matches("\\d+X\\d+")) {
                return line.split("X")[1].trim();
            }
        }
        return "Frequency";
    }

    public static String extractInstructions(String text) {
        String[] lines = text.split("\n");
        return lines.length > 3 ? lines[3].trim() : "Instructions";
    }

    public static String extractStartDate(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String extractEndDate(String text) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return sdf.format(calendar.getTime());
    }

    public static String extractRefillDate(String text) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, 9); // 7 days + 2 days for refill
        return sdf.format(calendar.getTime());
    }
}
