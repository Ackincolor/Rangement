package com.ackincolor.rangement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.ackincolor.rangement.Database.ObjetManager;
import com.ackincolor.rangement.controllers.PhotoController;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.dialogs.DialogNewName;
import com.ackincolor.rangement.ui.dialogs.DialogStatut;
import com.ackincolor.rangement.ui.dialogs.SwipeHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        nomobjet.setText("Nom :");
        final TextView textView = findViewById(R.id.textView4);
        textView.setText(this.objet.getNom());
        final TextView statusView = findViewById(R.id.textView8);
        statusView.setText(this.objet.getStatus());
        ImageButton modifyNameBtn = findViewById(R.id.modifyNamebtn);
        ImageButton modifyStatus = findViewById(R.id.modifystatusbtn);

        modifyNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogNewName dialogNewName = new DialogNewName(ObjetViewDetail.this,objet,textView);
                dialogNewName.onClick(v);
            }
        });

        modifyStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogStatut dialogStatut = new DialogStatut(ObjetViewDetail.this,objet,statusView);
                dialogStatut.onClick(v);
            }
        });

        photoController = new PhotoController(this);

        this.imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //demande de changement de photos
                if(objet.getFullsizeImage()!=null){
                    // Build an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(ObjetViewDetail.this);
                    // Set a title for alert dialog
                    builder.setTitle("");
                    // Ask the final question
                    builder.setMessage("Prendre une nouvelle photo ?");
                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File photo = photoController.takePhoto();
                        }
                    });
                    // Set the alert dialog no button click listener
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when No button clicked
                            Toast.makeText(getApplicationContext(),
                                    "Ok pas de nouvelle photo",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setView(R.layout.alertdialogimageview);
                    AlertDialog dialog = builder.create();

                    // Display the alert dialog on interface
                    dialog.show();
                    ImageView imageviewdialog = dialog.findViewById(R.id.dialog_imageview);
                    imageviewdialog.setImageBitmap(photoController.getResizedBitmap( BitmapFactory.decodeFile(objet.getFullsizeImage()), SwipeHelper.dpToPx(100),SwipeHelper.dpToPx(100)));
                }else{
                    File photo = photoController.takePhoto();
                }

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
            Bitmap imageBitmap = photoController.getResizedBitmap(fullsizeimage,SwipeHelper.dpToPx(100),SwipeHelper.dpToPx(100));
            this.objet.setThumbnail(photoController.currentPhotoPath);
            this.objet.setFullsizeImage( photoController.currentPhotoPath);
            this.imageView.setImageBitmap(imageBitmap);
            save();
        }
    }
    private void loadImage(){
        ObjetManager objetManager = new ObjetManager(this);
        objetManager.open();
        this.objet = objetManager.getObjet(this.objet.getId().toString());
        if(this.objet.getThumbnail()!=null){
            this.imageView.setImageBitmap(photoController.getResizedBitmap(BitmapFactory.decodeFile(this.objet.getThumbnail()),SwipeHelper.dpToPx(100),SwipeHelper.dpToPx(100)));
        }
    }

    private void save(){
        ObjetManager objetManager =new ObjetManager(this);
        objetManager.open();
        objetManager.updateObjet(this.objet);
    }

}
