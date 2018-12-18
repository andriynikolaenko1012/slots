package com.andrew.nikolaenko.top_slots

import android.app.Application
import android.content.ContextWrapper
import com.andrew.nikolaenko.top_slots.network.IPService
import com.andrew.nikolaenko.top_slots.network.RetrofitFactory
import com.andrew.nikolaenko.top_slots.network.URLService
import com.github.salomonbrys.kodein.*
import org.jetbrains.anko.ctx

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<IPService>() with provider { RetrofitFactory.getForIp("http://www.ip-api.com").create(IPService::class.java) }
        bind<URLService>() with provider { RetrofitFactory.getForUrlDetect("https://us-central1-slots-api.cloudfunctions.net").create(URLService::class.java) }
    }

    override fun onCreate() {
        super.onCreate()

    }
}