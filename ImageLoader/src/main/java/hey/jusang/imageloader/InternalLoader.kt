package hey.jusang.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.core.os.HandlerCompat
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class InternalLoader constructor(
    context: Context
) {
    private val memoryCache: MemoryCache
    private val diskCache: DiskCache
    private val validator: Validator

    private val executorService: ExecutorService
    private val mainThreadHandler: Handler

    init {
        // TODO : proper memoryCacheSize, diskCacheSize, threadPoolSize

        val maxMemorySize = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        memoryCache = MemoryCache(maxMemorySize / 8)
        diskCache = DiskCache(context)
        validator = Validator()

        executorService = Executors.newFixedThreadPool(8)
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    }

    internal fun doLoad(requestBuilder: RequestBuilder, imageView: ImageView) {
        val url: String = requestBuilder.url!!

        if (setImageFromMemoryCache(url, imageView)) {
            return
        }

        setImageFromDiskCacheAndRemote(url, imageView)
    }

    private fun setImageFromMemoryCache(url: String, imageView: ImageView): Boolean {
        val bitmap: Bitmap = memoryCache[url] ?: return false

        Log.d("imagesource", "from memory cache [$url]")
        imageView.setImageBitmap(bitmap)
        return true
    }

    private fun setImageFromDiskCacheAndRemote(url:String, imageView: ImageView) {
        val shouldFetch: Boolean = validator.shouldFetch(url)

        validator.set(url, imageView)

        // TODO : thumbnail
        // imageView.setImageResource(R.drawable.thumbnail)

        if (shouldFetch) {
            executorService.execute(LoadBitmap(url))
        }
    }

    private inner class LoadBitmap constructor(
        private val url: String,
    ) : Runnable {
        private var targetWidth: Int = 200
        private var targetHeight: Int = 200
        // TODO : proper targetWidth, targetHeight

        override fun run() {
            if (!validator.checkBinding(url)) {
                return
            }

            var bitmap: Bitmap? = getBitmapFromDiskCache(url)
            if (displayBitmap(bitmap, false)) {
                return
            }

            bitmap = getBitmapFromRemote(url)
            displayBitmap(bitmap, true)
        }

        private fun getBitmapFromDiskCache(url: String): Bitmap? {
            val f: File = diskCache.getCacheFile(url)

            if (!f.exists()) {
                return null
            }

            return Util.decodeImageFile(f, targetWidth, targetHeight)
        }

        private fun getBitmapFromRemote(url: String): Bitmap? {
            try {
                val f: File = diskCache.getCacheFile(url)
                val imageUrl: URL = URL(url)
                val conn: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
                val inputStream: InputStream

                conn.doInput = true
                conn.connect()

                inputStream = conn.inputStream
                inputStream.use {
                    diskCache.write(f, it)
                }

                conn.disconnect()

                return Util.decodeImageFile(f, targetWidth, targetHeight)
            }
            catch (e: Exception) {
                return null
            }
        }

        private fun displayBitmap(bitmap: Bitmap?, isRemote: Boolean): Boolean {
            if (bitmap != null && validator.checkBinding(url)) {
                memoryCache.put(url, bitmap)
                mainThreadHandler.post (DisplayBitmap(url, bitmap, isRemote))
                return true
            }
            return false
        }
    }

    private inner class DisplayBitmap constructor(
        private val url: String,
        private val bitmap: Bitmap,
        private val isRemote: Boolean
    ) : Runnable {
        override fun run() {
            Log.d("imagesource", "from ${if (isRemote) "remote" else "disk cache"} [$url]")

            validator.getImageViewList(url)?.forEach { imageView ->
                imageView.setImageBitmap(bitmap)
                // TODO : validate imageView
            }

            validator.clear(url)
        }
    }
}