package edu.moravian.csci215.misophoniaapp

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: MyApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}