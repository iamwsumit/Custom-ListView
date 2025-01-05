package com.sumit1334.listview.utils;

import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.ReplForm;

public class ListViewVerification {
    public static void verifyListViewComponent() {
        Form form = Form.getActiveForm();
        if (!(form instanceof ReplForm)) {
            try {
                Class.forName("androidx.recyclerview.widget.RecyclerView").getName();
            } catch (ClassNotFoundException e) {
                // showing warning
                Notifier.oneButtonAlert(form,
                        "This extension requires card view library to work which is present in AI2 ListView component, please drag one list view component in your project so that card view library could be compiled in your app",
                        "ListView Missing",
                        "Ok");
            }
        }
    }
}
