package sk.tokar.matus.gr.di

import android.content.Context
import android.content.res.Resources
import dagger.Component
import sk.tokar.matus.gr.App
import sk.tokar.matus.gr.common.AppActivityManager
import sk.tokar.matus.gr.ui.UsersFragment
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidModule::class, AppModule::class]
)
interface AppComponent {
    fun provideContext(): Context
    fun provideResources(): Resources
    fun provideAppActivityManager(): AppActivityManager

    fun inject(usersFragment: UsersFragment)
}