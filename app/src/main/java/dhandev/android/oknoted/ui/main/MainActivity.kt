package dhandev.android.oknoted.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dhandev.android.oknoted.R
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.databinding.ActivityMainBinding
import dhandev.android.oknoted.ui.detail.DetailActivity
import dhandev.android.oknoted.ui.main.note_rv.MarginFirstLastItemDecoration
import dhandev.android.oknoted.ui.main.note_rv.NoteItemAdapter
import dhandev.android.oknoted.ui.main.note_rv.NoteItemDelegate

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteItemAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserver()
        setupView()
    }

    private fun setupView() {
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            adapter = NoteItemAdapter()
            rvContent.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter.delegate = object : NoteItemDelegate {
                override fun onClick(value: NoteItemData) {
                    DetailActivity.open(this@MainActivity, value.timeStamp)
                }
            }
            rvContent.adapter = adapter
            rvContent.addItemDecoration(
                MarginFirstLastItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.top_margin),
                    resources.getDimensionPixelSize(R.dimen.bottom_margin)
                )
            )

            fab.setOnClickListener {
                DetailActivity.open(this@MainActivity)
            }
        }
    }

    private fun setupObserver() {
        viewModel.notes.observe(this) { notes ->
            showContent(notes.isNotEmpty())
            adapter.updateNotes(notes)
        }
    }

    private fun showContent(visible: Boolean) {
        binding.apply {
            if (visible) {
                rvContent.visibility = View.VISIBLE
                emptyState.visibility = View.GONE
            } else {
                rvContent.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            }
        }
    }
}