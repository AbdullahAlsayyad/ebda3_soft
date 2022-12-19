package com.abdullah_alsayyad.examapp.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.abdullah_alsayyad.examapp.data.Dao;
import com.abdullah_alsayyad.examapp.data.Database;
import com.abdullah_alsayyad.examapp.data.entities.Account;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AccountsRepository {
    private Dao dao;
    private LiveData<List<Account>> accounts;

    public AccountsRepository(@NotNull Application application) {
        Database database = Database.getDatabase(application);
        dao = database.getDao();
        accounts = dao.getAccounts();
    }

    public long newAccount(Account account){
        final long[] result = new long[1];
        Database.databaseWrite.execute(() -> result[0] = dao.newAccount(account));
        return result[0];
    }

    public void updateAccount(Account account){
        Database.databaseWrite.execute(() -> dao.updateAccount(account));
    }

    public void deleteAccount(Account account){
        Database.databaseWrite.execute(() -> dao.deleteAccount(account));
    }

    public void deleteAll(){
        Database.databaseWrite.execute(() -> dao.deleteAllAccounts());
    }

    public LiveData<List<Account>> getAccounts() {
        return accounts;
    }
}
