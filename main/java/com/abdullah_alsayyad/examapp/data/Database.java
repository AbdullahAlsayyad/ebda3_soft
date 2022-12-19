package com.abdullah_alsayyad.examapp.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.abdullah_alsayyad.examapp.data.entities.Account;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = Account.class, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract Dao getDao();

    private static volatile com.abdullah_alsayyad.examapp.data.Database database;

    private static final int NUMBER_OF_THREADS = 4;
    public static ExecutorService databaseWrite = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Database getDatabase(@NotNull Context context) {
        if (database == null) {
            synchronized (Database.class){
                if (database == null) {
                    database = Room.databaseBuilder(context, Database.class, "main_DB")
                            /*.addCallback(tempData)*/.build();
                }
            }
        }
        return database;
    }
//    Temp data for test
/*
    private static Callback tempData = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWrite.execute(() -> {
                Dao dao = database.getDao();
                dao.newAccount(new Account("as1a", "0011", "Ali1", "sdjfisjdfi1"));
                dao.newAccount(new Account("as2a", "0022", "Ali2", "sdjfisjdfi2"));
                dao.newAccount(new Account("as3a", "0033", "Ali3", "sdjfisjdfi3"));
            });
        }
    };

 */
}
