package hu.bme.aut.android.szelvenykeszito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.szelvenykeszito.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }
}