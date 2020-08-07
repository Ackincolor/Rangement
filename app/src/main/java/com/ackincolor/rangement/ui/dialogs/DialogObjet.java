package com.ackincolor.rangement.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ackincolor.rangement.R;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.home.HomeViewModel;
import com.ackincolor.rangement.ui.objet.DashboardViewModel;

public class DialogObjet implements  View.OnClickListener {
    DashboardViewModel dashboardViewModel;
    Dialog dialog;
    private Rangement rangement;
    public DialogObjet(DashboardViewModel dashboardViewModel, Context context, Rangement rangement){
        this.dashboardViewModel = dashboardViewModel;
        this.dialog = new Dialog(context);
        this.rangement = rangement;
    }
    @Override
    public void onClick(View v) {
        this.dialog.setContentView(R.layout.addobjetdialog);
        this.dialog.setCancelable(true);

        Button addObjet=(Button)this.dialog.findViewById(R.id.addbtnobjet);
        final EditText nomObjet=(EditText)this.dialog.findViewById(R.id.objettextinput);


        addObjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objet objet = new Objet(nomObjet.getText().toString());
                if(rangement!=null)
                    objet.setRangement(rangement.getId());
                dashboardViewModel.addObjet(objet);
                dialog.dismiss();
            }
        });
        this.dialog.show();
    }
}
