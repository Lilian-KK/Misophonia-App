package edu.moravian.csci215.misophoniaapp.server_data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import edu.moravian.csci215.misophoniaapp.MyApplication
import okio.FileSystem
import okio.Path.Companion.toPath


actual fun createDataStore(): DataStore<Preferences> = DataStoreFactory.create(
    storage = OkioStorage(
        serializer = PreferencesSerializer,
        fileSystem = FileSystem.SYSTEM,
        producePath = { MyApplication.applicationContext().filesDir.resolve(dataStoreFileName).absolutePath.toPath() }
    )
)
