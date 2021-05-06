package com.zadanierekrutacyjne.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zadanierekrutacyjne.OkHttp.OkHttpSend
import com.zadanierekrutacyjne.R
import kotlinx.android.synthetic.main.activity_details.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val json = JSONObject(loadDataUser())
        val email = json.get("email")

        userEmailDetail.text = email.toString()
        getStatistic()
    }

    fun addData(view: View) {
        val intent = Intent(applicationContext, ThirdActivity::class.java)
        startActivity(intent)
    }

    private fun getStatistic(){
        val json = JSONObject(loadDataUser())
        val id = json.get("id")

        val client = OkHttpClient()
        val url = OkHttpSend.BASE_URL +"statistic"
        val request = Request.Builder()
                .url(url)
                .header("id-user", id.toString())
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "onFailure: code: " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body!!.string()
                    parseJson(body)
                }
            }
        })
    }

    private fun loadDataUser(): String {
        val fis: FileInputStream
        var jsonFromFile: String = ""

        try {
            fis = openFileInput("userData.json")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            jsonFromFile = br.readText()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return jsonFromFile
    }

    private fun parseJson(body: String){
        val json = JSONArray(body)

        //tabela nr 1
        try{
            val text1 = json.get(0).toString()
            val js1 = JSONArray(text1)
            val jso1 = JSONObject(js1.get(0).toString())
            table1data1.text = jso1.get("date").toString()
            table1data2.text = jso1.get("action").toString()
            table1data3.text = jso1.get("data1").toString()
            table1data4.text = jso1.get("data2").toString()
        }
        catch (e: Exception){
            print("Brak danych")
        }

        //tabela nr 2
        try{
            val text2 = json.get(1).toString()
            val js2 = JSONArray(text2)
            val jso2 = JSONObject(js2.get(0).toString())
            table2data1.text = jso2.get("plant").toString()
            table2data2.text = jso2.get("term").toString()
            table2data3.text = jso2.get("condition").toString()
        }
        catch (e: Exception){
            println("Brak danych")
        }

        //tabela nr 3
        try{
            val text3 = json.get(2).toString()
            val js3 = JSONArray(text3)
            val jso3 = JSONObject(js3.get(0).toString())
            table3data1.text = jso3.get("datatime").toString()
            table3data2.text = jso3.get("incident").toString()
            table3data3.text = jso3.get("loss").toString()
        }
        catch (e: Exception){
            print("Brak danych")
        }
    }

    fun toMap(view: View) {
        val intent = Intent(applicationContext, MapActivity2::class.java)
        startActivity(intent)
    }
}