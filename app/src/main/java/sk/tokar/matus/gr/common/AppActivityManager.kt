package sk.tokar.matus.gr.common

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sk.tokar.matus.gr.App
import java.lang.ref.WeakReference
import javax.inject.Inject

interface AppActivityManager {
    fun getCurrentActivity(): AppCompatActivity?
    fun setCurrentActivity(baseActivity: AppCompatActivity?)
    fun registerActivityLifecycleHelper(application: App)
}

class AppActivityManagerImpl @Inject constructor(
) : AppActivityManager {

    private var currentActivity: WeakReference<AppCompatActivity?>? = null

    override fun getCurrentActivity(): AppCompatActivity? {
        return currentActivity?.get()
    }

    override fun setCurrentActivity(baseActivity: AppCompatActivity?) {
        this.currentActivity = WeakReference(baseActivity)
    }

    override fun registerActivityLifecycleHelper(application: App) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                setCurrentActivity(activity as? AppCompatActivity?)
            }

            override fun onActivityResumed(activity: Activity) {
                setCurrentActivity(activity as? AppCompatActivity?)
            }

            override fun onActivityStarted(activity: Activity) {
                setCurrentActivity(activity as? AppCompatActivity?)
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityDestroyed(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityStopped(activity: Activity) {}
        })
    }
}