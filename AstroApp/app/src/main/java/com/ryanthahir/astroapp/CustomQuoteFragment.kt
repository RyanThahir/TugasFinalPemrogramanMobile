package com.ryanthahir.astroapp

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import kotlinx.android.synthetic.main.dialog_lay.*



class CustomQuoteFragment : AppCompatActivity(){
    private lateinit var savedViewModel: SavedViewModel
    var cont = ""
    var author = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_lay)
        btnSave.setOnClickListener{
            insertToDatabase()
        }
        btnCancel.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnShare.setOnClickListener {
            cont = daquote.text.toString()
            author = daauthor.text.toString()
            if(inputCheck(cont,author)) {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "''" +cont+ "''\n -" +author)
                    type = "text/plain"
                }
                val title = resources.getString(R.string.share)
                val chooser = Intent.createChooser(sendIntent, title)
                try {
                    startActivity(chooser)
                } catch (e: ActivityNotFoundException) {
                    Log.e(TAG, "Cannot find app to share")
                }
            }else{
                Toast.makeText(this, "Quote dan author tidak boleh kosong!!!", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun insertToDatabase(){
        savedViewModel = ViewModelProvider(this).get(SavedViewModel::class.java)
        cont = daquote.text.toString()
        author = daauthor.text.toString()
        if(inputCheck(cont,author)){
            val user = Saved(0, author, cont)
            savedViewModel.insertSaved(user)
            Toast.makeText(this, "Quote berhasil ditambahkan", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Quote dan author tidak boleh kosong!!!", Toast.LENGTH_LONG).show()
        }
    }
    private fun inputCheck(cont: String, author: String): Boolean{
        return !(TextUtils.isEmpty(cont) && TextUtils.isEmpty(author))
    }
}