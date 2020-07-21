package com.example.gamerschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchContactActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Toolbar mToolbar;

    private Switch aSwitch;

    private EditText aSearch;

    private boolean searchByName = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);


        mToolbar = (Toolbar)findViewById(R.id.search_contacts_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        aSwitch = (Switch)findViewById(R.id.switch1) ;

        aSwitch.setOnCheckedChangeListener(this);

        getSupportActionBar().setTitle("Search By User Name");

        aSearch = findViewById(R.id.search_contacts);

        String name = aSearch.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("username", name);

        SearchContactFragment searchContactFragment = new SearchContactFragment();
        searchContactFragment.setArguments(bundle);
    }


    public static class SearchContactViewHolder extends RecyclerView.ViewHolder{
        TextView userName, userStatus;
        ImageView profileImage;

        public SearchContactViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_profile_name);

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(!isChecked) {
            searchByName = true;
            getSupportActionBar().setTitle("Search By User Name");



        }
        else {
            searchByName = false;
            getSupportActionBar().setTitle("Search By Favorite Game");
        }
    }

}
