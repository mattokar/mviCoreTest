package sk.tokar.matus.gr

import android.annotation.SuppressLint
import android.app.Application
import com.badoo.mvicore.consumer.middleware.LoggingMiddleware
import com.badoo.mvicore.consumer.middlewareconfig.MiddlewareConfiguration
import com.badoo.mvicore.consumer.middlewareconfig.Middlewares
import com.badoo.mvicore.consumer.middlewareconfig.WrappingCondition
import sk.tokar.matus.gr.common.AppActivityManager
import sk.tokar.matus.gr.di.AndroidModule
import sk.tokar.matus.gr.di.AppComponent
import sk.tokar.matus.gr.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var appActivityManager: AppActivityManager

    companion object {
         lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        component = DaggerAppComponent.builder()
            .androidModule(AndroidModule(this))
            .build()

        appActivityManager = component.provideAppActivityManager()
        appActivityManager.registerActivityLifecycleHelper(this)

        Middlewares.configurations.add(
            MiddlewareConfiguration(
                condition = WrappingCondition.Always,
                factories = listOf(
                    { consumer -> LoggingMiddleware(consumer, { Timber.d(it) }) }
                )
            )
        )
    }
}