package home.example.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE = 100;
    private static final String TAG = ContactsContract.Profile.class.getSimpleName();
    private static Context contextOfApplication;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    Uri mImageUri;
    Context mContext;
    ImageView ivImg_profile;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private ImageView changeProfileImage;
    private EditText editProfileName;
    private EditText editProfileTage;
    private EditText editProfileInfo;
    private FirebaseAuth.AuthStateListener authStateListener;

   /* ImageView ivImg1;
    ImageView ivImg2;
    ImageView ivImg3;
    ImageView ivImg4;*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        TextView tvProfile = parentView.findViewById(R.id.tv_edit_profile);
        editProfileName = (EditText) parentView.findViewById(R.id.et_edit_name);
        editProfileTage = (EditText) parentView.findViewById(R.id.et_edit_tag);
        editProfileInfo = (EditText) parentView.findViewById(R.id.et_edit_info);
        ivImg_profile = parentView.findViewById(R.id.et_url_profile);
        changeProfileImage = parentView.findViewById(R.id.imgbtn_url_profile);

        tvProfile.setText("Edit Profile Information");

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
                uploadToStorage();
            }
        });


        return parentView;
    }

    public void changeImage() {
        Intent intentObj = new Intent();
        intentObj.setType("image/*");
        intentObj.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentObj, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            //TODO: action
            try {


                mImageUri = data.getData();
                Log.d("img-uri", mImageUri.toString());
                Log.d("img-uri-path", mImageUri.getPath());

                InputStream imageStream = mContext.getContentResolver().openInputStream(mImageUri);    /////////////////////////////////
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivImg_profile.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                Toast.makeText(mContext, "Image not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void uploadToStorage() {

        final StorageReference profileRef = mStorageRef.child("images/profile.jpg");

        profileRef.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content


                        Toast.makeText(mContext, "Image was Uploaded successfully", Toast.LENGTH_SHORT).show();
                        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri imageFirebaseUrl) {

                                Log.d("imageUrl", imageFirebaseUrl.toString());


                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Log.d("failToUpload", exception.toString());
                    }
                });

    }


}
/*
        ivImg_profile = parentView.findViewById(R.id.iv_img_profile);
        ivImg1 = parentView.findViewById(R.id.iv_img1);
        ivImg2 = parentView.findViewById(R.id.iv_img2);
        ivImg3 = parentView.findViewById(R.id.iv_img3);
        ivImg4 = parentView.findViewById(R.id.iv_img4);
        TextView tvName = parentView.findViewById(R.id.tv_name);
        Button btnOpenGallery = parentView.findViewById(R.id.btn_gallery);
        Button btnSubmit = parentView.findViewById(R.id.btnSubmit);


        ivImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });

        tvName.setText("name of freelancer");
        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeImage();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToStorage();
            }
        });
        return parentView;
    }




   */
/*

    void addImageUrlToUserObject(Uri uri) {

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        final String myFirebaseId = firebaseUser.getUid();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(myFirebaseId);

        myRef.child("imageUrl").setValue(uri.toString())

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "img url added succufully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();

                            Log.d("img", task.getException().toString());
                        }
                    }
                });


    }
*/


