package com.ackincolor.rangement;

import android.content.Intent;
import android.os.Bundle;

import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.dialogs.DialogObjet;
import com.ackincolor.rangement.ui.objet.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RangementViewDetail extends AppCompatActivity {

    Rangement rangement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.rangement = (Rangement)intent.getParcelableExtra("RANGEMENT");
        setContentView(R.layout.activity_rangement_view_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = findViewById(R.id.textView2);
        textView.setText(this.rangement.getNom());
        TextView textlongueur =findViewById(R.id.textlongueur);
        textlongueur.setText(this.rangement.getProfondeur().toString());
        TextView textlargeur =findViewById(R.id.textlargeur);
        textlargeur.setText(this.rangement.getLargeur().toString());
        TextView texthauteur =findViewById(R.id.texthauteur);
        texthauteur.setText(this.rangement.getHauteur().toString());
        DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentlisteobjets);
        dashboardFragment.setObjetListe(new ArrayList<Objet>(dashboardFragment.getDashboardViewModel().getObjetManager().getAllObjetsForRangmeent(this.rangement.getId())));
        Button addbtnobjet = findViewById(R.id.addbtnobjet);


        final DialogObjet dialogObjet = new DialogObjet(dashboardFragment.getDashboardViewModel(),this,this.rangement);

        addbtnobjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogObjet.onClick(view);
            }
        });

    }

}
