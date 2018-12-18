package com.example.prime.testapplication.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.prime.testapplication.game.MainActivity
import com.example.prime.testapplication.network.IPModel
import com.example.prime.testapplication.network.IPService
import com.example.prime.testapplication.network.URLModel
import com.example.prime.testapplication.network.URLService
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*
import com.bumptech.glide.load.model.ModelLoader.LoadData
import org.jetbrains.anko.toast


class SplashActivity : KodeinAppCompatActivity() {

    protected val kodein = LazyKodein(appKodein)
    protected val ipService = kodein.instance<IPService>()
    protected val urlService = kodein.instance<URLService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val myDevide = checkPhoneModel()

        val timeZone = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT)
//        val text = timeZone.replace("0", "")
//        val text2 = text.replace("+", "")
//        val text3 = text2.replace(":", "")

//        val myTimeValue = text3.substring(3).trim()
        var myIp = ""

        if (isNetworkAvailable){

            ipService.value.getIpAddress()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t: IPModel ->

                        if (t.query != null){ myIp = t.query!! }
                        if (t.ip != null){ myIp = t.ip!! }

                        val yourData = LoadData("ip.txt")
                        val yourData2 = LoadData("ip2.txt")

                        if (yourData.contains(myIp) || yourData2.contains(myIp)){

                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                        } else {

                            if (timeZone.toString() == "UTC-10:00" ||  timeZone.toString() == "UTC-9:00" || timeZone.toString() == "UTC-8:00" ||
                                timeZone.toString() == "UTC-7:00" || timeZone.toString() == "UTC-6:00" || timeZone.toString() == "UTC-5:00" ||
                                timeZone.toString() == "UTC-4:00") {

                                startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                            } else {

                                if (Locale.getDefault().language.toString() == "ru"){

//                                    val url = "http://clickmonitor.website/139177"
//                                    val i = Intent(Intent.ACTION_VIEW)
//                                    i.data = Uri.parse(url)
//                                    startActivity(i)

                                    startActivity(Intent(this@SplashActivity, WebviewActivity::class.java))

                                } else {
                                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                                }

                            }

                        }

                    }, { e ->
                        e.printStackTrace()
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    })

        } else {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }

    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    private fun checkPhoneModel(): String{
        return android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
    }

    fun LoadData(inFile: String): String {
        var tContents = ""

        try {
            val stream = assets.open(inFile)

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            // Handle exceptions here
        }

        return tContents

    }

}
