package hey.jusang.imageloadersample.grid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hey.jusang.imageloadersample.databinding.ActivityGridBinding

class GridActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGridBinding
    private var adapter: GridAdapter = GridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        adapter.add(Sample.data)
    }
}