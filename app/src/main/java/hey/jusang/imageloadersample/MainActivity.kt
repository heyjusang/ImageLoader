package hey.jusang.imageloadersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hey.jusang.imageloader.ImageLoader
import hey.jusang.imageloadersample.databinding.ActivityMainBinding

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
    }
}