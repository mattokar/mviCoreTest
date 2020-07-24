package sk.tokar.matus.gr.blogic.details

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import io.reactivex.Observable
import sk.tokar.matus.gr.api.ReqresApi
import sk.tokar.matus.gr.api.models.user_details.UserDetails
import sk.tokar.matus.gr.blogic.details.UserDetailPresenter.*
import sk.tokar.matus.gr.blogic.list.UsersPresenter
import javax.inject.Inject

class UserDetailPresenter@Inject constructor(
    reqresApi: ReqresApi
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = State(),
    actor = ActorImpl(reqresApi),
    newsPublisher = NewsPublisherImpl(),
    reducer = ReducerImpl()
)  {

    sealed class Wish {

    }

    sealed class Effect {

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
        override fun invoke(state: State, action: Wish): Observable<out Effect> {
            TODO("Not yet implemented")
        }
    }

    class ReducerImpl: Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            TODO("Not yet implemented")
        }
    }

    class NewsPublisherImpl: NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(action: Wish, effect: Effect, state: State): News? {
            TODO("Not yet implemented")
        }

    }

}