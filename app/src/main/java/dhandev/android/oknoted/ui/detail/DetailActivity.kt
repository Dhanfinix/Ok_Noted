package dhandev.android.oknoted.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import dhandev.android.oknoted.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    companion object{
        const val TIME_STAMP = "time_stamp"
        fun open(
            activity: ComponentActivity,
            noteStamp: Long? = null,
        ){
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(TIME_STAMP, noteStamp)
            activity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel.updateTimeStamp(intent.getLongExtra(TIME_STAMP, 0L))
        setupView()
        getData()
    }

    private fun getData() {
        detailViewModel.getNoteByTimestamp{
            binding.etTitle.setText(it?.title)
            binding.etNote.setText(it?.note)
        }
    }

    private fun setupView() {
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            etTitle.addTextChangedListener {
                detailViewModel.updateTitle(it.toString())
            }
            etNote.addTextChangedListener {
                detailViewModel.updateNote(it.toString())
            }
            btnSave.setOnClickListener {
                detailViewModel.apply {
                    if (isEditMode.value == true)
                        editNote {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    else addNote {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
            ivDelete.isVisible = detailViewModel.isEditMode.value == true
            ivDelete.setOnClickListener {
                detailViewModel.removeNote{
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }
}