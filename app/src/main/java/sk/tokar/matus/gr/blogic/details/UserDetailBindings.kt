package sk.tokar.matus.gr.blogic.details

import com.badoo.mvicore.android.lifecycle.CreateDestroyBinderLifecycle
import com.badoo.mvicore.binder.Binder
import com.badoo.mvicore.binder.using
import sk.tokar.matus.gr.blogic.NewsListener
import sk.tokar.matus.gr.blogic.NewsListener.CommonNews
import sk.tokar.matus.gr.blogic.details.UserDetailPresenter.*
import sk.tokar.matus.gr.common.Bindings
import sk.tokar.matus.gr.common.MviFragment
import sk.tokar.matus.gr.common.Transformer
import sk.tokar.matus.gr.ui.UserDetailUiEvent
import sk.tokar.matus.gr.ui.UserDetailViewModel

class UserDetailBindings(
    private val presenter: UserDetailPresenter,
    private val listener: NewsListener
): Bindings<UserDetailUiEvent, UserDetailViewModel>() {
    override fun create(view: MviFragment<UserDetailUiEvent, UserDetailViewModel>) {
            val binder = Binder(CreateDestroyBinderLifecycle(view.lifecycle))
            binder.bind(view to presenter using UserDetailViewToPresenter)
            binder.bind(presenter to view using UserDetailPresenterToView)
            binder.bind(presenter.news to listener using UserDetailPresenterNewsToCommonNews)
    }
}

object UserDetailViewToPresenter : Transformer<UserDetailUiEvent, Wish>() {
    override fun action(input: UserDetailUiEvent): Wish? = when (input) {
        is UserDetailUiEvent.Init -> Wish.Init(input.id)
    }
}

object UserDetailPresenterToView : Transformer<State, UserDetailViewModel>() {
    override fun action(input: State): UserDetailViewModel? = UserDetailViewModel(
        userData = input.userData,
        loading = input.loading
    )
}

object UserDetailPresenterNewsToCommonNews : Transformer<News, CommonNews>() {
    override fun action(input: News): CommonNews? = when (input) {
        is News.OpenList -> CommonNews.OpenList
        is News.Error -> CommonNews.Error(input.throwable)
    }

}