import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import sk.tokar.matus.gr.R

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