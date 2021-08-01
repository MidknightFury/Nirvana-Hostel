package com.example.project;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class activity_updatenotice extends AppCompatActivity {

     ImageButton imageButton;
     ImageView noticeImageView;
     Button uploadNoticeButton;
     FirebaseAuth mAuth;
     FirebaseStorage storage;
     Uri imageUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenotice);

        imageButton=findViewById(R.id.imageButton);
        uploadNoticeButton=findViewById(R.id.uploadNoticeButton);
        noticeImageView=findViewById(R.id.noticeImageView);

        mAuth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();

        // run the below method on imageView Click
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
                //the image is selected successfully from the gallery
                //now we need to upload the image in the firebase storage.
            }
        });

        uploadNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upload image on button click
                uploadImage();
            }
        });

    }

    private void uploadImage() {
        //progress dialogue
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading...");
        dialog.show();
//        here we need to access the below result code, but we can't
//        so to solve it we'll take it as global
        if (imageUri !=null){
            FirebaseUser user = mAuth.getCurrentUser();
//            String userID = user.getUid();
//            DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("userID").child(userID);
            StorageReference reference = storage.getReference().child("images/" + mAuth.getCurrentUser().getUid());
//            we're creating a reference to store the image in the firebase storage
            // it'll be stored inside images folder inside firebase storage
//            now using the below code, we'll store the file
            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        // image uploaded succesfully
                        dialog.dismiss();
                        Toast.makeText(activity_updatenotice.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //failed to upload
                        dialog.dismiss();
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