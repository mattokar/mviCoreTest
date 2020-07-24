package sk.tokar.matus.gr.ui

import android.os.Bundle
import android.view.View
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.api.models.user_details.UserDetails
import sk.tokar.matus.gr.common.MviFragment

sealed class UserDetailUiEvent {
    data class Init (val id: Int)
}

data class UserDetailViewModel (
    val userData: UserDetails? = null,
    val loading: Boolean = false
)

class UserDetailsFragment : MviFragment<UserDetailUiEvent, UserDetailViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_user_details

    override fun init(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun accept(t: UserDetailViewModel?) {
        TODO("Not yet implemented")
    }
}