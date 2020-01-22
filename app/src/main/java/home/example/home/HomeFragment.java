package home.example.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.activity_home_fragment, container, false);

        Button btnBeauty = parentView.findViewById(R.id.btn_beauty);
        Button btnPartyOrganizer = parentView.findViewById(R.id.btn_party_organizer);
        Button btPhotography = parentView.findViewById(R.id.btn_photography);
        Button btnProgramming = parentView.findViewById(R.id.btn_programming);


        btnBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTo(new BeautyFragment(), BeautyFragment.class.getSimpleName());
            }
        });

        btnPartyOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragmentTo(new PartyOrganizerFragment(), PartyOrganizerFragment.class.getSimpleName());
            }
        });

        btPhotography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragmentTo(new PhotographyFragment(), PhotographyFragment.class.getSimpleName());
            }
        });
        btnProgramming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragmentTo(new PhotographyFragment(), PhotographyFragment.class.getSimpleName());
            }
        });


        return parentView;


    }

    void changeFragmentTo(Fragment fragmentToDisplay, String FragmentTag) {    //this fragment will change the fragment as we want
        FragmentManager myFramgmentManager = getFragmentManager();
        FragmentTransaction myFragmentTransaction = myFramgmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.fl_mycontainer, fragmentToDisplay, FragmentTag);
        myFragmentTransaction.commit();
    }


}