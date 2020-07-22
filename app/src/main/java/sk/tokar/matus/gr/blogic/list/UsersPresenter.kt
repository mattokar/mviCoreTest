package sk.tokar.matus.gr.blogic.list

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import io.reactivex.Observable
import sk.tokar.matus.gr.api.ReqresApi
import sk.tokar.matus.gr.blogic.list.UsersPresenter.*

data class User(
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val email: String,
    val id: Int
)

class UsersPresenter(
    reqresApi: ReqresApi
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = State(),
    bootstrapper = BootStrapperImpl(),
    actor = ActorImpl(),
    newsPublisher = NewsPublisherImpl(),
    reducer = ReducerImpl()
)
{
    sealed class Wish {

    }

    sealed class Effect {

    }

    sealed class News {

    }

    data class State(
        val users: Collection<User> = emptyList(),
        val id: Int? = null
    )

    class BootStrapperImpl : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> = Observable.just(LoadNewImage)
    }

    class ActorImpl : Actor<State, Wish, Effect> {
        override fun invoke(state: State, action: Wish): Observable<out Effect> {
            TODO("Not yet implemented")
        }

    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            TODO("Not yet implemented")
        }

    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(action: Wish, effect: Effect, state: State): News? {
            TODO("Not yet implemented")
        }

    }
}