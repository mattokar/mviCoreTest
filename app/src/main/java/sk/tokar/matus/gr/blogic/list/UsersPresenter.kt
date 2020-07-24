package sk.tokar.matus.gr.blogic.list

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import sk.tokar.matus.gr.api.ReqresApi
import sk.tokar.matus.gr.api.models.user_list.convert
import sk.tokar.matus.gr.blogic.list.UsersPresenter.*
import sk.tokar.matus.gr.common.toObservable
import javax.inject.Inject

data class User(
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val email: String,
    val id: Int
)

class UsersPresenter @Inject constructor(
    reqresApi: ReqresApi
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = State(),
    bootstrapper = BootStrapperImpl(),
    actor = ActorImpl(reqresApi),
    newsPublisher = NewsPublisherImpl(),
    reducer = ReducerImpl()
) {
    sealed class Wish {
        data class LoadPage(val index: Int = 1) : Wish()
        data class OpenUser(val id: Int) : Wish()
    }

    sealed class Effect {
        data class UsersPage(val page: List<User>) : Effect()
        data class Loading(val value: Boolean = false) : Effect()
        data class Error(val throwable: Throwable) : Effect()
        data class OpenDetail(val id: Int) : Effect()
    }

    sealed class News {
        data class OpenDetail(val id: Int) : News()
        data class Error(val throwable: Throwable) : News()
    }

    data class State(
        val users: List<User> = emptyList(),
        val loading: Boolean = false
    )

    class BootStrapperImpl : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> = Observable.just(Wish.LoadPage())
    }

    class ActorImpl(private val api: ReqresApi) : Actor<State, Wish, Effect> {
        private val PAGE_SIZE = 5

        override fun invoke(state: State, wish: Wish): Observable<out Effect> = when (wish) {
            is Wish.LoadPage -> api.getUsers(wish.index, PAGE_SIZE).toObservable()
                .map {
                    val newData = if (wish.index == 1) {
                        it.convert()
                    } else {
                        state.users.plus(it.convert())
                    }
                    Effect.UsersPage(newData) as Effect
                }
                .startWith(Effect.Loading(wish.index == 1))
                .onErrorReturn { Effect.Error(it) }
                .observeOn(AndroidSchedulers.mainThread())
            is Wish.OpenUser -> Effect.OpenDetail(wish.id).toObservable()
        }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            is Effect.UsersPage -> state.copy(users = effect.page, loading = false)
            is Effect.Loading -> state.copy(loading = effect.value)
            is Effect.Error -> state.copy(loading = false)
            else -> state
        }
    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(action: Wish, effect: Effect, state: State): News? = when (effect) {
            is Effect.OpenDetail -> News.OpenDetail(effect.id)
            is Effect.Error -> News.Error(effect.throwable)
            else -> null
        }

    }
}