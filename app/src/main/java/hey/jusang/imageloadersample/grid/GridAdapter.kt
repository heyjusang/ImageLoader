package hey.jusang.imageloadersample.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import hey.jusang.imageloader.ImageLoader
import hey.jusang.imageloadersample.R
import hey.jusang.imageloadersample.databinding.ItemGridBinding

class GridAdapter : Adapter<GridAdapter.GridViewHolder>() {
    private var imageUrlList: ArrayList<String> = ArrayList()

    fun add(urlList: List<String>) {
        imageUrlList.addAll(urlList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding: ItemGridBinding =
            ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val url: String = imageUrlList[position]
        holder.bind(url)
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    class GridViewHolder(private val binding: ItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            ImageLoader.with(binding.root.context)
                .load(url)
                .thumbnail(R.drawable.thumbnail)
                .into(binding.image)
        }
    }
}