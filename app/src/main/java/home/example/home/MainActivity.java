package home.example.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    Uri mImageUri;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        changeFragmentTo(new HomeFragment(), HomeFragment.class.getSimpleName());


        changeToMainFragment();
    }


    private void changeToMainFragment() {
        //introduce the buttons
        Button btnChangeFragment = findViewById(R.id.bt_profile);
        Button btnChangeToSearchFragment = findViewById(R.id.bt_search);
        Button btnChangeToChatFragment = findViewById(R.id.btn_chat);
        Button btnChangeToHome = findViewById(R.id.btn_home);

        //call profile fragment
        btnChangeFragment.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {

                changeFragmentTo(new ProfileFragment(), ProfileFragment.class.getSimpleName());

            }

        });
        //call search fragment
        btnChangeToSearchFragment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                changeFragmentTo(new Search(), Search.class.getSimpleName());

            }
        });
        //call chat fragment
        btnChangeToChatFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragmentTo(new ChatFragment(), ChatFragment.class.getSimpleName());
            }
        });

        //call home fragment
        btnChangeToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragmentTo(new HomeFragment(), HomeFragment.class.getSimpleName());
            }
        });


    }


    void changeFragmentTo(Fragment fragmentToDisplay, String FragmentTag1) {    //this fragment will change the fragment as we want
        FragmentManager myFramgmentManager = getSupportFragmentManager();
        FragmentTransaction myFragmentTransaction = myFramgmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.fl_mycontainer, fragmentToDisplay, FragmentTag1);
        myFragmentTransaction.commit();
    }
}


