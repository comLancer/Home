package home.example.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileFragment extends Fragment {
    private static final String KEY_Users = "user";
    Model mUser;
    private static final int PICK_IMAGE = 100;
    private static final String TAG = ContactsContract.Profile.class.getSimpleName();
    private static Context contextOfApplication;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CompanyAndFreelancer");
    Uri mImageUri;
    Context mContext;
    ImageView ivImg_profile;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private ImageView changeProfileImage;
    private EditText editProfileName;
    private EditText editProfileTage;
    private EditText editProfileInfo;
    private Button btnSubmit;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Dialog dialog;
   /* ImageView ivImg1;
    ImageView ivImg2;
    ImageView ivImg3;
    ImageView ivImg4;*/
   /*public static ProfileFragment newInstance(Model user) {
       ProfileFragment fragment = new ProfileFragment();
       Bundle args = new Bundle();
       args.putSerializable(KEY_Users, user);

       fragment.setArguments(args);
       return fragment;
   }
*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (Model) getArguments().getSerializable(KEY_Users);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.profile, container, false);



        TextView tvProfile = parentView.findViewById(R.id.tv_edit_profile);
        editProfileName = (EditText) parentView.findViewById(R.id.et_edit_name);
        editProfileTage = (EditText) parentView.findViewById(R.id.et_edit_tag);
        editProfileInfo = (EditText) parentView.findViewById(R.id.et_edit_info);

        btnSubmit = parentView.findViewById(R.id.btn_submit_edit_profile);
        //  ivImg_profile = parentView.findViewById(R.id.et_url_profile);
        changeProfileImage = parentView.findViewById(R.id.imgbtn_url_profile);

        tvProfile.setText("Edit Profile Information");

        //editProfileTage.setValue( getEditProfileTage());

       /* btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String profileName = editProfileName.getText().toString();

                if(TextUtils.isEmpty(profileName)) {
                    Model.displayMessageToast(mContext,"Name must be filled");
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent firebaseUserIntent = new Intent(mContext, MainActivity.class);
                    startActivity(firebaseUserIntent);
                    finish();
                } else {
                    String userId = user.getProviderId();
                    String id = user.getUid();
                    String profileEmail = user.getEmail();

                    Model userEntity = new Model( editProfileName);
                    ProfileFragment firebaseDatabaseHelper = new ProfileFragment();
                    firebaseDatabaseHelper.createUserInFirebaseDatabase(id, userEntity);

                    editProfileName.setText("");





            }
        });


        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();

            }
        });
*/
        readFromDatabase();


        return parentView;
    }

    private void updateData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("users").child(getTag()).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("leftSpace").setValue(" ");
                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }


    private boolean updateArtist(String id, String name, String genre) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);

        //updating artist
        ProfileFragment Profile = new ProfileFragment();      //editProfileName, editProfileTage, editProfileInfo
        dR.setValue(Profile);
        Toast.makeText(mContext, "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdate(final String editProfileName, String editProfileTage, String editProfileInfo) {

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });

        String name = editProfileName.getText().toString().trim();


    }

    private boolean update(String editProfileName, String editProfileTage, String editProfileInfo) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user").child(editProfileName);

        //updating artist
        Model name = new Model();
        dR.setValue(getText(editProfileName));
        Toast.makeText(mContext, "Profile Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    private void readFromDatabase() {
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                Model value = dataSnapshot.getValue(Model.class);

                editProfileInfo.setMovementMethod(new ScrollingMovementMethod());
                editProfileInfo.setText((CharSequence) value.getEditProfileInfo());
                editProfileName.setText(value.getEditProfileName());
                editProfileTage.setText(value.getEditProfileTage());

                Glide.with(mContext).load(value.getImageUrl()).placeholder(R.mipmap.ic_launcher).into(ivImg_profile);
                mUser.
                        String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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


