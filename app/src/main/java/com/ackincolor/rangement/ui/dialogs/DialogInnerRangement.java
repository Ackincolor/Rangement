package com.ackincolor.rangement.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ackincolor.rangement.R;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.home.HomeViewModel;

public class DialogInnerRangement implements  View.OnClickListener {
    HomeViewModel homeViewModel;
    Rangement rangement;
    Dialog dialog;
    public DialogInnerRangement(HomeViewModel homeViewModel, Context context, Rangement rangement){
        this.homeViewModel = homeViewModel;
        this.rangement = rangement;
        this.dialog = new Dialog(context);
    }
    @Override
    public void onClick(View v) {
        this.dialog.setContentView(R.layout.addrangementdialog);
        this.dialog.setCancelable(true);

        Button addrangement=(Button)this.dialog.findViewById(R.id.addbtnrangement);
        final EditText nomRangement=(EditText)this.dialog.findViewById(R.id.rangementtextinput);

        addrangement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rangement rangement1 = new Rangement(nomRangement.getText().toString());
                rangement1.setIdRangementParent(rangement.getId());
                homeViewModel.addRangement(rangement1);
                dialog.dismiss();
            }
        });
        this.dialog.show();
    }
}
