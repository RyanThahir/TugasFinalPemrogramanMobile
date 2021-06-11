package com.ryanthahir.astroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.inflate

import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_quote.*
import kotlinx.android.synthetic.main.dialog_lay.*

class MainActivity : AppCompatActivity() {
    private lateinit var savedViewModel: SavedViewModel
    private lateinit var savedAdapter: SavedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedRV.layoutManager = LinearLayoutManager(this)
        savedAdapter = SavedAdapter(this) { saved, i ->
            showAlertMenu(saved)
        }
        savedRV.adapter = savedAdapter
        savedViewModel = ViewModelProvider(this).get(SavedViewModel::class.java)
        savedViewModel.getSaveds()?.observe(this, androidx.lifecycle.Observer{
            savedAdapter.setSaveds(it)
        })
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, CustomQuoteFragment::class.java)
            startActivity(intent)
        }
        getquote.setOnClickListener {
            val intent = Intent(this, QuoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlertMenu(saved: Saved) {
        val items = arrayOf("Edit", "Delete")
        val builder =
                AlertDialog.Builder(this)
        builder.setItems(items) { dialog, which ->
            when (which) {
                0 -> {
                    showAlertDialogEdit(saved)
                }
                1 -> {
                    savedViewModel.deleteSaved(saved)
                }
            }
        }
        builder.show()
    }
    private fun showAlertDialogEdit(saved: Saved) {
        val alert = AlertDialog.Builder(this)
        val editText = EditText(applicationContext)
        editText.setText(saved.saved)
        alert.setTitle("Edit Quote")
        alert.setView(editText)
        alert.setPositiveButton("Update") { dialog, _ ->
            saved.saved = editText.text.toString()
            savedViewModel.updateSaved(saved)
            dialog.dismiss()
        }
        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alert.show()
    }
}
