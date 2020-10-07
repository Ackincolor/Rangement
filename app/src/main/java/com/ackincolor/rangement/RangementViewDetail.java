package com.ackincolor.rangement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import com.ackincolor.rangement.Database.RangementManager;
import com.ackincolor.rangement.controllers.PhotoController;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.dialogs.DialogObjet;
import com.ackincolor.rangement.ui.dialogs.SwipeHelper;
import com.ackincolor.rangement.ui.objet.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class RangementViewDetail extends AppCompatActivity {

    Rangement rangement;
    PhotoController photoController;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.rangement = (Rangement)intent.getParcelableExtra("RANGEMENT");
        setContentView(R.layout.activity_rangement_view_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        photoController = new PhotoController(this);

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

        this.imageView = findViewById(R.id.imageView);
        if(this.rangement.getThumbnail()!=null){
            this.imageView.setImageBitmap(photoController.getResizedBitmap(BitmapFactory.decodeFile(this.rangement.getFullsizeImage()), SwipeHelper.dpToPx(300),SwipeHelper.dpToPx(300)));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File photo = photoController.takePhoto();

            }
        });


        final DialogObjet dialogObjet = new DialogObjet(dashboardFragment.getDashboardViewModel(),this,this.rangement);

        addbtnobjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogObjet.onClick(view);
            }
        });
        this.loadImage();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PhotoController.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //get your gallery image uri data here
            Log.d("DEBUG PHOTO","résultat activité ici");
            Log.d("DEBUG PHOTO", "Photo prise : "+ photoController.currentPhotoPath);
            Bitmap fullsizeimage= BitmapFactory.decodeFile(photoController.currentPhotoPath);

            Bitmap imageBitmap = photoController.getResizedBitmap(fullsizeimage, SwipeHelper.dpToPx(300),SwipeHelper.dpToPx(300));
            this.rangement.setThumbnail(photoController.currentPhotoPath);
            this.rangement.setFullsizeImage( photoController.currentPhotoPath);
            this.imageView.setImageBitmap(imageBitmap);
            save();
        }
    }
    private void loadImage(){
        RangementManager rangementManager = new RangementManager(this);
        rangementManager.open();
        this.rangement = rangementManager.getRangements(this.rangement.getId().toString());
        if(this.rangement.getThumbnail()!=null)
            this.imageView.setImageBitmap(photoController.getResizedBitmap(BitmapFactory.decodeFile(this.rangement.getFullsizeImage()), SwipeHelper.dpToPx(300),SwipeHelper.dpToPx(300)));
    }

    private void save(){
        RangementManager rangementManager = new RangementManager(this);
        rangementManager.open();
        rangementManager.updateRangement(this.rangement);
    }

}
