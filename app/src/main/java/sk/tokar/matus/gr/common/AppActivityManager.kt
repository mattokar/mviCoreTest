package sk.tokar.matus.gr.common

import android.app.Activity
import android.app.Application
import android.os.Bundle
import sk.tokar.matus.gr.App
import java.lang.ref.WeakReference
import javax.inject.Inject

interface AppActivityManager {
    fun getCurrentActivity(): BaseActivity?
    fun setCurrentActivity(baseActivity: BaseActivity?)
    fun registerActivityLifecycleHelper(application: App)
}

class AppActivityManagerImpl @Inject constructor(
) : AppActivityManager {

    private var currentActivity: WeakReference<BaseActivity?>? = null

    override fun getCurrentActivity(): BaseActivity? {
        return currentActivity?.get()
    }

    override fun setCurrentActivity(baseActivity: BaseActivity?) {
        this.currentActivity = WeakReference(baseActivity)
    }

    override fun registerActivityLifecycleHelper(application: App) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                setCurrentActivity(activity as? BaseActivity?)
            }

            override fun onActivityResumed(activity: Activity) {
                setCurrentActivity(activity as? BaseActivity?)
            }

            override fun onActivityStarted(activity: Activity) {
                setCurrentActivity(activity as? BaseActivity?)
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityDestroyed(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityStopped(activity: Activity) {}
        })
    }
}