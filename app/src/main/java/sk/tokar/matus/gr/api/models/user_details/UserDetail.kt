package sk.tokar.matus.gr.api.models.user_details

import com.google.gson.annotations.SerializedName
import sk.tokar.matus.gr.blogic.details.UserDetails

data class UserDetail(
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