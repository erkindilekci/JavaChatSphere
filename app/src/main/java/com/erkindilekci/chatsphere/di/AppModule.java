package com.erkindilekci.chatsphere.di;

import android.content.Context;

import com.erkindilekci.chatsphere.data.repository.ChatRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    public DatabaseReference provideDatabaseReference(FirebaseDatabase database) {
        return database.getReference();
    }

    @Provides
    @Singleton
    public ChatRepository provideChatRepository(FirebaseAuth mAuth, DatabaseReference mDbRef, @ApplicationContext Context context) {
        return new ChatRepository(mAuth, mDbRef, context);
    }
}
