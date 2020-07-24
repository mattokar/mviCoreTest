package sk.tokar.matus.gr.blogic.list

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import sk.tokar.matus.gr.blogic.NewsListener
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
)
{
    fun create(view: UsersFragment) {
        object : AndroidBindings<UsersFragment>(view){
            override fun setup(view: UsersFragment) {
                binder.bind(view to presenter using UsersViewToPresenter)
                binder.bind(presenter to view using UsersPresenterToView)
                binder.bind(presenter.news to listener using UserPresenterNewsToCommonNews)
            }
        }.setup(view)
    }
}

object UsersViewToPresenter : Transformer<UsersUiEvent, UsersPresenter.Wish>() {
    override fun action(input: UsersUiEvent): UsersPresenter.Wish? = when (input) {
        is UsersUiEvent.UserSelected -> UsersPresenter.Wish.OpenUser(input.id)
        is UsersUiEvent.NextPage -> UsersPresenter.Wish.LoadPage(input.page)
    }
}

object UsersPresenterToView : Transformer<UsersPresenter.State, UsersViewModel>() {
    override fun action(input: UsersPresenter.State): UsersViewModel? = UsersViewModel(
        users = input.users,
        loading = input.loading
    )
}

object UserPresenterNewsToCommonNews : Transformer<UsersPresenter.News, NewsListener.CommonNews>() {
    override fun action(input: UsersPresenter.News): NewsListener.CommonNews? = when (input) {
        is UsersPresenter.News.OpenDetail -> NewsListener.CommonNews.OpenDetail(input.id)
        is UsersPresenter.News.Error -> NewsListener.CommonNews.Error(input.throwable)
    }
}