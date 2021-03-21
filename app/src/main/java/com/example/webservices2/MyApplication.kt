package com.example.webservices2

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

//@Suppress("DEPRECATION")
@Suppress("DEPRECATION")
class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()

        if(myApplicationInstance == null){
            myApplicationInstance = this
        }
    }

    fun hasNetwork(): Boolean? {
        return myApplicationInstance?.isNetworkConnected()
        //return instance?.isNetworkConnected()
    }

    private fun isNetworkConnected(): Boolean{
        //var isConnected = false

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo


        return activeNetwork != null && activeNetwork.isConnectedOrConnecting

           // isConnected = true
       // return isConnected
    }

    companion object{
        var myApplicationInstance: MyApplication? = null
    }
}