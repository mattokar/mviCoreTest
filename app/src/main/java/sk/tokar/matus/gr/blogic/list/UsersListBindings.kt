package sk.tokar.matus.gr.blogic.list

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.android.lifecycle.ResumePauseBinderLifecycle
import com.badoo.mvicore.binder.Binder
import com.badoo.mvicore.binder.using
import sk.tokar.matus.gr.blogic.NewsListener
import sk.tokar.matus.gr.blogic.NewsListener.*
import sk.tokar.matus.gr.blogic.list.UsersPresenter.*
import sk.tokar.matus.gr.common.AppActivityManager
import sk.tokar.matus.gr.common.MainNavigator
import sk.tokar.matus.gr.common.Transformer
import sk.tokar.matus.gr.ui.UsersFragment
import sk.tokar.matus.gr.ui.UsersUiEvent
import sk.tokar.matus.gr.ui.UsersViewModel
import timber.log.Timber

class UsersListBindings(
    private val presenter: UsersPresenter,
    private val listener: NewsListener
) {
    private var initiated: Boolean = false
    fun create(view: UsersFragment) {
        if (!initiated) {
            val binder = Binder(ResumePauseBinderLifecycle(view.lifecycle))
            binder.bind(view to presenter using UsersViewToPresenter)
            binder.bind(presenter to view using UsersPresenterToView)
            binder.bind(presenter.news to listener using UserPresenterNewsToCommonNews)
            initiated = true
        }
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