package com.example.generalapplication.Helpers;

import android.util.Log;

import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.Classes.OrderShippingInfo;
import com.example.generalapplication.Classes.OrderTotalsInfo;

import org.json.JSONObject;

import java.util.UUID;

public class Parser {

    public static OrderDetails ParseJSONToOrderDetails(final JSONObject orderObject ){

        String logLocation = "HLPR.PARSER.JSONOD";

        OrderDetails order = null;

        try {
            final JSONObject totalsInfoObject = orderObject.getJSONObject("TotalsInfo");
            final JSONObject shippingInfoObject = orderObject.getJSONObject("ShippingInfo");

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
                ShippingInfo = new OrderShippingInfo(){{
                    Vendor = shippingInfoObject.getString("Vendor");
                    PostalServiceId = UUID.fromString(shippingInfoObject.getString("PostalServiceId"));
                    PostalServiceName = shippingInfoObject.getString("PostalServiceName");
                    TotalWeight = shippingInfoObject.getDouble("TotalWeight");
                    ItemWeight = shippingInfoObject.getDouble("ItemWeight");
                    PackageCategoryId = UUID.fromString(shippingInfoObject.getString("PackageCategoryId"));
                    PackageCategory = shippingInfoObject.getString("PackageCategory");
                    PackageTypeId = shippingInfoObject.has("PackageTypeId") ? UUID.fromString(shippingInfoObject.getString("PackageTypeId")) : null;
                    PackageType = shippingInfoObject.has("PackageType") ? shippingInfoObject.getString("PackageType") : "";
                    PostageCost = shippingInfoObject.has("PostageCost") ? shippingInfoObject.getDouble("PostageCost") : 0;
                    PostageCostExTax = shippingInfoObject.has("PostageCostExTax") ? shippingInfoObject.getDouble("PostageCostExTax") : 0;
                    TrackingNumber = shippingInfoObject.has("TrackingNumber") ? shippingInfoObject.getString("TrackingNumber") : "";
                    ManualAdjust = shippingInfoObject.has("ManualAdjust") && shippingInfoObject.getBoolean("ManualAdjust");
                }};
            }};
        }catch (Exception ex){
            Log.e(logLocation, ex.getMessage());
        }

        return order;
    }

}
