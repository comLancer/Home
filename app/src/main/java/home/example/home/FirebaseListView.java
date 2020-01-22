package home.example.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseListView extends AppCompatActivity {


    private static final String TAG = "FirebaseUsersList";
    ImageAdapter mAdapter;
    DatabaseReference mRef;
    Context mContext;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.firebase_list_view, container, false);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference("users");

        ListView lvListView = parentView.findViewById(R.id.lv_inters);

        mAdapter = new ImageAdapter(FirebaseListView.this);
        readUsersListFromFirebase();
        lvListView.setAdapter(mAdapter);
        return parentView;


    }


    void readUsersListFromFirebase() {
// Read from the database
        mRef.addValueEventListener(new ValueEventListener() {

            ArrayList<Model> usersArrayList = new ArrayList<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersArrayList.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Model value = d.getValue(Model.class);
                    usersArrayList.add(value);
                }

                mAdapter.updateImageArrayList(usersArrayList);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}

