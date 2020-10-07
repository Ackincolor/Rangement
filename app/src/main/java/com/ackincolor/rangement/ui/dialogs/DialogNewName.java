package com.ackincolor.rangement.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ackincolor.rangement.Database.ObjetManager;
import com.ackincolor.rangement.R;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.objet.DashboardViewModel;

public class DialogNewName implements  View.OnClickListener {
    Dialog dialog;
    private Objet objet;
    ObjetManager objetManager;
    private TextView textView;
    public DialogNewName(Context context, Objet objet, TextView textView){
        this.dialog = new Dialog(context);
        this.objet = objet;
        objetManager = new ObjetManager(context);
        this.textView = textView;
    }
    @Override
    public void onClick(View v) {
        this.dialog.setContentView(R.layout.modifiernomobjet);
        this.dialog.setCancelable(true);

        Button addObjet=(Button)this.dialog.findViewById(R.id.modifynamebtn);
        final EditText nomObjet=(EditText)this.dialog.findViewById(R.id.nomtextinput);


        addObjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objet.setNom(nomObjet.getText().toString());
                textView.setText(objet.getNom());
                objetManager.open();
                objetManager.updateObjet(objet);
                dialog.dismiss();
            }
        });
        this.dialog.show();
    }
}
