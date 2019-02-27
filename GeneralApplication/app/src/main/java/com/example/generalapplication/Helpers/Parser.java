package com.example.generalapplication.Helpers;

import android.util.Log;

import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.Classes.OrderGeneralInfo;
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
//                    Identifiers = generalInfoObject.has("Identifiers") ? generalInfoObject.getInt("Identifiers") : 0;
                    ReferenceNum = generalInfoObject.has("ReferenceNum") ? generalInfoObject.getString("ReferenceNum") : "";
                    SecondaryReference = generalInfoObject.has("SecondaryReference") ? generalInfoObject.getString("SecondaryReference") : "";
                    ExternalReferenceNum = generalInfoObject.has("ExternalReferenceNum") ? generalInfoObject.getString("ExternalReferenceNum") : "";
//                    ReceivedDate = generalInfoObject.has("ReceivedDate") ? generalInfoObject.getInt("ReceivedDate") : "";
                    Source = generalInfoObject.has("Source") ? generalInfoObject.getString("Source") : "";
                    SubSource = generalInfoObject.has("SubSource") ? generalInfoObject.getString("SubSource") : "";
                    SiteCode = generalInfoObject.has("SiteCode") ? generalInfoObject.getString("SiteCode") : "";
                    HoldOrCancel = generalInfoObject.has("HoldOrCancel") && generalInfoObject.getBoolean("HoldOrCancel");
//                    DespatchByDate = generalInfoObject.has("DespatchByDate") ? generalInfoObject.getInt("DespatchByDate") : 0;
//                    ScheduledDelivery = generalInfoObject.has("ScheduledDelivery") ? generalInfoObject.getInt("ScheduledDelivery") : 0;
                    HasScheduledDelivery = generalInfoObject.has("HasScheduledDelivery") && generalInfoObject.getBoolean("HasScheduledDelivery");
                    Location = generalInfoObject.has("Location") ? UUID.fromString(generalInfoObject.getString("Location")) : new UUID(0L, 0L);
                    NumItems = generalInfoObject.has("NumItems") ? generalInfoObject.getInt("NumItems") : 0;
//                    StockAllocationType = generalInfoObject.has("StockAllocationType") ? generalInfoObject.getInt("StockAllocationType") : 0;
                }};
            }};
        }catch (Exception ex){
            Log.e(logLocation, ex.getMessage());
        }

        return order;
    }

}
