package com.example.womensecurity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewUser.setText(user.toString());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public void addUser(User user) {
        userList.add(user);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUser = itemView.findViewById(android.R.id.text1);
        }
    }
}