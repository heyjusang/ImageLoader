package hey.jusang.imageloader

import android.content.Context

class ImageLoader {
    companion object {
        @Volatile private var INSTANCE: InternalLoader? = null

        fun getInstance(context: Context): InternalLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildInternalLoader(context).also { INSTANCE = it }
            }
        }

        private fun buildInternalLoader(context: Context): InternalLoader {
            return InternalLoader(context)
        }
    }
}