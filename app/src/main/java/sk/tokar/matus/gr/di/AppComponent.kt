package sk.tokar.matus.gr.di

import android.content.Context
import android.content.res.Resources
import dagger.Component
import sk.tokar.matus.gr.blogic.details.UserDetailBindings
import sk.tokar.matus.gr.blogic.list.UsersListBindings
import sk.tokar.matus.gr.common.AppActivityManager
import sk.tokar.matus.gr.ui.UsersFragment
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidModule::class, CommonModule::class, NetworkModule::class, FeaturesModule::class]
)
interface AppComponent {
    fun provideAppActivityManager(): AppActivityManager
    fun provideUserListBindings(): UsersListBindings
    fun provideUserDetailBindings(): UserDetailBindings
}