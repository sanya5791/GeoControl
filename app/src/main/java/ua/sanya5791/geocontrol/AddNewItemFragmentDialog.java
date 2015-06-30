package ua.sanya5791.geocontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Sanya on 28.06.2015.
 */
public class AddNewItemFragmentDialog extends DialogFragment {

    interface GetText{
        void getNameAndNum(String name, String num);
    }

    GetText mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddNewItemFragmentDialog.GetText) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_add_contact, null);
        final EditText etName = (EditText) v.findViewById(R.id.et_name);
        final EditText etNumber = (EditText) v.findViewById(R.id.et_number);
        builder.setView(v)
            .setTitle("Add an item")
//            .setMessage("Add Item")
            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                    mListener.getNameAndNum(etName.getText().toString(),
                                            etNumber.getText().toString());
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });


        // Create the AlertDialog object and return it
        return builder.create();

//        return super.onCreateDialog(savedInstanceState);
    }
}
