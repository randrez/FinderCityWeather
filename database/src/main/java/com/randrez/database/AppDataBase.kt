package com.randrez.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.randrez.database.dao.WeatherInfoDAO
import com.randrez.database.entities.WeatherInfoEntity


@Database(
    entities = [WeatherInfoEntity::class],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun weatherInfoDao(): WeatherInfoDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context,
                    klass = AppDataBase::class.java,
                    name = BuildConfig.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}