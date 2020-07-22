package sk.tokar.matus.gr.common

import androidx.navigation.NavController
import androidx.navigation.findNavController
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.ui.UsersFragmentDirections
import javax.inject.Inject

sealed class AppViews {
    object UsersList : AppViews()
    data class UserDetail(val userId: Int) : AppViews()
}

interface Navigator<T> {
    fun navigate(screen: T)
}

interface MainNavigator : Navigator<AppViews>

class MainNavigatorImpl @Inject constructor(
    private val appActivityManager: AppActivityManager
) : MainNavigator {

    override fun navigate(screen: AppViews) {
        when (screen) {
            is AppViews.UserDetail -> getNavController()?.navigate(UsersFragmentDirections.actionUsersToDetail(screen.userId))
            AppViews.UsersList -> getNavController()?.navigate(R.id.action_detail_to_users)
        }
    }

    private fun getNavController(): NavController? = appActivityManager.getCurrentActivity()?.findNavController(R.id.nav_container)
}