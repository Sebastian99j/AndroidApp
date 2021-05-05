package com.zadanierekrutacyjne.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zadanierekrutacyjne.OkHttp.OkHttpSend
import com.zadanierekrutacyjne.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loginFunction(v: View) = runBlocking {
        sendDataToLogin(addEmailField.text.toString(), addPasswordField.text.toString())
    }

    fun registerFunction(view: View) {
        val intent = Intent(applicationContext, SecondActivity::class.java)
        startActivity(intent)
    }

//    private fun getAccess(): Int{
//        val accessing = OkHttpSend.sendDataToLogin(addEmailField.text.toString(), addPasswordField.text.toString())
//        return accessing
//    }

    private fun sendDataToLogin(addedEmail: String, addedPassword: String) {
        val jsonType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val client = OkHttpClient()
        val url = OkHttpSend.BASE_URL + "getuser"
        val js = JSONObject()

        js.put("email", addedEmail)
        js.put("password", addedPassword)

        val body: RequestBody = RequestBody.create(jsonType, js.toString())
        val request: Request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                Toast.makeText(applicationContext, "Błąd serwera!", Toast.LENGTH_SHORT).show()

                Log.e("MainActivity", "onFailure: code: " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                if (response.isSuccessful) {
                    val body = response.body!!.string()
                    saveData(body)
                    if (response.code == 200) {
                        val intent = Intent(applicationContext, DetailsActivity::class.java)
                        startActivity(intent)
                    } else if (response.code == 404) {
//                        toast.visibility = TextView.VISIBLE
//                        toast.text = "Nieprawidłowe dane!"
                        Toast.makeText(applicationContext, "Niepawidłowe dane!", Toast.LENGTH_SHORT).show()
                    }
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