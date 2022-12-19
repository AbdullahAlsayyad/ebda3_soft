package com.abdullah_alsayyad.examapp.recycler_view_adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdullah_alsayyad.examapp.R;
import com.abdullah_alsayyad.examapp.data.entities.Account;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Act1_RecyclerViewAdapter extends RecyclerView.Adapter<Act1_RecyclerViewAdapter.ViewHolder> {

    private List<Account> accounts = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ly1_item_recycler_view_accounts,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(accounts.get(position).getName());
        holder.txtDescription.setText(accounts.get(position).getDescription());
        holder.itemView.setOnClickListener(v -> showDetails(holder.itemView.findViewById(R.id.ly1_details)));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public Account getAccount(int index) {
        return this.accounts.get(index);
    }

    public void showDetails(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            return;
        }

        view.setVisibility(View.GONE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.ly1_txt_name);
            txtDescription = itemView.findViewById(R.id.ly1_txt_description);
        }
    }
}
