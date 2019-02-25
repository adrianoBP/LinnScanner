package com.example.generalapplication.Helpers;

import android.util.Log;

import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.Classes.OrderTotalsInfo;

import org.json.JSONObject;

import java.util.UUID;

public class Parser {

    public static OrderDetails ParseJSONToOrderDetails(final JSONObject orderObject ){

        String logLocation = "HLPR.PARSER.JSONOD";

        OrderDetails order = null;

        try {
            final JSONObject totalsInfoObject = orderObject.getJSONObject("TotalsInfo");

            order = new OrderDetails() {{
                NumOrderId = orderObject.getInt("NumOrderId");
                OrderId = UUID.fromString(orderObject.getString("OrderId"));
                TotalsInfo = new OrderTotalsInfo() {{
                    Subtotal = totalsInfoObject.getDouble("Subtotal");
                    PostageCost = totalsInfoObject.getDouble("PostageCost");
                    Tax = totalsInfoObject.getDouble("Tax");
                    TotalCharge = totalsInfoObject.getDouble("TotalCharge");
                    PaymentMethod = totalsInfoObject.getString("PaymentMethod");
                    PaymentMethodId = UUID.fromString(totalsInfoObject.getString("PaymentMethodId"));
                    ProfitMargin = totalsInfoObject.getDouble("ProfitMargin");
                    TotalDiscount = totalsInfoObject.getDouble("TotalDiscount");
                    Currency = totalsInfoObject.getString("Currency");
                    CountryTaxRate = totalsInfoObject.getDouble("CountryTaxRate");
                    ConversionRate = totalsInfoObject.getDouble("ConversionRate");
                }};
            }};
        }catch (Exception ex){
            Log.e(logLocation, ex.getMessage());
        }

        return order;
    }

}
