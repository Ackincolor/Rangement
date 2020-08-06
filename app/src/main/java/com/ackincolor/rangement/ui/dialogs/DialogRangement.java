package com.ackincolor.rangement.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ackincolor.rangement.R;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.home.HomeViewModel;

public class DialogRangement implements  View.OnClickListener {
    HomeViewModel homeViewModel;
    Dialog dialog;
    public DialogRangement (HomeViewModel homeViewModel, Context context){
        this.homeViewModel = homeViewModel;
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
                homeViewModel.getRangements().add(new Rangement(nomRangement.getText().toString()));
                dialog.dismiss();
            }
        });
        this.dialog.show();
    }
}
