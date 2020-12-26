package hey.jusang.imageloadersample.basic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hey.jusang.imageloader.ImageLoader
import hey.jusang.imageloadersample.R
import hey.jusang.imageloadersample.databinding.ActivityMainBinding
import hey.jusang.imageloadersample.grid.GridActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImageLoader.with(this)
            .load("https://picsum.photos/id/237/200/200")
            .thumbnail(R.drawable.thumbnail)
            .into(binding.imageView)

        binding.button.setOnClickListener {
            startActivity(Intent(this, GridActivity::class.java))
        }
    }
}