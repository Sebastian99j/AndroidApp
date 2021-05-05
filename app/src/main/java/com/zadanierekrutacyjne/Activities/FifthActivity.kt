package com.zadanierekrutacyjne.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.view.View
import com.zadanierekrutacyjne.OkHttp.OkHttpSend
import com.zadanierekrutacyjne.R
import java.io.File

class FifthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)
    }

    fun nextPage(view: View) {
        val intent = Intent(applicationContext, SixthActivity::class.java)
        startActivity(intent)
    }
    fun backPage(view: View) {
        val intent = Intent(applicationContext, FourthActivity::class.java)
        startActivity(intent)
    }

    fun addFile(view: View) {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        startActivityForResult(intent, 1)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == RESULT_OK) {
                    val uri = data!!.data
                    val name = getName(uri!!)
                    try {
                        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), name)
                        OkHttpSend.sendFile(file)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getName(uri: Uri): String{
        var name = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }
}