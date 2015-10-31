package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by MY System on 10/31/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WaitFragment extends DialogFragment {
    private ProgressDialog dialog;

    public WaitFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new ProgressDialog(getActivity());
        this.setStyle(STYLE_NO_TITLE, getTheme());
        dialog.setMessage("Please Wait.."); // set your messages if not inflated from XML
        dialog.setCancelable(false);
        return dialog;
    }
}
