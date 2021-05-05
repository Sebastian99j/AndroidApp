package com.zadanierekrutacyjne.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zadanierekrutacyjne.OkHttp.OkHttpSend
import com.zadanierekrutacyjne.R
import kotlinx.android.synthetic.main.activity_main.addEmailField
import kotlinx.android.synthetic.main.activity_main.addPasswordField
import kotlinx.android.synthetic.main.activity_second.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.FileOutputStream
import java.io.IOException

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun login(view: View) {
        if (checkBoxRegulations.isChecked){
            registerUser(addEmailField.text.toString(), addPasswordField.text.toString())
        }
        else {
            Toast.makeText(applicationContext, "Należy potwierdzić regulamin!", Toast.LENGTH_SHORT).show()
        }
    }

    fun exit(view: View) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    private fun registerUser(addedEmail: String?, addedPassword: String?) {
        val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
        val client = OkHttpClient()

        val url = OkHttpSend.BASE_URL + "adduser"
        val js = JSONObject()
        js.put("email", addedEmail)
        js.put("password", addedPassword)
        val body: RequestBody = RequestBody.create(JSON, js.toString())
        val request: Request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "onFailure: code: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {

                if (response.isSuccessful) {
                    val body = response.body!!.string()
                    saveData(body)

                    val intent = Intent(applicationContext, DetailsActivity::class.java)
                    startActivity(intent)
                }
                Log.e("MainActivity", "onResponse: " + response.code)
            }
        })
    }

    private fun saveData(data: String){
        var fp: FileOutputStream? = null
        try {
            fp = openFileOutput("userData.json", MODE_PRIVATE)
            fp.write(data.encodeToByteArray())
            fp.close()
        }
        catch (e: Exception){
            e.fillInStackTrace()
        }
    }
}