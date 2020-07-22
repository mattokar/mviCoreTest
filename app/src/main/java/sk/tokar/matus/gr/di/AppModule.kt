package sk.tokar.matus.gr.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import sk.tokar.matus.gr.api.ReqresApi
import sk.tokar.matus.gr.blogic.list.UsersListBindings
import sk.tokar.matus.gr.blogic.list.UsersPresenter
import sk.tokar.matus.gr.common.*
import javax.inject.Singleton

@Module
class FeaturesModule {

    @Singleton
    @Provides
    fun provideUsersPresenter(reqresApi: ReqresApi): UsersPresenter = UsersPresenter(reqresApi)

    @Singleton
    @Provides
    fun provideUsersListBindings(presenter: UsersPresenter): UsersListBindings = UsersListBindings(presenter)
}

@Module
abstract class CommonModule(){

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