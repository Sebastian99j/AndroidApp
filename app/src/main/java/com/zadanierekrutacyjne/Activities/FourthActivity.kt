package com.zadanierekrutacyjne.Activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.zadanierekrutacyjne.Interfaces.Json
import com.zadanierekrutacyjne.OkHttp.OkHttpSend
import com.zadanierekrutacyjne.R
import com.zadanierekrutacyjne.Retrofit.RetrofitSend
import kotlinx.android.synthetic.main.activity_fourth.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class FourthActivity : AppCompatActivity(), Json {

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        getAdapter()
    }

    private fun getAdapter(){
        var adapter = ArrayAdapter.createFromResource(this, R.array.plant, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        plantInput.adapter = adapter

        var adapter2 = ArrayAdapter.createFromResource(this, R.array.condition, android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        conditionInput.adapter = adapter2

    }

    fun nextPage(view: View) {
        getJson()
        val intent = Intent(applicationContext, FifthActivity::class.java)
        startActivity(intent)
    }

    fun backPage(view: View) {
        val intent = Intent(applicationContext, ThirdActivity::class.java)
        startActivity(intent)
    }

    fun addPhoto(view: View) {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, 10)

//        val intent: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> {
                if (resultCode == RESULT_OK) {
                    val uri = data!!.data
                    val name = getName(uri!!)
                    try {
//                        var input = contentResolver.openInputStream(uri)
//                        bitmap = BitmapFactory.decodeStream(input)
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        OkHttpSend.uploadImage(bitmap, this)

//                        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name)
//                        OkHttpSend.sendFile(file)
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

    override fun getJson(){
        var js = JSONObject()
        jsonDetails(js, plantInput.selectedItem, termInput.text.toString(), conditionInput.selectedItem)
        saveJson(js)
    }

    override fun saveJson(js: JSONObject){
        var fp: FileOutputStream? = null
        try {
            fp = openFileOutput("data2.json", MODE_PRIVATE)
            fp.write(js.toString().encodeToByteArray())
            fp.close()
        }
        catch (e: Exception){
            e.fillInStackTrace()
        }
    }

    private fun jsonDetails(js: JSONObject, dane1: Any, dane2: String, dane3: Any){
        js.put("plant", dane1)
        js.put("term", dane2)
        js.put("condition", dane3)
    }
}