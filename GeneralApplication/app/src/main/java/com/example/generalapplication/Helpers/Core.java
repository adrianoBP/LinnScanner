package com.example.generalapplication.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.generalapplication.Classes.InventoryStockLocation;
import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.generalapplication.APIHelper.Internal.AuthorizeByApplication;
import static com.example.generalapplication.APIHelper.Internal.GetStockLocations;

public class Core {

    public static List<OrderDetails> allOrders;
    public static List<InventoryStockLocation> allLocations;
    public static List<String> allBarcodes;

    public static void CoreInit(Context context){
        allLocations = new ArrayList<>();
        allOrders = new ArrayList<>();
        allBarcodes = new ArrayList<>();

        GetStockLocations(context);

    }

    //region SharedPreferences
    public static String ReadPreference(Context context, String preferenceName){
        if(!CheckPreference(context, preferenceName)){ return null; }
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_destination), Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, "");
    }
    public static void WritePreference(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_destination), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }
    public static void ClearPreference(Context context, String preferenceName){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_destination), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, "");
        editor.apply();
    }
    public static boolean CheckPreference(Context context, String preferenceName){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_destination), Context.MODE_PRIVATE);
        return sharedPreferences.contains(preferenceName);
    }
    //endregion

    public static boolean IsNullOrEmpty(String string){
        return string.equals("null") || string.isEmpty() || TextUtils.isEmpty(string);
    }

    public static void HideKeyboard(View view, Context context){
        if(view != null){
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void CheckLoginSaved(Context context){
        String loginPreference = ReadPreference(context, context.getString(R.string.preference_saveLogin));
        if(loginPreference == null){
            return;
        }
        if(!IsNullOrEmpty(loginPreference) && loginPreference.equals("YES")){
            AuthorizeByApplication(context, true);
        }
    }

    public static UUID GetPreferredLocationUUIDfromName(Context context){

        String locationPreference = ReadPreference(context, context.getString(R.string.preference_ordersViewLocation));

        if(locationPreference == null){
            return new UUID(0L, 0L);
        }

        for (InventoryStockLocation location :
                allLocations) {
            if(location.LocationName.equals(locationPreference)){
                return location.StockLocationId;
            }
        }

        return new UUID(0L, 0L);

    }

}
