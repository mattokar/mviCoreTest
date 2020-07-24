package sk.tokar.matus.gr.common

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observable
import sk.tokar.matus.gr.R

fun <T> T.toObservable(): Observable<T> = Observable.just(this)

fun loadImage(context: Context, url: String, view: ImageView) {
    Glide.with(context)
        .load(url)
        .thumbnail(
            Glide.with(context).load(R.drawable.placeholder)
                .apply(RequestOptions().circleCrop())
        )
        .apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .circleCrop()
        .into(view)
}

fun RecyclerView.Adapter<*>.setDataWithDiff(newList: List<Any>, oldList: List<Any>) {
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    }).dispatchUpdatesTo(this)
}

class RecyclerViewLazyListener(private val nextPageListener: (Int) -> Unit, private val visibleThreshold: Int = 2, private val sizeCheck: (RecyclerView) -> Int = { it.layoutManager?.itemCount ?: 0 }) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0
    private var loading = true
    private var currentPage = 1
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val layoutManager = recyclerView.layoutManager
        val totalItemCount = sizeCheck(recyclerView)
        val firstVisibleItem = when (layoutManager) {
            is androidx.recyclerview.widget.LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
            is androidx.recyclerview.widget.GridLayoutManager -> layoutManager.findFirstVisibleItemPosition()
            else -> 0
        }
        if (loading) {
            if (totalItemCount != previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            currentPage++
            nextPageListener.invoke(currentPage)
            loading = true
        }
    }
    fun reset() {
        previousTotal = 0
        loading = true
        currentPage = 1
    }
}