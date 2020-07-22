package sk.tokar.matus.gr.api.models.user_details

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("ad")
    val ad: Ad,
    @SerializedName("data")
    val data: Data
)

fun UserDetail.convert(): UserDetails = UserDetails(
        data.id,
        data.email,
        data.firstName,
        data.lastName,
        data.avatar
    )


data class UserDetails(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String
)