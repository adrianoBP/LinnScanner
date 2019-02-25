package com.example.generalapplication.Classes;

import com.google.gson.GsonBuilder;

import java.util.List;

public class FieldsFilter {
    /// <summary>
    /// Text type field filters
    /// </summary>
    public List<TextFieldFilter> TextFields;

    /// <summary>
    /// Boolean type field filters
    /// </summary>
    public List<BooleanFieldFilter> BooleanFields;

    /// <summary>
    /// Numerical type field filters
    /// </summary>
    public List<NumericFieldFilter> NumericFields;

    /// <summary>
    /// Date type field filters
    /// </summary>
    public List<DateFieldFilter> DateFields;

    /// <summary>
    /// List type field filters
    /// </summary>
    public List<ListFieldFilter> ListFields;

    public List<FieldVisibility> FieldVisibility;

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, FieldsFilter.class);
    }
}
