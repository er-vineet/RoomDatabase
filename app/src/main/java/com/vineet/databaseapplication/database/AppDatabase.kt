package com.vineet.databaseapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val migration_1_2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user_table ADD COLUMN city TEXT DEFAULT 'Delhi'")
            }
        }

        fun getDatabase(context: Context) : AppDatabase {
            var instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                    .addMigrations(migration_1_2)
                    .build()
                INSTANCE = instance
                return instance as AppDatabase
            }
        }
    }

}