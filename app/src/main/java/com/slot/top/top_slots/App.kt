package com.slot.top.top_slots

import android.app.Application
import com.slot.top.top_slots.network.IPService
import com.slot.top.top_slots.network.RetrofitFactory
import com.slot.top.top_slots.network.URLService
import com.github.salomonbrys.kodein.*

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<IPService>() with provider { RetrofitFactory.getForIp("http://www.ip-api.com").create(IPService::class.java) }
        bind<URLService>() with provider { RetrofitFactory.getForUrlDetect("https://us-central1-slots-api.cloudfunctions.net").create(URLService::class.java) }
    }

    override fun onCreate() {
        super.onCreate()

    }
}