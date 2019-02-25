package com.example.generalapplication.Classes;

import java.util.UUID;

public class OrderShippingInfo {
    /// <summary>
    /// Courier name (e.g. Royal Mail)
    /// </summary>
    public String Vendor;

    /// <summary>
    /// Postal service ID
    /// </summary>
    public UUID PostalServiceId;

    /// <summary>
    /// Postal service name (e.g. Next day delivery)
    /// </summary>
    public String PostalServiceName;

    /// <summary>
    /// Order total weight
    /// </summary>
    public Double TotalWeight;

    /// <summary>
    /// If order is processed
    /// </summary>
    public Double ItemWeight;

    /// <summary>
    /// Package category ID
    /// </summary>
    public UUID PackageCategoryId;

    /// <summary>
    /// Package category name
    /// </summary>
    public String PackageCategory;

    /// <summary>
    /// Package type ID
    /// </summary>
    public UUID PackageTypeId;

    /// <summary>
    /// Package type name
    /// </summary>
    public String PackageType;

    /// <summary>
    /// Order postage cost
    /// </summary>
    public Double PostageCost;

    /// <summary>
    /// Order postage cost excluding tax
    /// </summary>
    public Double PostageCostExTax;

    /// <summary>
    /// Order tracking number provided by courier
    /// </summary>
    public String TrackingNumber;

    /// <summary>
    /// If there is an adjustment to shipping cost was made
    /// </summary>
    public Boolean ManualAdjust;
}
