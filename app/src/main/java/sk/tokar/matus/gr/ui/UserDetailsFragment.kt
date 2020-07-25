package sk.tokar.matus.gr.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.fragment_user_details.swipe_container
import kotlinx.android.synthetic.main.fragment_users.*
import sk.tokar.matus.gr.App
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.blogic.details.UserDetailBindings
import sk.tokar.matus.gr.blogic.details.UserDetails
import sk.tokar.matus.gr.blogic.list.UsersListBindings
import sk.tokar.matus.gr.common.MviFragment
import sk.tokar.matus.gr.common.loadImage
import javax.inject.Inject

sealed class UserDetailUiEvent {
    data class Init (val id: Int) : UserDetailUiEvent()
}

data class UserDetailViewModel (
    val userData: UserDetails? = null,
    val loading: Boolean = false
)

class UserDetailsFragment : MviFragment<UserDetailUiEvent, UserDetailViewModel>() {

    @Inject
    lateinit var bindings: UserDetailBindings

    private val args: UserDetailsFragmentArgs by navArgs()

    override fun getLayoutResId(): Int = R.layout.fragment_user_details

    override fun init(view: View, savedInstanceState: Bundle?) {
        bindings = App.component.provideUserDetailBindings()
        swipe_container.setOnRefreshListener {
            loadUser()
        }
        bindings.create(this)
        loadUser()
    }

    private fun loadUser() =  onNext(UserDetailUiEvent.Init(args.userId))

    override fun accept(model: UserDetailViewModel) {
        model.userData?.let { userDetails ->
            swipe_container.isRefreshing = false
            loadImage(requireContext(), userDetails.avatarUrl, iv_user_avatar)
            tv_name.text = "${userDetails.firstName} ${userDetails.lastName}"
            tv_email.text = userDetails.email
        }
    }
}