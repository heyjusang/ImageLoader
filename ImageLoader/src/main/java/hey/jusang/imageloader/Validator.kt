package hey.jusang.imageloader

import android.widget.ImageView
import java.util.*
import kotlin.collections.HashMap

class Validator {
    private val viewMap: HashMap<ImageView, String> = HashMap()
    private val urlMap: HashMap<String, HashSet<ImageView>> = HashMap()

    @Synchronized fun shouldFetch(url: String): Boolean {
        return !urlMap.containsKey(url)
    }

    @Synchronized fun set(url: String, imageView: ImageView) {
        val oldUrl = viewMap[imageView]

        if (urlMap.containsKey(oldUrl)) {
            urlMap[oldUrl]?.remove(imageView)

            if (urlMap[oldUrl]?.size == 0) {
                urlMap.remove(oldUrl)
            }
        }

        if (!urlMap.containsKey(url)) {
            urlMap[url] = HashSet()
        }
        urlMap[url]?.add(imageView)

        viewMap[imageView] = url
    }

    @Synchronized fun checkBinding(url: String): Boolean {
        return urlMap.containsKey(url)
    }

    @Synchronized fun getImageViewList(url: String): Set<ImageView>? {
        return urlMap[url]
    }

    @Synchronized fun clear(url: String) {
        urlMap[url]?.forEach{imageView ->
            viewMap.remove(imageView)
        }
        urlMap.remove(url)
    }
}