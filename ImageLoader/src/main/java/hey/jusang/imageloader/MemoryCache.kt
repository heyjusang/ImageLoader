package hey.jusang.imageloader

import android.graphics.Bitmap
import androidx.collection.LruCache


class MemoryCache constructor(
    maxSize: Int
): LruCache<String, Bitmap>(maxSize) {

    override fun sizeOf(key: String, value: Bitmap): Int {
        return value.byteCount / 1024
    }
}