package dhandev.android.oknoted.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dhandev.android.oknoted.data.local.NotesLocalStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    @Singleton
    fun provideNotesLocalStorage(
        @ApplicationContext context: Context
    ) = NotesLocalStorage(context)
}