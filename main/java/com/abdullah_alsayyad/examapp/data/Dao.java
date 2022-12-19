package com.abdullah_alsayyad.examapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.abdullah_alsayyad.examapp.data.entities.Account;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    long newAccount(Account account);

    @Update
    void updateAccount(Account account);

    @Delete
    void deleteAccount(Account account);

    @Query("DELETE FROM accounts_table WHERE username NOT LIKE '%;'")
    void deleteAllAccounts();

    @Query("SELECT * FROM accounts_table ORDER BY name DESC")
    LiveData<List<Account>> getAccounts();

}
