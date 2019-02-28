package com.example.generalapplication.Helpers;

import android.util.Log;

import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.Classes.OrderGeneralInfo;
import com.example.generalapplication.Classes.OrderIdentifier;
import com.example.generalapplication.Classes.OrderShippingInfo;
import com.example.generalapplication.Classes.OrderTotalsInfo;
import com.example.generalapplication.Classes.ScheduledDelivery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Parser {

    public static OrderDetails ParseJSONToOrderDetails(final JSONObject orderObject ){

        String logLocation = "HLPR.PARSER.JSONOD";

        OrderDetails order = null;

        try {
            final JSONObject totalsInfoObject = orderObject.getJSONObject("TotalsInfo");
            final JSONObject shippingInfoObject = orderObject.getJSONObject("ShippingInfo");
            final JSONObject generalInfoObject = orderObject.getJSONObject("GeneralInfo");

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
                GeneralInfo = new OrderGeneralInfo(){{
                    Status = generalInfoObject.has("Status") ? generalInfoObject.getInt("Status") : 0;
                    LabelPrinted = generalInfoObject.has("LabelPrinted") && generalInfoObject.getBoolean("LabelPrinted");
                    LabelError = generalInfoObject.has("LabelError") ? generalInfoObject.getString("LabelError") : "";
                    InvoicePrinted = generalInfoObject.has("InvoicePrinted") && generalInfoObject.getBoolean("InvoicePrinted");
                    PickListPrinted = generalInfoObject.has("PickListPrinted") && generalInfoObject.getBoolean("PickListPrinted");
                    IsRuleRun = generalInfoObject.has("IsRuleRun") && generalInfoObject.getBoolean("IsRuleRun");
                    Notes = generalInfoObject.has("Notes") ? generalInfoObject.getInt("Notes") : 0;
                    PartShipped = generalInfoObject.has("PartShipped") && generalInfoObject.getBoolean("PartShipped");
                    Marker = generalInfoObject.has("Marker") ? generalInfoObject.getInt("Marker") : 0;
                    IsParked = generalInfoObject.has("IsParked") && generalInfoObject.getBoolean("IsParked");
                    Identifiers = GetIdentifiersFromGeneralInfoJSON(generalInfoObject);
                    ReferenceNum = generalInfoObject.has("ReferenceNum") ? generalInfoObject.getString("ReferenceNum") : "";
                    SecondaryReference = generalInfoObject.has("SecondaryReference") ? generalInfoObject.getString("SecondaryReference") : "";
                    ExternalReferenceNum = generalInfoObject.has("ExternalReferenceNum") ? generalInfoObject.getString("ExternalReferenceNum") : "";
                    ReceivedDate = generalInfoObject.has("ReceivedDate") ? GetDateFromString(generalInfoObject.getString("ReceivedDate")) : new Date();
                    Source = generalInfoObject.has("Source") ? generalInfoObject.getString("Source") : "";
                    SubSource = generalInfoObject.has("SubSource") ? generalInfoObject.getString("SubSource") : "";
                    SiteCode = generalInfoObject.has("SiteCode") ? generalInfoObject.getString("SiteCode") : "";
                    HoldOrCancel = generalInfoObject.has("HoldOrCancel") && generalInfoObject.getBoolean("HoldOrCancel");
                    DespatchByDate = generalInfoObject.has("DespatchByDate") ? GetDateFromString(generalInfoObject.getString("DespatchByDate")) : new Date();
                    ScheduledDelivery = GetScheduledDeliveryFromGeneralInfoJSON(generalInfoObject);
                    HasScheduledDelivery = generalInfoObject.has("HasScheduledDelivery") && generalInfoObject.getBoolean("HasScheduledDelivery");
                    Location = generalInfoObject.has("Location") ? UUID.fromString(generalInfoObject.getString("Location")) : new UUID(0L, 0L);
                    NumItems = generalInfoObject.has("NumItems") ? generalInfoObject.getInt("NumItems") : 0;
                    StockAllocationType = generalInfoObject.has("StockAllocationType") ? com.example.generalapplication.Classes.StockAllocationType.valueOf(generalInfoObject.getString("StockAllocationType")) : null;
                }};
            }};
        }catch (Exception ex){
            Log.e(logLocation, ex.getMessage());
        }
        return order;
    }

    public static List<OrderIdentifier> GetIdentifiersFromGeneralInfoJSON(JSONObject generalInfoObject){

        String logLocation = "HLPR.PARSER.JSONID";

        List<OrderIdentifier> identifiers = new ArrayList<>();

        try {
            if(generalInfoObject.has("Identifiers")) {

                final JSONArray giIdentifiers = generalInfoObject.getJSONArray("Identifiers");

                for (int i = 0; i < giIdentifiers.length(); i++) {
                    final JSONObject identifierObject = giIdentifiers.getJSONObject(i);
                    identifiers.add(
                            new OrderIdentifier() {{
                                IdentifierId = identifierObject.has("IdentifierId") ? identifierObject.getInt("IdentifierId") : 0;
                                Name = identifierObject.has("Name") ? identifierObject.getString("Name") : "";
                                Tag = identifierObject.has("Tag") ? identifierObject.getString("Tag") : "";
                            }}
                    );
                }
            }
        }catch (Exception ex){
            Log.e(logLocation, ex.getMessage());
        }

        return identifiers;
    }

    public static ScheduledDelivery GetScheduledDeliveryFromGeneralInfoJSON(JSONObject generalInfoObject){

        String logLocation = "HLPR.PARSER.JSONSD";

        ScheduledDelivery scheduledDelivery = new ScheduledDelivery();
        if(generalInfoObject.has("ScheduledDelivery")){
            try {
                JSONObject scheduledDeliveryObject = generalInfoObject.getJSONObject("ScheduledDelivery");
                scheduledDelivery.from = GetDateFromString(generalInfoObject.getString("From"));
                scheduledDelivery.to = GetDateFromString(generalInfoObject.getString("To"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return scheduledDelivery;
    }

    public static Date GetDateFromString(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date formatteDate = new Date();
        try {
            formatteDate = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatteDate;
    }

    public static String GetStringFromDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(date);
    }

}
