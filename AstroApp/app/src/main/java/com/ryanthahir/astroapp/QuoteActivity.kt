package com.ryanthahir.astroapp

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_quote.*



class QuoteActivity : AppCompatActivity(){
    private val TAG = "ApiProvider"
    private lateinit var savedViewModel: SavedViewModel
    private var textMessage = ""
    private var cont = ""
    private var author = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)
        textView.text="Please wait generating quote..."
        ApiProvider().callApi(object : ApiResult {
            override fun onError(e: Exception) {
                    Log.e(TAG, e.message.toString())
            }
            override fun onModel(baseModel: BaseModel) {
                if (baseModel is SampleGetModel) {
                        cont = baseModel.content.toString()
                        author = baseModel.author.toString()
                        textView.text = "Quote: " + cont + "\n-" + author
                        textMessage ="''" +cont+ "''\n -" +author
                }
            }
            override fun onJson(jsonObject: JsonObject) {
                Log.e(TAG, "Received a different model")
            }
            override fun onAPIFail() {
                Log.e(TAG, "Failed horribly")
            }
        })
        share.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textMessage)
                type = "text/plain"
            }
            val title = resources.getString(R.string.share)
            val chooser = Intent.createChooser(sendIntent, title)
            try {
                startActivity(chooser)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Cannot find app to share")
            }
        }
        saveBtn.setOnClickListener{
            showAlertDialogAdd()
        }
    }
    private fun showAlertDialogAdd() {
        savedViewModel = ViewModelProvider(this).get(SavedViewModel::class.java)
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Do you want to save this quote?")
        val user = Saved(0,author,cont)
        alert.setPositiveButton("Save") { dialog, _ ->
            savedViewModel.insertSaved(user)
            dialog.dismiss()
        }
        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alert.show()
    }
}
