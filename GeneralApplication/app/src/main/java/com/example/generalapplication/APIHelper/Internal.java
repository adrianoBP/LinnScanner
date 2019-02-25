package com.example.generalapplication.APIHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.generalapplication.Activities.MainActivity;
import com.example.generalapplication.Classes.FieldCode;
import com.example.generalapplication.Classes.FieldSorting;
import com.example.generalapplication.Classes.FieldsFilter;
import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.Classes.OrderTotalsInfo;
import com.example.generalapplication.Classes.TextFieldFilter;
import com.example.generalapplication.Classes.TextFieldFilterType;
import com.example.generalapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.generalapplication.APIHelper.External.RetrieveUserInformation;
import static com.example.generalapplication.Helpers.Core.allOrders;
import static com.example.generalapplication.Helpers.Parser.ParseJSONToOrderDetails;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;
import static com.example.generalapplication.Helpers.Core.IsNullOrEmpty;
import static com.example.generalapplication.Helpers.Core.ReadPreference;
import static com.example.generalapplication.Helpers.Core.WritePreference;
import static com.example.generalapplication.Helpers.UI.orderAdapter;

public class Internal {

    public static void RetrieveUserId(final String email, final String password, final Context context){

        final String logLocation = "API.INT.RETRIEVEUSERID";

        String url = context.getString(R.string.linnworks_general_IP) + "/Auth/MultiLogin";

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

        String url = context.getString(R.string.linnworks_general_IP) + "/Auth/AuthorizeByApplication";

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

    public static void GetAllOrdersByBarcodes(final Context context, List<String> barcodes){

        final String logLocation = "API.INT.GETOO";

        String url = ReadPreference(context, context.getString(R.string.preference_linnworksServer)) + "/api/Orders/GetOpenOrders";

        allOrders = new ArrayList<>();

        final List<UUID> activeCalls = new ArrayList<>();

        for(final String barcode : barcodes){

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
            final UUID fulfilmentCenter = new UUID(0L, 0L);

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

    }

}
