package sk.tokar.matus.gr.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_user_details.*
import sk.tokar.matus.gr.App
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.blogic.details.UserDetails
import sk.tokar.matus.gr.common.Bindings
import sk.tokar.matus.gr.common.MviFragment
import sk.tokar.matus.gr.common.loadImage

sealed class UserDetailUiEvent {
    data class Init (val id: Int) : UserDetailUiEvent()
}

data class UserDetailViewModel (
    val userData: UserDetails? = null,
    val loading: Boolean = false
)

class UserDetailsFragment : MviFragment<UserDetailUiEvent, UserDetailViewModel>() {

    private val args: UserDetailsFragmentArgs by navArgs()

    override fun getLayoutResId(): Int = R.layout.fragment_user_details
    override fun getBindings(): Bindings<UserDetailUiEvent, UserDetailViewModel> = App.component.provideUserDetailBindings()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        swipe_container.setOnRefreshListener {
            loadUser()
        }
    }

    override fun afterInitAction() {
        loadUser()
    }

    private fun loadUser() =  onNext(UserDetailUiEvent.Init(args.userId))

    override fun accept(model: UserDetailViewModel) {
        model.userData?.let { userDetails ->
            swipe_container.isRefreshing = false
            loadImage(requireContext(), userDetails.avatarUrl, user_avatar)
            name.text = "${userDetails.firstName} ${userDetails.lastName}"
            email.text = userDetails.email
        }
    }

}