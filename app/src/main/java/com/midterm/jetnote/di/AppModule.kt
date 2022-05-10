package com.midterm.jetnote.di

import android.content.Context
import androidx.room.Room
import com.midterm.jetnote.data.NoteDatabase
import com.midterm.jetnote.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): NoteDatabase = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "notes"
    ).fallbackToDestructiveMigration()
        .build()
}