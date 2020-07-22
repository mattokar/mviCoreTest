package sk.tokar.matus.gr.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_users.*
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.blogic.list.User
import sk.tokar.matus.gr.blogic.list.UsersListBindings
import sk.tokar.matus.gr.common.MainNavigator
import sk.tokar.matus.gr.common.MviFragment
import javax.inject.Inject


sealed class UsersUiEvent {
    data class UserSelected(val id: Int) : UsersUiEvent()
    data class NextPage(val page: Int) : UsersUiEvent()
}

data class UsersViewModel(
    val users: Collection<User> = emptyList(),
    val openDetail: Int? = null
)

class UsersFragment : MviFragment<UsersUiEvent, UsersViewModel>() {

    @Inject
    lateinit var navigator: MainNavigator

    @Inject
    lateinit var bindings: UsersListBindings

    private lateinit var adapter: UserListAdapter

    private val lazyListener = RecyclerViewLazyListener({
        onNext(UsersUiEvent.NextPage(it))
    })

    override fun getLayoutResId(): Int = R.layout.fragment_users

    override fun init(view: View, savedInstanceState: Bundle?) {
        adapter = UserListAdapter { onNext(UsersUiEvent.UserSelected(it.id)) }
        rv_users.apply {
            adapter = this@UsersFragment.adapter
            layoutManager = LinearLayoutManager(this@UsersFragment.context)
            removeOnScrollListener(lazyListener)
            addOnScrollListener(lazyListener)
        }
        bindings.create(this)
    }

    override fun accept(model: UsersViewModel?) {
        TODO("Not yet implemented")
    }

}