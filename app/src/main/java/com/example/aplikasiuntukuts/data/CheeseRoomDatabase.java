package com.example.aplikasiuntukuts.data;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Cheese.class}, version = 1, exportSchema = false)
public class CheeseRoomDatabase extends RoomDatabase{
    public abstract CheeseDao wordDao();

    private static volatile CheeseRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CheeseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CheeseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CheeseRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                CheeseDao dao = INSTANCE.wordDao();
                dao.deleteAll();

                Cheese cheese = new Cheese("Hello");
                dao.insert(cheese);
                cheese = new Cheese("World");
                dao.insert(cheese);
            });
        }
    };
}
