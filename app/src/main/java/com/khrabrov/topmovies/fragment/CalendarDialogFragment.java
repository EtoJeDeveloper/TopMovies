package com.khrabrov.topmovies.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CalendarDialogFragment extends DatePickerDialog {
    public CalendarDialogFragment(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    public CalendarDialogFragment(@NonNull Context context, int themeResId, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }
}
