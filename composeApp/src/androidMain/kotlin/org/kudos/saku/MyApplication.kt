package org.kudos.saku

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.kudos.saku.di.initKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)

        }
    }
}