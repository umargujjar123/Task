package com.example.basearchitectureproject.base

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.example.basearchitectureproject.data.localdb.RoomDB
import com.example.basearchitectureproject.util.EMPTY_STRING
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch

@HiltAndroidApp
class BaseApplication : MultiDexApplication() {

    override fun onCreate() {

        super.onCreate()
        context = baseContext

        try {
            Branch.getAutoInstance(this)
//            FirebaseApp.initializeApp(this);
//            RemoteConfigs.init()

            FacebookSdk.sdkInitialize(applicationContext);

            AppEventsLogger.activateApp(this)
//            FacebookSdk.setAutoLogAppEventsEnabled(true)
//            FacebookSdk.setAdvertiserIDCollectionEnabled(true)
//            FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS)
//
//            DefaultEventLogger.facebookLogger = AppEventsLogger.newLogger(this)

//            initializeWebEngage()
//            sendTokenToWebEngage()
//
//            if (BuildConfig.DEBUG) {
//                Barricade.Builder(this, BarricadeConfig.getInstance())
//                    .enableShakeToStart(this).install()
//                Timber.plant(DebugTree())
//            }

            RoomDB.init(baseContext)

        } catch (ex: Exception) {
//            Logs.generalEx(ex)
//            FirebaseCrashlytics.getInstance().recordException(ex)
        }
    }
//
//    private fun initializeWebEngage() {
//        val webEngageConfig = WebEngageConfig.Builder()
//            .setWebEngageKey(resources.getString(R.string.web_engage_key))
//            .setDebugMode(BuildConfig.DEBUG) // only in development mode
//            .build()
//        registerActivityLifecycleCallbacks(
//            WebEngageActivityLifeCycleCallbacks(
//                this,
//                webEngageConfig
//            )
//        )
//    }
//
//    private fun sendTokenToWebEngage() {
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (task.isSuccessful.not()) {
//                task.exception?.cause?.let {
//                    FirebaseCrashlytics.getInstance().recordException(it)
//                }
//                return@OnCompleteListener
//            }
//            val token = task.result
//            WebEngage.get().setRegistrationID(token);
//        })
//    }

    companion object {

        //Todo: need to remove these properties from here
        lateinit var context: Context
        var oosError: Boolean = false
        var selectedTimeSlab: String = EMPTY_STRING

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        @JvmStatic
        fun toast(message: String?, length: Int = Toast.LENGTH_LONG) {
            Toast.makeText(context, message, length).show()
        }
    }

}