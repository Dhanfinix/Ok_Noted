package dhandev.android.oknoted

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = NoteItemAdapter(listOf(
            NoteItemData("Test Title", "Test note content"),
            NoteItemData("Test Title", "Test note content"),
            NoteItemData("Test Title", "Test note content"),
            NoteItemData("Test Title", "Test note content"),
            NoteItemData("Test Title", "Test note content"),
        ))
        binding.apply {
            rvContent.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter.delegate = object : NoteItemDelegate{
                override fun onClick(value: NoteItemData) {
                    Toast.makeText(this@MainActivity, value.title, Toast.LENGTH_SHORT).show()
                }
            }
            rvContent.adapter = adapter
        }
    }
}