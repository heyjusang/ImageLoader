package hey.jusang.imageloader

import android.content.Context
import android.widget.ImageView

class RequestBuilder constructor(
    private val context: Context
) {
    internal var url: String? = null

    fun load(url: String): RequestBuilder {
        this.url = url
        return this
    }

    fun into(imageView: ImageView) {
        ImageLoader.getInstance(context).doLoad(this, imageView)
    }
}