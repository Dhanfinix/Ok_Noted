package dhandev.android.oknoted.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import dhandev.android.oknoted.R
import dhandev.android.oknoted.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private var enableBack = true

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
        setupObserver()
    }

    private fun setupObserver() {
        detailViewModel.enableSave.observe(this@DetailActivity){
            binding.btnSave.isEnabled = it
        }
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
        setSupportActionBar(binding.topAppBar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            topAppBar.setNavigationOnClickListener {
                enableBack = isChanged()
                onBackPressedDispatcher.onBackPressed()
            }
            topAppBar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_delete -> {
                        showRemoveNoteConfirmationDialog {
                            detailViewModel.removeNote {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        }
                        true
                    }
                    else -> true
                }
            }
            etTitle.addTextChangedListener {
                detailViewModel.updateTitle(it.toString())
                enableBack = isChanged()
            }
            etNote.addTextChangedListener {
                detailViewModel.updateNote(it.toString())
                enableBack = isChanged()
            }
            btnSave.setOnClickListener {
                enableBack = true
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
        }
        onBackPressedDispatcher.addCallback {
            if (enableBack)
                finish()
            else{
                showExitConfirmationDialog()
            }
        }
    }

    private fun isChanged() = if (detailViewModel.isEditMode.value == true){
        detailViewModel.originalNote?.title == detailViewModel.title.value &&
                detailViewModel.originalNote?.note == detailViewModel.note.value
    } else {
        detailViewModel.title.value == "" &&
                detailViewModel.note.value == ""
    }


    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Discard Changes?")
            .setMessage("Are you sure you want to discard your note changes?")
            .setPositiveButton("Yes") { dialog, _ ->
                enableBack = true
                dialog.dismiss()
                onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showRemoveNoteConfirmationDialog(
        doRemove: ()->Unit
    ){
        AlertDialog.Builder(this)
            .setTitle("Remove Note?")
            .setMessage("Do you really want to delete this note from your notes list?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                doRemove()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_top_app_bar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val deleteItem: MenuItem? = menu?.findItem(R.id.menu_delete)
        deleteItem?.isVisible = intent.getLongExtra(TIME_STAMP, 0L) != 0L
        return super.onPrepareOptionsMenu(menu)
    }
}