package com.abdullah_alsayyad.examapp.data.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abdullah_alsayyad.examapp.data.Database;
import com.abdullah_alsayyad.examapp.data.entities.Account;
import com.abdullah_alsayyad.examapp.data.repositories.AccountsRepository;

import java.util.List;

public class AccountsViewModel extends AndroidViewModel {
    private AccountsRepository repository;
    private LiveData<List<Account>> accounts;

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountsRepository(application);
        accounts = repository.getAccounts();
    }

    /**
     *
     * @param account
     * @return will return id or number of element
     */
    public long newAccount(Account account){return repository.newAccount(account);}

    public void updateAccount(Account account){repository.updateAccount(account);}

    public void deleteAccount(Account account){repository.deleteAccount(account);}

    public void deleteAll(){repository.deleteAll();}

    public LiveData<List<Account>> getAccounts() {
        return accounts;
    }
}
