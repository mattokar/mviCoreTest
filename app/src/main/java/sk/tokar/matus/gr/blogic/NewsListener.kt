package sk.tokar.matus.gr.blogic


import io.reactivex.functions.Consumer
import sk.tokar.matus.gr.blogic.list.UsersPresenter.News
import sk.tokar.matus.gr.common.AppViews
import sk.tokar.matus.gr.common.MainNavigator
import javax.inject.Inject

class NewsListener @Inject constructor(
    private val mainNavigator: MainNavigator
) : Consumer<NewsListener.CommonNews> {

    sealed class CommonNews {
        data class OpenDetail(val id: Int) : CommonNews()
        data class Error(val throwable: Throwable) : CommonNews()
        object OpenList: CommonNews()
    }

    override fun accept(news: CommonNews) {
        when (news) {
            is CommonNews.OpenDetail -> mainNavigator.navigate(AppViews.UserDetail(news.id))
        }
    }
}
