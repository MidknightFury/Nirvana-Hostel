package com.example.project;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class activity_updatenotice extends AppCompatActivity {

     ImageButton imageButton;
     ImageView noticeImageView;
     FirebaseStorage storage;
     Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenotice);

        imageButton=findViewById(R.id.uploadNoticeButton);
        noticeImageView=findViewById(R.id.noticeImageView);
        storage=FirebaseStorage.getInstance();

        // run the below method on imageView Click
        noticeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
                //the image is selected successfully from the gallery
                //now we need to upload the image in the firebase storage.
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upload image on button click
                uploadImage();
            }
        });

    }

    private void uploadImage() {
//        here we need to access the below result code, but we can't
//        so to solve it we'll take it as global
        if (imageUri !=null){
            StorageReference reference = storage.getReference().child("images/" + UUID.randomUUID().toString());
//            we're creating a reference to store the image in the firebase storage
            // it'll be stored inside images folder inside firebase storage
//            now using the below code, we'll store the file
            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        // image uploaded succesfully
                        Toast.makeText(activity_updatenotice.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //failed to upload
                        Toast.makeText(activity_updatenotice.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    //start an activity for result (new method)

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                //this result is the result of uri
                    if (result != null) {
                        noticeImageView.setImageURI(result);
//                        image will be set in the imageUri
                        imageUri=result;
                    }
                }
            });


}