package com.example.generalapplication.Classes;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderDetails {

    /// <summary>
    /// Order ID (pkOrderId) 
    /// </summary>
    public UUID OrderId;

    /// <summary>
    /// Linnworks order number 
    /// </summary>
    public Integer NumOrderId;

    /// <summary>
    /// If order is processed 
    /// </summary>
    public Boolean Processed;

    /// <summary>
    /// Date and time when order was processed 
    /// </summary>
    public Date ProcessedDate;

    /// <summary>
    /// Location ID 
    /// </summary>
    public UUID FulfilmentLocationId;

    /// <summary>
    /// General information about order 
    /// </summary>
    public OrderGeneralInfo GeneralInfo;

    /// <summary>
    /// Order shipping information 
    /// </summary>
    public OrderShippingInfo ShippingInfo;

    /// <summary>
    /// Order Customer information (Name, email etc) 
    /// </summary>
    public OrderCustomerInfo CustomerInfo;

    /// <summary>
    /// Order totals information 
    /// </summary>
    public OrderTotalsInfo TotalsInfo;

    /// <summary>
    /// Extended properties of an order 
    /// </summary>
    public List<ExtendedProperty> ExtendedProperties;

    /// <summary>
    /// Folder names assigned to an order 
    /// </summary>
    public List<String> FolderName;

    /// <summary>
    /// List of order items 
    /// </summary>
    public List<OrderItem> Items;

    /// <summary>
    /// List of order notes 
    /// </summary>
    public List<OrderNote> Notes;

}
