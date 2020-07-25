package sk.tokar.matus.gr.blogic.list

import com.badoo.mvicore.android.lifecycle.CreateDestroyBinderLifecycle
import com.badoo.mvicore.android.lifecycle.ResumePauseBinderLifecycle
import com.badoo.mvicore.binder.Binder
import com.badoo.mvicore.binder.using
import sk.tokar.matus.gr.blogic.NewsListener
import sk.tokar.matus.gr.blogic.NewsListener.CommonNews
import sk.tokar.matus.gr.blogic.list.UsersPresenter.*
import sk.tokar.matus.gr.common.Bindings
import sk.tokar.matus.gr.common.MviFragment
import sk.tokar.matus.gr.common.Transformer
import sk.tokar.matus.gr.ui.UsersUiEvent
import sk.tokar.matus.gr.ui.UsersViewModel

class UsersListBindings(
    private val presenter: UsersPresenter,
    private val listener: NewsListener
) : Bindings<UsersUiEvent, UsersViewModel>() {
    override fun create(view: MviFragment<UsersUiEvent, UsersViewModel>) {
        val binder = Binder(ResumePauseBinderLifecycle(view.lifecycle))
        binder.bind(view to presenter using UsersViewToPresenter)
        binder.bind(presenter to view using UsersPresenterToView)
        binder.bind(presenter.news to listener using UserPresenterNewsToCommonNews)
    }
}

object UsersViewToPresenter : Transformer<UsersUiEvent, Wish>() {
    override fun action(input: UsersUiEvent): Wish? = when (input) {
        is UsersUiEvent.UserSelected -> Wish.OpenUser(input.id)
        is UsersUiEvent.NextPage -> Wish.LoadPage(input.page)
    }
}

object UsersPresenterToView : Transformer<State, UsersViewModel>() {
    override fun action(input: State): UsersViewModel? = UsersViewModel(
        users = input.users,
        loading = input.loading
    )
}

object UserPresenterNewsToCommonNews : Transformer<News, CommonNews>() {
    override fun action(input: News): CommonNews? = when (input) {
        is News.OpenDetail -> CommonNews.OpenDetail(input.id)
        is News.Error -> CommonNews.Error(input.throwable)
    }
}