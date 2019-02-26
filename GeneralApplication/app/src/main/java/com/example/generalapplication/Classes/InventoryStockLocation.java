package com.example.generalapplication.Classes;

import java.util.UUID;

public class InventoryStockLocation {

    /// <summary>
    /// Location ID
    /// </summary>
    public UUID StockLocationId;

    /// <summary>
    /// Location name
    /// </summary>
    public String LocationName;

    /// <summary>
    /// If location is a fulfilment center
    /// </summary>
    public Boolean IsFulfillmentCenter;

    /// <summary>
    /// Location tag
    /// </summary>
    public String LocationTag;

    /// <summary>
    /// Bin rack
    /// </summary>
    public String BinRack;

    /// <summary>
    /// If the location is warehosue managed.
    /// </summary>
    public Boolean IsWarehouseManaged;

}
