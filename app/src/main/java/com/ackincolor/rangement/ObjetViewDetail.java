package com.ackincolor.rangement;

import android.content.Intent;
import android.os.Bundle;

import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class ObjetViewDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.activity_objet_view_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nomobjet = findViewById(R.id.textView3);
        nomobjet.setText("OBJET de nom :");
        TextView textView = findViewById(R.id.textView4);
        textView.setText(((Objet)intent.getParcelableExtra("OBJET")).getNom());
    }

}
