package com.example.gamerschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabsAccessorAdapter mtTabsAccessorAdapter;

    private String username, userId;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("userName").getValue().toString();
                //Log.e("aaaaaaaaaaaaaaaaaaaaa", username+"");
                getSupportActionBar().setTitle("Hi, " + username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);


        mViewPager = (ViewPager)findViewById(R.id.mainTabsPager);
        mtTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mtTabsAccessorAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.mainTabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_search_contacts_option){
            Intent intent = new Intent(this, SearchContactActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.main_search_games_option){

        }
        if(item.getItemId() == R.id.main_create_group_option){
            requestNewGroup();
        }
        if(item.getItemId() == R.id.main_settings_option){

        }
        if(item.getItemId() == R.id.main_logout_option){
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
    }

    private void requestNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);

        builder.setTitle("Enter Group Name:");
        final EditText groupNameField = new EditText(MainActivity.this);
        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName = groupNameField.getText().toString();

                if(TextUtils.isEmpty(groupName))
                    Toast.makeText(MainActivity.this, "Group name cannot be empty", Toast.LENGTH_SHORT).show();
                else
                    createNewGroup(groupName);


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void createNewGroup(String groupName) {
        myRef.getRoot().child("Groups").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Group has created", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
