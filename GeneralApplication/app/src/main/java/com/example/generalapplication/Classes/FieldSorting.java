package com.example.generalapplication.Classes;

public class FieldSorting {
    /// <summary>
    /// Field code to sort by
    /// </summary>
    public FieldCode FieldCode;

    /// <summary>
    /// Sorting direction (Ascending:0, descending:1)
    /// </summary>
    public ListSortDirection Direction;

    /// <summary>
    /// Priority of sorting (e.g. sort by date first, then by status)
    /// </summary>
    public Integer Order;
}
