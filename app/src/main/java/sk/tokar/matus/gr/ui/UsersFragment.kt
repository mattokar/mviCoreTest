package sk.tokar.matus.gr.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_users.*
import sk.tokar.matus.gr.App
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.blogic.list.User
import sk.tokar.matus.gr.common.Bindings
import sk.tokar.matus.gr.common.MviFragment
import sk.tokar.matus.gr.common.RecyclerViewLazyListener


sealed class UsersUiEvent {
    data class UserSelected(val id: Int) : UsersUiEvent()
    data class NextPage(val page: Int = 1) : UsersUiEvent()
}

data class UsersViewModel(
    val users: List<User> = emptyList(),
    val loading: Boolean = false
)

class UsersFragment : MviFragment<UsersUiEvent, UsersViewModel>() {

    private lateinit var adapter: UserListAdapter
    private val lazyListener = RecyclerViewLazyListener({
        onNext(UsersUiEvent.NextPage(it))
    })

    override fun getLayoutResId(): Int = R.layout.fragment_users
    override fun getBindings(): Bindings<UsersUiEvent, UsersViewModel> = App.component.provideUserListBindings()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        adapter = UserListAdapter { onNext(UsersUiEvent.UserSelected(it.id)) }
        rv_users.apply {
            adapter = this@UsersFragment.adapter
            layoutManager = LinearLayoutManager(this@UsersFragment.context)
            removeOnScrollListener(lazyListener)
            addOnScrollListener(lazyListener)
        }
        swipe_container.setOnRefreshListener {
            lazyListener.reset()
            onNext(UsersUiEvent.NextPage())
        }
    }

    override fun accept(model: UsersViewModel) {
        adapter.update(model.users)
        swipe_container.isRefreshing = model.loading
    }
}