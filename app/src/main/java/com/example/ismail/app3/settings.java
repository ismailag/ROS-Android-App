package com.example.ismail.app3;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.net.MalformedURLException;

public class settings extends DialogFragment {

    EditText mEdit;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
       // final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View promptView = inflater.inflate(R.layout.settings, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.username);
        // setup a dialog window
        alertDialogBuilder.setTitle("Settings") ;

        alertDialogBuilder.setCancelable(false)
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity callingActivity = (MainActivity) getActivity();
             //   mEdit=(EditText) callingActivity.findViewById(R.id.username) ;

                callingActivity.connection("http://"+editText.getText().toString());

            }
        }) ;
        return alertDialogBuilder.create();
    }




}