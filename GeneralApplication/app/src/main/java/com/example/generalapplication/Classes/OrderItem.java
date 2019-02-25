package com.example.generalapplication.Classes;

import java.util.List;
import java.util.UUID;

public class OrderItem {
    /// <summary>
    /// Stock Item ID 
    /// </summary>
    public UUID ItemId;

    /// <summary>
    /// Item number as on channel 
    /// </summary>
    public String ItemNumber;

    /// <summary>
    /// Product SKU 
    /// </summary>
    public String SKU;

    /// <summary>
    /// Item source / channel name 
    /// </summary>
    public String ItemSource;

    /// <summary>
    /// Item title 
    /// </summary>
    public String Title;

    /// <summary>
    /// Quantity 
    /// </summary>
    public Integer Quantity;

    /// <summary>
    /// Product category 
    /// </summary>
    public String CategoryName;

    /// <summary>
    /// Composite availability 
    /// </summary>
    public Integer CompositeAvailablity;

    /// <summary>
    /// If stock level specified 
    /// </summary>
    public Boolean StockLevelsSpecified;

    /// <summary>
    /// Level due in purchase orders 
    /// </summary>
    public Integer OnOrder;

    /// <summary>
    /// Quantity currently in open orders 
    /// </summary>
    public Integer InOrderBook;

    /// <summary>
    /// Current stock level 
    /// </summary>
    public Integer Level;

    /// <summary>
    /// Minimum level 
    /// </summary>
    public Integer MinimumLevel;

    /// <summary>
    /// Currently available stock level (Level-InOrderBook) 
    /// </summary>
    public Integer AvailableStock;

    /// <summary>
    /// Unit price 
    /// </summary>
    public Double PricePerUnit;

    /// <summary>
    /// Unit cost 
    /// </summary>
    public Double UnitCost;

    /// <summary>
    /// Despatch stock unit cost 
    /// </summary>
    public Double DespatchStockUnitCost;

    /// <summary>
    /// Percentage (0%, 10%, 20%, etc...) 
    /// </summary>
    public Double Discount;

    /// <summary>
    /// Actual tax value on an item 
    /// </summary>
    public Double Tax;

    /// <summary>
    /// Tax rate 
    /// </summary>
    public Double TaxRate;

    /// <summary>
    /// Total item cost (exc tax) 
    /// </summary>
    public Double Cost;

    /// <summary>
    /// Total item cost (inc tax) 
    /// </summary>
    public Double CostIncTax;

    /// <summary>
    /// List of order items 
    /// </summary>
    public List<OrderItem> CompositeSubItems;

    /// <summary>
    /// if item is a service 
    /// </summary>
    public Boolean IsService;

    /// <summary>
    /// Sales Tax 
    /// </summary>
    public Double SalesTax;

    /// <summary>
    /// If tax is included in a cost 
    /// </summary>
    public Boolean TaxCostInclusive;

    /// <summary>
    /// If order is partly shipped 
    /// </summary>
    public Boolean PartShipped;

    /// <summary>
    /// Order weight 
    /// </summary>
    public Double Weight;

    /// <summary>
    /// Product barcode 
    /// </summary>
    public String BarcodeNumber;

    /// <summary>
    /// Market 
    /// </summary>
    public Integer Market;

    /// <summary>
    /// Channel product SKU 
    /// </summary>
    public String ChannelSKU;

    /// <summary>
    /// Channel product title 
    /// </summary>
    public String ChannelTitle;

    public Double DiscountValue;

    /// <summary>
    /// If item got an image 
    /// </summary>
    public Boolean HasImage;

    /// <summary>
    /// Image ID 
    /// </summary>
    public UUID ImageId;

    /// <summary>
    /// List of order item options 
    /// </summary>
    public List<OrderItemBinRack> AdditionalInfo;

    /// <summary>
    /// Stock level indicator 
    /// </summary>
    public Integer StockLevelIndicator;

    /// <summary>
    /// If batch number scan required 
    /// </summary>
    public Boolean BatchNumberScanRequired;

    /// <summary>
    /// If serial number scan required 
    /// </summary>
    public Boolean SerialNumberScanRequired;

    /// <summary>
    /// Binrack location 
    /// </summary>
    public String BinRack;

    /// <summary>
    /// List of BinRacks used for OrderItem 
    /// </summary>
    public List<OrderItemBinRack> BinRacks;

    /// <summary>
    /// Identifies whether the item has a sell by date or other defined order in which inventory is to be sold 
    /// </summary>
    public Integer InventoryTrackingType;

    /// <summary>
    /// If item has batches 
    /// </summary>
    public Boolean isBatchedStockItem;

    public Boolean IsWarehouseManaged;

    public Boolean HasPurchaseOrders;

    public Boolean CanPurchaseOrderFulfil;

    public Boolean IsUnlinked;

    public UUID RowId;

    public UUID OrderId;

    public UUID StockItemId;
}
