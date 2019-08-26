package com.jandas.codechallenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jandas.codechallenge.db.entity.CartEntity
import com.jandas.codechallenge.model.cart.CartQuantityEntity

@Database(entities = [CartEntity::class, CartQuantityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun cartQuantityDao(): CartQuantityDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "coding_challenge.db"
            )
                .build()
    }
}