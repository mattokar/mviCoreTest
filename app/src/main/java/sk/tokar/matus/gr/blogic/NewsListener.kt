package sk.tokar.matus.gr.blogic


import io.reactivex.functions.Consumer
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.common.AppActivityManager
import sk.tokar.matus.gr.common.AppViews
import sk.tokar.matus.gr.common.MainNavigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsListener @Inject constructor(
    private val mainNavigator: MainNavigator,
    private val appActivityManager: AppActivityManager
) : Consumer<NewsListener.CommonNews> {

    sealed class CommonNews {
        data class OpenDetail(val id: Int) : CommonNews()
        data class Error(val throwable: Throwable) : CommonNews()
        object OpenList: CommonNews()
    }

    override fun accept(news: CommonNews) {
        when (news) {
            is CommonNews.OpenDetail -> mainNavigator.navigate(AppViews.UserDetail(news.id))
            CommonNews.OpenList -> mainNavigator.navigate(AppViews.UsersList)
            is CommonNews.Error -> appActivityManager.getCurrentActivity()?.showMessage(R.string.general_error)
        }
    }
}
