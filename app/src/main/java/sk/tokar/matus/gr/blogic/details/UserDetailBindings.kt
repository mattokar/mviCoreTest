package sk.tokar.matus.gr.blogic.details

import androidx.fragment.app.Fragment
import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.android.lifecycle.ResumePauseBinderLifecycle
import com.badoo.mvicore.binder.Binder
import com.badoo.mvicore.binder.lifecycle.Lifecycle
import com.badoo.mvicore.binder.using
import sk.tokar.matus.gr.blogic.NewsListener
import sk.tokar.matus.gr.blogic.NewsListener.*
import sk.tokar.matus.gr.blogic.details.UserDetailPresenter.*
import sk.tokar.matus.gr.blogic.list.UsersPresenter
import sk.tokar.matus.gr.common.MviFragment
import sk.tokar.matus.gr.common.Transformer
import sk.tokar.matus.gr.ui.UserDetailUiEvent
import sk.tokar.matus.gr.ui.UserDetailViewModel
import sk.tokar.matus.gr.ui.UserDetailsFragment

class UserDetailBindings(
    private val presenter: UserDetailPresenter,
    private val listener: NewsListener
): Bindings<UserDetailUiEvent, UserDetailViewModel>() {
    override fun create(view: MviFragment<UserDetailUiEvent, UserDetailViewModel>) {
            val binder = Binder(ResumePauseBinderLifecycle(view.lifecycle))
            binder.bind(view to presenter using UserDetailViewToPresenter)
            binder.bind(presenter to view using UserDetailPresenterToView)
            binder.bind(presenter.news to listener using UserDetailPresenterNewsToCommonNews)
    }
}

abstract class Bindings<OUTPUT, INPUT> {
    private var initiated: Boolean = false
    abstract fun create(view: MviFragment<OUTPUT, INPUT>)

    fun setup(view: MviFragment<OUTPUT, INPUT>) {
        if (!initiated) {
            create(view)
            initiated = true
        }
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