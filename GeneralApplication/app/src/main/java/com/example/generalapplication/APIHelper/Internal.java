package com.example.generalapplication.APIHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.generalapplication.Activities.MainActivity;
import com.example.generalapplication.Classes.CustomSource;
import com.example.generalapplication.Classes.FieldCode;
import com.example.generalapplication.Classes.FieldSorting;
import com.example.generalapplication.Classes.FieldsFilter;
import com.example.generalapplication.Classes.InventoryStockLocation;
import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.Classes.PrinterStatus;
import com.example.generalapplication.Classes.TextFieldFilter;
import com.example.generalapplication.Classes.TextFieldFilterType;
import com.example.generalapplication.Classes.VirtualPrinter;
import com.example.generalapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.generalapplication.APIHelper.External.RetrieveUserInformation;
import static com.example.generalapplication.Activities.PrintActivity.sPrinters;
import static com.example.generalapplication.Adapters.OrderAdapter.multiSelectedOrders;
import static com.example.generalapplication.Helpers.Core.GetLocationNames;
import static com.example.generalapplication.Helpers.Core.GetPreferredLocationUUIDfromName;
import static com.example.generalapplication.Helpers.Core.allBarcodes;
import static com.example.generalapplication.Helpers.Core.allLocations;
import static com.example.generalapplication.Helpers.Core.allOrders;
import static com.example.generalapplication.Helpers.Core.allPrinters;
import static com.example.generalapplication.Helpers.Core.allSources;
import static com.example.generalapplication.Helpers.Parser.ParseJSONToOrderDetails;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;
import static com.example.generalapplication.Helpers.Core.IsNullOrEmpty;
import static com.example.generalapplication.Helpers.Core.ReadPreference;
import static com.example.generalapplication.Helpers.Core.WritePreference;
import static com.example.generalapplication.Helpers.UI.orderAdapter;

public class Internal {

    public static void RetrieveUserId(final String email, final String password, final Context context){

        final String logLocation = "API.INT.RETRIEVEUSERID";

        String url = context.getString(R.string.linnworks_generalApi_IP) + "/Auth/MultiLogin";

        StringRequest retrieveUIDRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        boolean displayError = false;

                        try{
                            JSONObject responseObject = new JSONArray(response).getJSONObject(0);
                            String userId = responseObject.getString("UserId");

                            if(!TextUtils.isEmpty(userId)){
                                WritePreference(context, context.getString(R.string.preference_userId), userId);
                                RetrieveUserInformation(context);
                            }else{
                                displayError = true;
                            }

                        }catch (JSONException jex){
                            displayError = true;
                            Log.e(logLocation, jex.getMessage());
                        }catch (Exception ex){
                            displayError = true;
                            Log.e(logLocation, ex.getMessage());
                        }finally {
                            if (displayError) {
                                CreateBasicSnack("Unable to authorize the user!", null, context);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try{
                            JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            String errorMessage =  errorObject.getString("Message");
                            Log.e(logLocation, errorMessage);
                            CreateBasicSnack(errorMessage, null, context);
                        }catch (Exception ex){
                            Log.e(logLocation, ex.getMessage());
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userName", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(retrieveUIDRequest);

    }


    public static void AuthorizeByApplication(final Context context){
        AuthorizeByApplication(context, false);
    }

    public static void AuthorizeByApplication(final Context context, final boolean startApplication){

        final String logLocation = "API.INT.AUTHBYAPP";

        String url = context.getString(R.string.linnworks_generalApi_IP) + "/Auth/AuthorizeByApplication";

        StringRequest retrieveUIDRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        boolean displayError = false;

                        try{
                            JSONObject responseObject = new JSONObject(response);

                            String server = responseObject.getString("Server");
                            String token = responseObject.getString("Token");

                            if(!IsNullOrEmpty(server) && !IsNullOrEmpty(token)){
                                WritePreference(context, context.getString(R.string.preference_linnworksServer), server);
                                WritePreference(context, context.getString(R.string.preference_linnworksToken), token);

                                // TODO: change this ( option / setting  )
                                WritePreference(context, context.getString(R.string.preference_saveLogin), "YES");

                                if(startApplication){
                                    Intent mainIntent = new Intent(context, MainActivity.class);
                                    ((Activity)context).finish();
                                    context.startActivity(mainIntent);
                                }

                            }else{
                                displayError = true;
                            }
                        }catch (JSONException jex){
                            displayError = true;
                            Log.e(logLocation, jex.getMessage());
                        }catch (Exception ex){
                            displayError = true;
                            Log.e(logLocation, ex.getMessage());
                        }finally {
                            if (displayError) {
                                CreateBasicSnack("Unable to authorize the user!", null, context);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        try{
                            JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            String errorMessage =  errorObject.getString("Message");
                            Log.e(logLocation, errorMessage);
                            CreateBasicSnack(errorMessage, null, context);
                        }catch (Exception ex){
                            Log.e(logLocation, ex.getMessage());
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("applicationId", context.getString(R.string.applicationId));
                params.put("applicationSecret", context.getString(R.string.applicationSecret));
                params.put("token", ReadPreference(context, context.getString(R.string.preference_userInstallationToken)));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(retrieveUIDRequest);

    }

    public static void GetOpenOrders(final Context context, final Integer entriesPerPage, final Integer pageNumber, final FieldsFilter filters, final List<FieldSorting> sorting, final UUID fulfilmentCenter, final String additionalFilter){

        final String logLocation = "API.INT.GETOO";

        String url = ReadPreference(context, context.getString(R.string.preference_linnworksServer)) + "/api/Orders/GetOpenOrders";

        StringRequest retrieveUIDRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        boolean displayError = false;

                        try{

                            JSONObject responseObject = new JSONObject(response);

                            int totalEntries = responseObject.getInt("TotalEntries");

                            if(totalEntries > 0){
                                JSONArray data = responseObject.getJSONArray("Data");

                                for(int i = 0; i<data.length(); i++){
                                    JSONObject orderObject = data.getJSONObject(i);
                                    OrderDetails order = ParseJSONToOrderDetails(orderObject);
                                    allOrders.add(order);
                                }

                                orderAdapter.notifyDataSetChanged();

                            }

                        }catch (JSONException jex){
                            displayError = true;
                            Log.e(logLocation, jex.getMessage());
                        }catch (Exception ex){
                            displayError = true;
                            Log.e(logLocation, ex.getMessage());
                        }finally {
                            if (displayError) {
                                CreateBasicSnack("Unable to authorize the user!", null, context);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try{
                            JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            String errorMessage =  errorObject.getString("Message");
                            Log.e(logLocation, errorMessage);
                            CreateBasicSnack(errorMessage, null, context);
                        }catch (Exception ex){
                            Log.e(logLocation, ex.getMessage());
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                params.put("Authorization", ReadPreference(context, context.getString(R.string.preference_linnworksToken)));
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("entriesPerPage", String.valueOf(entriesPerPage));
                params.put("pageNumber", String.valueOf(pageNumber));
                params.put("filters", filters == null ? "" : filters.toString());
                params.put("sorting", sorting == null ? "" : sorting.toString());
                params.put("fulfilmentCenter", fulfilmentCenter == null ? "00000000-0000-0000-0000-000000000000" : fulfilmentCenter.toString());
                params.put("additionalFilter", additionalFilter == null ? "" : additionalFilter);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(retrieveUIDRequest);
    }



    // TODO: option to search on processed orders ( SQL )
    public static void GetAllOrdersByBarcodes(final Context context, Boolean clearOrders, final Boolean finishActivity){

        final String logLocation = "API.INT.GETOO";

        String url = ReadPreference(context, context.getString(R.string.preference_linnworksServer)) + "/api/Orders/GetOpenOrders";

        if(clearOrders)
            allOrders = new ArrayList<>();

        multiSelectedOrders = new ArrayList<>();


        final List<UUID> activeCalls = new ArrayList<>();

        for(final String barcode : allBarcodes){

            final UUID currentCallID = UUID.randomUUID();
            activeCalls.add(currentCallID);

            final Integer entriesPerPage = 200;
            final Integer pageNumber = 1;

            final FieldsFilter filters = new FieldsFilter();
            filters.TextFields = new ArrayList<>();
            TextFieldFilter textFieldFilter = new TextFieldFilter();
            textFieldFilter.FieldCode = FieldCode.SHIPPING_INFORMATION_TRACKING_NUMBER;
            textFieldFilter.Text = barcode;
            textFieldFilter.Type = TextFieldFilterType.Equal;
            filters.TextFields.add(textFieldFilter);

//            final FieldsFilter filters = new FieldsFilter(){{
//                TextFields = new ArrayList<TextFieldFilter>(){{
//                    new TextFieldFilter(){
//                        {
//                            Type = TextFieldFilterType.Equal;
//                            Text = barcode;
//                            FieldCode = com.example.generalapplication.Classes.FieldCode.SHIPPING_INFORMATION_TRACKING_NUMBER;
//                        }
//                    };
//                }};
//            }};

//            final UUID fulfilmentCenter = new UUID(0L, 0L);
            final UUID fulfilmentCenter = GetPreferredLocationUUIDfromName(context);


            StringRequest retrieveUIDRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            boolean displayError = false;

                            try{

                                JSONObject responseObject = new JSONObject(response);

                                int totalEntries = responseObject.getInt("TotalEntries");

                                if(totalEntries > 0){
                                    JSONArray data = responseObject.getJSONArray("Data");

                                    for(int i = 0; i<data.length(); i++){
                                        JSONObject orderObject = data.getJSONObject(i);
                                        OrderDetails order = ParseJSONToOrderDetails(orderObject);

                                        allOrders.add(order);
                                    }
                                }

                            }catch (JSONException jex){
                                displayError = true;
                                Log.e(logLocation, jex.getMessage());
                            }catch (Exception ex){
                                displayError = true;
                                Log.e(logLocation, ex.getMessage());
                            }finally {
                                if (displayError) {
                                    CreateBasicSnack("Unable to authorize the user!", null, context);
                                }
                                activeCalls.remove(currentCallID);
                                if(activeCalls.size() == 0){
                                    orderAdapter.notifyDataSetChanged();
                                    if (finishActivity)
                                        ((Activity)context).finish();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try{
                                JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                                String errorMessage =  errorObject.getString("Message");
                                Log.e(logLocation, errorMessage);
                                CreateBasicSnack(errorMessage, null, context);
                            }catch (Exception ex){
                                Log.e(logLocation, ex.getMessage());
                            }finally {
                                activeCalls.remove(currentCallID);
                                if(activeCalls.size() == 0){
                                    orderAdapter.notifyDataSetChanged();
                                    if (finishActivity)
                                        ((Activity)context).finish();
                                }
                            }
                        }
                    }
            ){
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    params.put("Authorization", ReadPreference(context, context.getString(R.string.preference_linnworksToken)));
                    return params;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("entriesPerPage", String.valueOf(entriesPerPage));
                    params.put("pageNumber", String.valueOf(pageNumber));
                    params.put("filters", filters.toString());
                    params.put("sorting", "");
                    params.put("fulfilmentCenter", fulfilmentCenter.toString());
                    params.put("additionalFilter", "");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(retrieveUIDRequest);
        }

        orderAdapter.notifyDataSetChanged();
    }

    public static void GetStockLocations(final Context context){

        final String logLocation = "API.INT.GSTLC";

        String url = ReadPreference(context, context.getString(R.string.preference_linnworksServer)) + "/api/Inventory/GetStockLocations";

        StringRequest getStockLocations = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        boolean displayError = false;

                        try{

                            JSONArray locationsJSONArray = new JSONArray(response);

                            if (locationsJSONArray.length() > 0)
                                allLocations = new ArrayList<>();

                            for(int i = 0; i < locationsJSONArray.length(); i++) {

                                final JSONObject locationObject = locationsJSONArray.getJSONObject(i);

                                allLocations.add(new InventoryStockLocation(){{
                                        StockLocationId = UUID.fromString(locationObject.getString("StockLocationId"));
                                        LocationName = locationObject.getString("LocationName");
                                        IsFulfillmentCenter = locationObject.getBoolean("IsFulfillmentCenter");
                                        BinRack = locationObject.isNull("BinRack") ? "" : locationObject.getString("BinRack");
                                        IsWarehouseManaged = locationObject.getBoolean("IsWarehouseManaged");
                                    }});

                            }

                        }catch (JSONException jex){
                            displayError = true;
                            Log.e(logLocation, jex.getMessage());
                        }catch (Exception ex){
                            displayError = true;
                            Log.e(logLocation, ex.getMessage());
                        }finally {
                            if (displayError) {
                                CreateBasicSnack("Unable to Get user locations!", null, context);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try{
                            JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            String errorMessage =  errorObject.getString("Message");
                            Log.e(logLocation, error.networkResponse.statusCode + " | " + errorMessage);
                            CreateBasicSnack(errorMessage, null, context);
                        }catch (Exception ex){
                            Log.e(logLocation, ex.getMessage());
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                params.put("Authorization", ReadPreference(context, context.getString(R.string.preference_linnworksToken)));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(getStockLocations);
    }

    public static void ProcessOrders(final Context context, List<UUID> orderIDs, final UUID locationID){

        final String logLocation = "API.INT.PRCSOR";

        String url = ReadPreference(context, context.getString(R.string.preference_linnworksServer)) + "/api/Orders/ProcessOrder";

        final List<UUID> activeCalls = new ArrayList<>();

        for(final UUID orderID : orderIDs) {

            final UUID currentCallID = UUID.randomUUID();
            activeCalls.add(currentCallID);

            StringRequest processOrder = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject responseObject = new JSONObject(response);

                                if(responseObject.has("Error")){
                                    Log.e(logLocation, responseObject.getString("Error"));
                                }else{
                                    activeCalls.remove(currentCallID);
                                    if (activeCalls.size() == 0) {
//                                RemoveProcessedOrders();
                                        GetAllOrdersByBarcodes(context, true, false);
                                        Log.i(logLocation, "Order(s) processed.");
                                    }
                                }


                            }catch (Exception ex){
                                Log.e(logLocation, ex.getMessage());
                                CreateBasicSnack("Error while processing the order(s)!", null, context);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                                String errorMessage = errorObject.getString("Message");
                                Log.e(logLocation, error.networkResponse.statusCode + " | " + errorMessage);
                                CreateBasicSnack(errorMessage, null, context);
                            } catch (Exception ex) {
                                Log.e(logLocation, ex.getMessage());
                            }finally {
                                activeCalls.remove(currentCallID);
                                if(activeCalls.size() == 0){
                                    GetAllOrdersByBarcodes(context, true, false);
                                    Log.i(logLocation, "Order(s) processed ERROR.");
                                }
                            }
                        }
                    }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    params.put("Authorization", ReadPreference(context, context.getString(R.string.preference_linnworksToken)));
                    return params;
                }
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("orderId", orderID.toString());
                    params.put("scanPerformed", String.valueOf(false));
                    params.put("locationId", locationID.toString());
                    params.put("allowZeroAndNegativeBatchQty", String.valueOf(false));
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(processOrder);
        }
    }

    public static void GetAllSources(final Context context){

        final String logLocation = "API.INT.GETSRC";

        String url = ReadPreference(context, context.getString(R.string.preference_linnworksServer)) + "/api/OpenOrders/GetAvailableChannels";

        JsonObjectRequest sendJsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray channelsArray = response.getJSONArray("Channels");

                            if(response.has("Error")){
                                Log.e(logLocation, response.getString("Error"));
                            }else{

                                for (int i = 0; i < channelsArray.length(); i++){

                                    JSONObject channelObject = channelsArray.getJSONObject(i);

                                    final String channelName = channelObject.getString("Name");
                                    final String channelIdentifier = channelObject.getString("Identifier");
                                    final String channelImageUrl  = URLUtil.isValidUrl(channelObject.getString("Logo") ) ? channelObject.getString("Logo")  : context.getString(R.string.linnworks_general_IP) + channelObject.getString("Logo") ;

                                    allSources.put(channelIdentifier.toUpperCase(), new CustomSource(){{
                                        name = channelName;
                                        identifier = channelIdentifier;
                                        imageUrl = channelImageUrl;
                                    }});

                                }

                            }


                        }catch (Exception ex){
                            Log.e(logLocation, ex.getMessage());
                            CreateBasicSnack("Error while retrieving the channels!", null, context);
                        }



                        Log.i(logLocation, "RESPONSE RETRIEVED");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        try {
                            JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                            String errorMessage = errorObject.getString("Message");
                            Log.e(logLocation, error.networkResponse.statusCode + " | " + errorMessage);
                            CreateBasicSnack(errorMessage, null, context);
                        } catch (Exception ex) {
                            Log.e(logLocation, ex.getMessage());
                        }

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                params.put("Authorization", ReadPreference(context, context.getString(R.string.preference_linnworksToken)));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(sendJsonRequest);

    }

    public static void GetVirtualPrinters(final Context context){

        final String logLocation = "API.INT.GETPRNT";

        String url = ReadPreference(context, context.getString(R.string.preference_linnworksServer)) + "/api/PrintService/VP_GetPrinters";

            StringRequest processOrder = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            List<String> printerLocations = new ArrayList<>();

                            try {
                                JSONArray responseArray = new JSONArray(response);
                                allPrinters = new ArrayList<>();
                                for (int i = 0; i < responseArray.length(); i++) {

                                    final JSONObject printerObject = responseArray.getJSONObject(i);

                                    if(printerObject.has("PrinterLocationName") && printerObject.has("PrinterName") && printerObject.has("Status")) {
                                        VirtualPrinter printer = new VirtualPrinter(){{
                                            PrinterLocationName = printerObject.getString("PrinterLocationName");
                                            PrinterName = printerObject.getString("PrinterName");
                                            Status = PrinterStatus.valueOf(printerObject.getString("Status"));
                                        }};
                                        allPrinters.add(printer);
                                        printerLocations.add(printer.PrinterLocationName + "/" + printer.PrinterName);
                                    }
                                }

                                if(allPrinters.size() > 0) {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line,printerLocations);
                                    sPrinters.setAdapter(adapter);
                                }

                            }catch (Exception ex){
                                Log.e(logLocation, ex.getMessage());
                                CreateBasicSnack("Error while retrieving the printers!", null, context);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                JSONObject errorObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                                String errorMessage = errorObject.getString("Message");
                                Log.e(logLocation, error.networkResponse.statusCode + " | " + errorMessage);
                                CreateBasicSnack(errorMessage, null, context);
                            } catch (Exception ex) {
                                Log.e(logLocation, ex.getMessage());
                            }
                        }
                    }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    params.put("Authorization", ReadPreference(context, context.getString(R.string.preference_linnworksToken)));
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(processOrder);
        }
    }
