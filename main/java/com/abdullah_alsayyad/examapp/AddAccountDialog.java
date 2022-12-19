package com.abdullah_alsayyad.examapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.abdullah_alsayyad.examapp.custom_view.FilterEditText;
import com.abdullah_alsayyad.examapp.data.entities.Account;
import com.abdullah_alsayyad.examapp.data.view_models.AccountsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AddAccountDialog {
    private ArrayList<Account> accounts;
    private Activity activity;
    private AccountsViewModel accountsVM;
    private AlertDialog dialog;

    private EditText eTextName, eTextPassword, eTextDescription;
    private FilterEditText eTextUsername;
    private Button btnAdd;

    public AddAccountDialog(Activity activity, AccountsViewModel accountsVM, ArrayList<Account> accounts) {
        this.accounts = accounts;
        this.activity = activity;
        this.accountsVM = accountsVM;
    }

    public AlertDialog getDialog() {
        buildView();
        return dialog;
    }

    private void buildView(){
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog1_new_account,
                activity.findViewById(R.id.d1_clayout));

        this.eTextName = view.findViewById(R.id.d1_etxt_name);
        this.eTextUsername = view.findViewById(R.id.d1_fetxt_username);
        this.eTextPassword = view.findViewById(R.id.d1_etxt_password);
        this.eTextDescription = view.findViewById(R.id.d1_etxt_description);
        this.btnAdd = view.findViewById(R.id.d1_btn_add);

        eTextUsername.setForbiddenCharacters(" ,/;.");

        btnAdd.setOnClickListener(v -> {
            String name, username, password, description;
            name = eTextName.getText().toString();
            username = eTextUsername.getText().toString();
            password = eTextPassword.getText().toString();
            description = eTextDescription.getText().toString();

            addNewAccount(name, username, password, description, view);
        });

        buildAlertDialog(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void buildAlertDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.bg_dialog));
        this.dialog = dialog;
    }

    @SuppressLint("StringFormatInvalid")
    private void addNewAccount(String name, String username, String password, String description, View v){
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Snackbar.make(activity, v, activity.getString(R.string.empty), 2000).setAction(activity.getString(R.string.close), view -> {
            }).show();
            return;
        }

        if(usernameIsExist(username)) {
            Snackbar.make(activity, v, activity.getString(R.string.username_exisit), 2000).setAction(activity.getString(R.string.close), view -> {
            }).show();
            return;
        }

        Account account = new Account(username, password, name, description);
        if (accountsVM.newAccount(account) != -1) {
            dialog.cancel();
            Toast.makeText(activity, activity.getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean usernameIsExist(String username){
        if (accounts == null) return false;
        for (Account account:
             accounts)
            if (username.equals(account.getUsername()))
                return true;

        return false;
    }
}
