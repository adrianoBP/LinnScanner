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
import com.example.generalapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.example.generalapplication.APIHelper.External.RetrieveUserInformation;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;
import static com.example.generalapplication.Helpers.Util.IsNullOrEmpty;
import static com.example.generalapplication.Helpers.Util.ReadPreference;
import static com.example.generalapplication.Helpers.Util.WritePreference;

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

                                // TODO: change this
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


}
