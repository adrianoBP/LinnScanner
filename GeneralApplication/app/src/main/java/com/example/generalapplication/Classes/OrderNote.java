package com.example.generalapplication.Classes;

import java.util.Date;
import java.util.UUID;

public class OrderNote {
    /// <summary>
    /// Order note ID
    /// </summary>
    public UUID OrderNoteId;

    /// <summary>
    /// Order Id
    /// </summary>
    public UUID OrderId;

    /// <summary>
    /// Date and time when note was added
    /// </summary>
    public Date NoteDate;

    /// <summary>
    /// order note type (Internal or External)
    /// </summary>
    public Boolean Internal;

    /// <summary>
    /// Note's text
    /// </summary>
    public String Note;

    /// <summary>
    /// User that created note
    /// </summary>
    public String CreatedBy;

    public Byte NoteTypeId;
}
