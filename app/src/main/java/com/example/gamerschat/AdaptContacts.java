package com.example.gamerschat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptContacts extends RecyclerView.Adapter<SearchContactActivity.SearchContactViewHolder> {

    Context context;
    List<Contacts> contactsList;

    public AdaptContacts(Context context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }




    @NonNull
    @Override
    public SearchContactActivity.SearchContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.users_display_layout, viewGroup, false);


        return new SearchContactActivity.SearchContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchContactActivity.SearchContactViewHolder holder, int position) {
        String userName = contactsList.get(position).getUserName();
        holder.userName.setText(userName);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
