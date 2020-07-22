package sk.tokar.matus.gr.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.user_item_row.view.*
import loadImage
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.blogic.list.User

class UserListAdapter(private val action: (user: User) -> Unit) : RecyclerView.Adapter<UserViewHolder>() {

    private var users = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder = UserViewHolder(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        users[position].let {user ->
            holder.bind(user)
            holder.itemView.setOnClickListener { action.invoke(user) }
        }
    }

    override fun getItemCount(): Int = users.size

    fun update(users: List<User>){
        this.users = users
        notifyDataSetChanged()
    }
}

class UserViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.user_item_row, parent, false)
) {
    @SuppressLint("SetTextI18n")
    fun bind(user: User) {
        itemView.tv_user_name.text = "${user.firstName} ${user.lastName}"
        itemView.tv_user_email.text = user.email
        loadImage(itemView.context, user.avatarUrl, itemView.iv_user_avatar)
    }
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