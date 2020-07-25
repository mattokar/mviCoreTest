package sk.tokar.matus.gr.di

import android.content.Context
import android.content.res.Resources
import dagger.Binds
import dagger.Module
import dagger.Provides
import sk.tokar.matus.gr.api.ReqresApi
import sk.tokar.matus.gr.blogic.NewsListener
import sk.tokar.matus.gr.blogic.details.UserDetailBindings
import sk.tokar.matus.gr.blogic.details.UserDetailPresenter
import sk.tokar.matus.gr.blogic.list.UsersListBindings
import sk.tokar.matus.gr.blogic.list.UsersPresenter
import sk.tokar.matus.gr.common.*
import javax.inject.Singleton

@Module
class FeaturesModule {

    @Provides
    fun provideUsersListBindings(presenter: UsersPresenter, listener: NewsListener): UsersListBindings = UsersListBindings(presenter, listener)

    @Provides
    fun provideUserDetailBindings(presenter: UserDetailPresenter, listener: NewsListener): UserDetailBindings = UserDetailBindings(presenter, listener)
}

@Module
abstract class CommonModule{

    @Singleton
    @Binds
    abstract fun provideAppActivityManager(appActivityManagerImpl: AppActivityManagerImpl): AppActivityManager

    @Singleton
    @Binds
    abstract fun provideAppNavigator(appNavigatorImpl: MainNavigatorImpl): MainNavigator
}

@Module
class NetworkModule() {

    @Singleton
    @Provides
    fun provideReqresAPi(): ReqresApi = ReqresApi.create()
}

@Module
class AndroidModule(context: Context) {
    private val context: Context = context.applicationContext

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideResources(): Resources = context.resources
}