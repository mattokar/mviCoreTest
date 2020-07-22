package sk.tokar.matus.gr.blogic.list

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import sk.tokar.matus.gr.ui.UsersFragment
import sk.tokar.matus.gr.ui.UsersUiEvent
import sk.tokar.matus.gr.ui.UsersViewModel

class UsersListBindings(
    private val presenter: UsersPresenter
)
{
    fun create(view: UsersFragment) {
        object : AndroidBindings<UsersFragment>(view){
            override fun setup(view: UsersFragment) {
                binder.bind(view to presenter using UsersViewToPresenter)
                binder.bind(presenter to view using UsersPresenterToView)
            }
        }.setup(view)
    }
}

object UsersViewToPresenter : (UsersUiEvent) -> UsersPresenter.Wish? {

    override fun invoke(event: UsersUiEvent): UsersPresenter.Wish? = when(event){
        is UsersUiEvent.UserSelected -> TODO()
    }
}

object UsersPresenterToView : (UsersPresenter.State) -> UsersViewModel? {

    override fun invoke(state: UsersPresenter.State): UsersViewModel? = UsersViewModel(
        users = state.users,
        openDetail = state.id
    )
}