package sk.tokar.matus.gr.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item_row.view.*
import sk.tokar.matus.gr.common.loadImage
import sk.tokar.matus.gr.R
import sk.tokar.matus.gr.blogic.list.User
import sk.tokar.matus.gr.common.setDataWithDiff

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

    fun update(newUsers: List<User>){
        setDataWithDiff(newUsers, users)
        this.users = newUsers
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

