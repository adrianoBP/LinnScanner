package com.example.generalapplication.Classes;

import java.util.UUID;

public class OrderItemBinRack {
    /// <summary>
    /// Quantity for BinRack per Location 
    /// </summary>
    public Integer Quantity;

    /// <summary>
    /// BinRack 
    /// </summary>
    public String BinRack;

    /// <summary>
    /// LocationId of the BinRack 
    /// </summary>
    public UUID Location;

    /// <summary>
    /// If the item is batched, identifies the batch number 
    /// </summary>
    public Integer BatchId;

    /// <summary>
    /// If the item is batched, identifies the unique order item batch row 
    /// </summary>
    public Integer OrderItemBatchId;
}
