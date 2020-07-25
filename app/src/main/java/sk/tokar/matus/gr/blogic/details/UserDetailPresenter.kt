package sk.tokar.matus.gr.blogic.details

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import sk.tokar.matus.gr.api.ReqresApi
import sk.tokar.matus.gr.api.models.user_details.convert
import sk.tokar.matus.gr.blogic.details.UserDetailPresenter.*
import javax.inject.Inject

data class UserDetails(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String
)

class UserDetailPresenter@Inject constructor(
    reqresApi: ReqresApi
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = State(),
    actor = ActorImpl(reqresApi),
    newsPublisher = NewsPublisherImpl(),
    reducer = ReducerImpl()
)  {

    sealed class Wish {
        data class Init(val id: Int): Wish()
    }

    sealed class Effect {
        data class UserData(val value: UserDetails) : Effect()
        object Loading : Effect()
        data class Error(val throwable: Throwable) : Effect()
    }

    sealed class News {
        object OpenList : News()
        data class Error(val throwable: Throwable) : News()
    }

    data class State (
        val userData: UserDetails? = null,
        val loading: Boolean = false
    )

    class ActorImpl(private val api: ReqresApi) : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> = when(wish){
            is Wish.Init -> api.getUserDetails(wish.id)
                .toObservable()
                .map { Effect.UserData(it.convert()) as Effect}
                .startWith( Effect.Loading)
                .onErrorReturn { Effect.Error(it) }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    class ReducerImpl: Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when(effect){
            is Effect.UserData -> state.copy(userData = effect.value, loading = false)
            Effect.Loading -> state.copy(loading = true)
            is Effect.Error -> state.copy(loading = true)
        }
    }

    class NewsPublisherImpl: NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(action: Wish, effect: Effect, state: State): News? = when(effect){
            is Effect.Error -> News.Error(effect.throwable)
            else -> null
        }

    }

}