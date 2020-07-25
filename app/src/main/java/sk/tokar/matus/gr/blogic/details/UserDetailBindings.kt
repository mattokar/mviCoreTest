package sk.tokar.matus.gr.blogic.details

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import sk.tokar.matus.gr.blogic.NewsListener
import sk.tokar.matus.gr.blogic.NewsListener.*
import sk.tokar.matus.gr.blogic.details.UserDetailPresenter.*
import sk.tokar.matus.gr.blogic.list.UsersPresenter
import sk.tokar.matus.gr.common.Transformer
import sk.tokar.matus.gr.ui.UserDetailUiEvent
import sk.tokar.matus.gr.ui.UserDetailViewModel
import sk.tokar.matus.gr.ui.UserDetailsFragment

class UserDetailBindings(
    private val presenter: UserDetailPresenter,
    private val listener: NewsListener
) {

    fun create(view: UserDetailsFragment) {
        object : AndroidBindings<UserDetailsFragment>(view){
            override fun setup(view: UserDetailsFragment) {
                binder.bind(view to presenter using UserDetailViewToPresenter)
                binder.bind(presenter to view using UserDetailPresenterToView)
                binder.bind(presenter.news to listener using UserDetailPresenterNewsToCommonNews)
            }
        }.setup(view)
    }
}

object UserDetailViewToPresenter : Transformer<UserDetailUiEvent, Wish>(){
    override fun action(input: UserDetailUiEvent): Wish? = when(input){
        is UserDetailUiEvent.Init -> Wish.Init(input.id)
    }
}

object UserDetailPresenterToView : Transformer<State, UserDetailViewModel>(){
    override fun action(input: State): UserDetailViewModel? = UserDetailViewModel(
        userData = input.userData,
        loading = input.loading
    )
}

object UserDetailPresenterNewsToCommonNews : Transformer<News, CommonNews>() {
    override fun action(input: News): CommonNews? = when(input){
        is News.OpenList -> CommonNews.OpenList
        is News.Error -> CommonNews.Error(input.throwable)
    }

}