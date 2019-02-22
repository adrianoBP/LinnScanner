package com.example.generalapplication.APIHelper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.generalapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.generalapplication.APIHelper.Internal.AuthorizeByApplication;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;
import static com.example.generalapplication.Helpers.Util.IsNullOrEmpty;
import static com.example.generalapplication.Helpers.Util.ReadPreference;
import static com.example.generalapplication.Helpers.Util.WritePreference;

public class External {

    public static void RetrieveUserInformation(final Context context){

        final String logLocation = "API.EXT.RETRIEVEUSERID";

        String url = context.getString(R.string.users_endpoing) + "/" + ReadPreference(context, context.getString(R.string.preference_userId));
        String parametrizedUrl = url + "?userId=" + ReadPreference(context, context.getString(R.string.preference_userId));

        JsonObjectRequest getUserInformation = new JsonObjectRequest(Request.Method.GET, parametrizedUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        boolean displayError = false;

                        try{

                            String error = response.getString("Error");
                            if(IsNullOrEmpty(error)){

                                JSONObject userInfoObject = response.getJSONObject("Result");

                                String fullName = userInfoObject.getString("FullName");
                                String email = userInfoObject.getString("Email");
                                String installationToken = userInfoObject.getString("InstallationToken");

                                WritePreference(context, context.getString(R.string.preference_userFullName), fullName);
                                WritePreference(context, context.getString(R.string.preference_userEmail), email);
                                WritePreference(context, context.getString(R.string.preference_userInstallationToken), installationToken);

                                AuthorizeByApplication(context, true);

                            }else{
                                displayError = true;
                                Log.e(logLocation, error);
                            }

                        }catch (JSONException jex){
                            displayError = true;
                            Log.e(logLocation, jex.getMessage());
                        }catch (Exception ex){
                            displayError = true;
                            Log.e(logLocation, ex.getMessage());
                        }finally {
                            if(displayError){ CreateBasicSnack("Unable to retrieve the data!", null, context); }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(getUserInformation);

    }

}
