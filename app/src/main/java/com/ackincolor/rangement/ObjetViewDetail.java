package com.ackincolor.rangement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.ackincolor.rangement.Database.ObjetManager;
import com.ackincolor.rangement.controllers.PhotoController;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ObjetViewDetail extends AppCompatActivity {

    Objet objet;

    ImageView imageView;
    PhotoController photoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.objet = intent.getParcelableExtra("OBJET");
        setContentView(R.layout.activity_objet_view_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nomobjet = findViewById(R.id.textView3);
        nomobjet.setText("OBJET de nom :");
        TextView textView = findViewById(R.id.textView4);
        textView.setText(this.objet.getNom());

        photoController = new PhotoController(this);

        this.imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File photo = photoController.takePhoto();

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

            Bitmap imageBitmap = photoController.getResizedBitmap(fullsizeimage,120,120);
            this.objet.setThumbnail(imageBitmap);
            this.objet.setFullsizeImage( fullsizeimage);
            this.imageView.setImageBitmap(imageBitmap);
            save();
        }
    }
    private void loadImage(){
        ObjetManager objetManager = new ObjetManager(this);
        objetManager.open();
        this.objet = objetManager.getObjet(this.objet.getId().toString());
        if(this.objet.getThumbnail()!=null){
            this.imageView.setImageBitmap(photoController.getResizedBitmap(this.objet.getThumbnail(),120,120));
        }
    }

    private void save(){
        ObjetManager objetManager =new ObjetManager(this);
        objetManager.open();
        objetManager.updateObjet(this.objet);
    }

}
