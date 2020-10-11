package com.ackincolor.rangement.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ackincolor.rangement.Database.ObjetManager;
import com.ackincolor.rangement.Database.RangementManager;
import com.ackincolor.rangement.R;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;

public class DialogStatut implements  View.OnClickListener, AdapterView.OnItemSelectedListener {
    Dialog dialog;
    private Objet objet;
    ObjetManager objetManager;
    RangementManager rangementManager;
    private Rangement selectedRangement;
    private TextView textView;
    public DialogStatut(Context context, Objet objet, TextView textView){
        this.dialog = new Dialog(context);
        this.objet = objet;
        this.objetManager = new ObjetManager(context);
        this.rangementManager = new RangementManager(context);
        this.textView = textView;
    }
    @Override
    public void onClick(View v) {
        this.dialog.setContentView(R.layout.addmodifstatutdialog);
        this.dialog.setCancelable(true);

        Button addObjet=(Button)this.dialog.findViewById(R.id.modifyrangement);
        final Spinner spinner = (Spinner)this.dialog.findViewById(R.id.status_spinner);
        spinner.setOnItemSelectedListener(this);

        this.rangementManager.open();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_spinner_item,rangementManager.getAllRangements().toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        addObjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objet.setRangement(selectedRangement.getId());
                textView.setText(Objet.Status.RANGE.toString());
                objetManager.open();
                objetManager.updateObjet(objet);
                dialog.dismiss();
            }
        });
        this.dialog.show();

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        this.selectedRangement =  (Rangement) parent.getItemAtPosition(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
