package com.example.gamerschat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchContactFragment extends Fragment {
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    RecyclerView recyclerView;
    private AdaptContacts adaptContacts;
    private List<Contacts> contactsList;

    private EditText aSearch;

    private boolean searchByName = true;

    public SearchContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // String userName = getArguments().getString("username");

     //   Log.e("aaaaaa", " "+userName);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_conatct, container, false);

        recyclerView = view.findViewById(R.id.search_contacts_recycle_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        contactsList = new ArrayList<>();

        getAllContacts();

        return view;
    }

    private void getAllContacts() {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contactsList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Contacts contacts = ds.getValue(Contacts.class);

                    if(!contacts.getUid().equals(firebaseUser.getUid()))
                        contactsList.add(contacts);


                    adaptContacts = new AdaptContacts(getActivity(), contactsList);
                    recyclerView.setAdapter(adaptContacts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
