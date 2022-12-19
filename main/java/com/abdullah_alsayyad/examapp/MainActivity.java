package com.abdullah_alsayyad.examapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdullah_alsayyad.examapp.custom_view.FilterEditText;
import com.abdullah_alsayyad.examapp.data.entities.Account;
import com.abdullah_alsayyad.examapp.data.view_models.AccountsViewModel;
import com.abdullah_alsayyad.examapp.recycler_view_adapters.Act1_RecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    private AccountsViewModel accountsVM;
    private ArrayList<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act1_main);

//        change bar color
        changeNavigationBarColor(this);

//        recycler view and adapter for accounts
        RecyclerView rView = findViewById(R.id.act1_r_view);
        Act1_RecyclerViewAdapter adapter = new Act1_RecyclerViewAdapter();

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(adapter);

//        view model for accounts
        accountsVM = new ViewModelProvider(this).get(AccountsViewModel.class);
        accountsVM.getAccounts().observe(this, accounts -> {
            if (accounts.size() == 0) {
                MainActivity.this.findViewById(R.id.act1_txt_no_data).setVisibility(View.VISIBLE);
                return;
            }

            MainActivity.this.findViewById(R.id.act1_txt_no_data).setVisibility(View.GONE);
            this.accounts = (ArrayList<Account>) accounts;
            adapter.setAccounts(accounts);
        });

//        swipe for delete item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(10,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Account account = adapter.getAccount(viewHolder.getAdapterPosition());
                accountsVM.deleteAccount(account);
                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.delete), 2000)
                        .setAction(getResources().getString(R.string._return), v -> {accountsVM.newAccount(account);}).show();
            }
        }).attachToRecyclerView(rView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act1, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_act1_add:
                showAddNewAccountDialog();
                return true;
            case R.id.m_act1_delete:
                Snackbar.make(findViewById(android.R.id.content),
                        getResources().getString(R.string.how_delete), 3000).show();
                return true;

//             ERROR   not deleted
/*            case R.id.m_act1_delete_all:
                accountsVM.deleteAll();
                Toast.makeText(this, getResources().getString(R.string.all_deleted), Toast.LENGTH_LONG).show();
                recreate();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    add account dialog
    @SuppressLint("UseCompatLoadingForDrawables")
    private void showAddNewAccountDialog() {
        AddAccountDialog dialog = new AddAccountDialog( this, this.accountsVM, this.accounts);
        dialog.getDialog().show();
    }

    /**
     * To change navigation bar color
     * @param activity NavigationBarColor activity has navigation bar
     */
    public static void changeNavigationBarColor(Activity activity){
        activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.orange));
    }

}