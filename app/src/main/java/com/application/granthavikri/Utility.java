package com.application.granthavikri;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Utility {

    public static String tag = "debug";

    public static String app_date_format = "dd-MM-yyyy";

    public static List<String> types = new ArrayList<>(Arrays.asList("Select Type", "Book", "CD", "Frame", "Group"));

    public static List<String> payment_method = new ArrayList<>(Arrays.asList("Select Payment Method", "UPI", "Cash"));

    public static HashMap<String, String> kendraMap = new HashMap<>();

    private static String kendra;

    public static String getKendra() {
        return kendra;
    }

    public static void setKendra(String kendra) {
        Utility.kendra = kendra;
    }

    public static String getCurrentDateTime() {
        // Create a Date object representing the current date and time

        Date currentDate = new Date();

        // Create a SimpleDateFormat object to format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the date and time as a string
        String formattedDateTime = dateFormat.format(currentDate);

        return formattedDateTime;
    }

    public static String setFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }


    public static String changeDateFormat(String inputDate, String current_format, String new_format){
        SimpleDateFormat inputFormat = new SimpleDateFormat(current_format);
        SimpleDateFormat outputFormat = new SimpleDateFormat(new_format);
        String outputDate = "";
        try {
            Date date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static void setupSpinner(Context context, Spinner spinner, List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item_layout, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public static void clear_field(EditText editText, String hint){
        editText.setText("");
        editText.setHint(hint);
    }

    public static float round_off_two(float floatValue){
        float roundedValue = Math.round(floatValue * 100.0f) / 100.0f;
        return roundedValue;
    }

    public interface OnDataFetchedListener<T> {
        void onDataFetched(List<T> data);
        void onError(Exception e);
    }

    public interface FirestoreCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    public interface OnUploadListener{
        void onSuccess(String imageUrl);
        void onFailure(Exception e);
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static boolean areEqualStrings(String str1, String str2){
        int check = str1.compareTo(str2);
        return check==0;
    }
}
