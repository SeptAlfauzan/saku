package org.kudos.saku

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.kudos.saku.di.initKoin
import org.kudos.saku.di.platformModule
import org.kudos.saku.di.sharedModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
        }
    }
}