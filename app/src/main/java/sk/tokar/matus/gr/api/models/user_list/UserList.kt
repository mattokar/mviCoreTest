package sk.tokar.matus.gr.api.models.user_list

import com.google.gson.annotations.SerializedName
import sk.tokar.matus.gr.blogic.list.User

data class UserList(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)

fun UserList.convert(): List<User> = this.data.map {
    User(
        it.firstName,
        it.lastName,
        it.avatar,
        it.email,
        it.id
    )
}
