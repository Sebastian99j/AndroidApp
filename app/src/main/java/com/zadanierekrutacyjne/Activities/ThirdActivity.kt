package com.zadanierekrutacyjne.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.zadanierekrutacyjne.Interfaces.Json
import com.zadanierekrutacyjne.R
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.android.synthetic.main.fragment_table.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileOutputStream
import java.lang.Exception

class ThirdActivity : AppCompatActivity(), Json{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        var adapter = getAdapter()
        actionInput.adapter = adapter
    }

    private fun getAdapter(): ArrayAdapter<CharSequence>{
        var adapter = ArrayAdapter.createFromResource(this, R.array.action, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    fun addRowTable(view: View) {
        when {
            fragment.dane1.visibility == EditText.INVISIBLE -> {
                addRow(fragment)
                var adapter = getAdapter()
                fragment.dane2.adapter = adapter
            }
            fragment2.dane1.visibility == EditText.INVISIBLE -> {
                addRow(fragment2)
                var adapter = getAdapter()
                fragment2.dane2.adapter = adapter
            }
            fragment3.dane1.visibility == EditText.INVISIBLE -> {
                addRow(fragment3)
                var adapter = getAdapter()
                fragment3.dane2.adapter = adapter
            }
            fragment4.dane1.visibility == EditText.INVISIBLE -> {
                addRow(fragment4)
                var adapter = getAdapter()
                fragment4.dane2.adapter = adapter
            }
            fragment5.dane1.visibility == EditText.INVISIBLE -> {
                addRow(fragment5)
                var adapter = getAdapter()
                fragment5.dane2.adapter = adapter
            }
            fragment6.dane1.visibility == EditText.INVISIBLE -> {
                addRow(fragment6)
                var adapter = getAdapter()
                fragment6.dane2.adapter = adapter
            }
        }
    }

    override fun getJson(){
        val js = JSONObject()
        val jsonarray1 = JSONArray()
        val jsonarray2 = JSONArray()
        val jsonarray3 = JSONArray()
        val jsonarray4 = JSONArray()

        jsonarray1.put(dateInput.text.toString())
        jsonarray2.put(actionInput.selectedItem)
        jsonarray3.put(dataInput1.text.toString())
        jsonarray4.put(dataInput2.text.toString())

        if(fragment.dane1.visibility == EditText.VISIBLE){
            jsonarray1.put(fragment.dane1.text.toString())
            jsonarray2.put(fragment.dane2.selectedItem)
            jsonarray3.put(fragment.dane3.text.toString())
            jsonarray4.put(fragment.dane4.text.toString())
        }
        if(fragment2.dane1.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragment2.dane1.text.toString())
            jsonarray2.put(fragment2.dane2.selectedItem)
            jsonarray3.put(fragment2.dane3.text.toString())
            jsonarray4.put(fragment2.dane4.text.toString())
        }
        if(fragment3.dane1.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragment3.dane1.text.toString())
            jsonarray2.put(fragment3.dane2.selectedItem)
            jsonarray3.put(fragment3.dane3.text.toString())
            jsonarray4.put(fragment3.dane4.text.toString())
        }
        if (fragment4.dane1.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragment4.dane1.text.toString())
            jsonarray2.put(fragment4.dane2.selectedItem)
            jsonarray3.put(fragment4.dane3.text.toString())
            jsonarray4.put(fragment4.dane4.text.toString())
        }
        if(fragment5.dane1.visibility == EditText.VISIBLE){
            jsonarray1.put(fragment5.dane1.text.toString())
            jsonarray2.put(fragment5.dane2.selectedItem)
            jsonarray3.put(fragment5.dane3.text.toString())
            jsonarray4.put(fragment5.dane4.text.toString())
        }
        if(fragment6.dane1.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragment6.dane1.text.toString())
            jsonarray2.put(fragment6.dane2.selectedItem)
            jsonarray3.put(fragment6.dane3.text.toString())
            jsonarray4.put(fragment6.dane4.text.toString())
        }

        jsonDetails(js, jsonarray1, jsonarray2, jsonarray3, jsonarray4)

        saveJson(js)
    }

    override fun saveJson(js: JSONObject){
        var fp: FileOutputStream? = null
        try {
            fp = openFileOutput("data.json", MODE_PRIVATE)
            fp.write(js.toString().encodeToByteArray())
            fp.close()
        }
        catch (e: Exception){
            e.fillInStackTrace()
        }
    }

    private fun jsonDetails(js: JSONObject, dane1: JSONArray, dane2: JSONArray, dane3: JSONArray, dane4: JSONArray){
        js.put("date", dane1)
        js.put("action", dane2)
        js.put("data1", dane3)
        js.put("data2", dane4)
    }

    fun deleteRowTable(view: View) {
        when {
            fragment6.dane1.visibility == EditText.VISIBLE -> {
                deleteRow(fragment6)
            }
            fragment5.dane1.visibility == EditText.VISIBLE -> {
                deleteRow(fragment5)
            }
            fragment4.dane1.visibility == EditText.VISIBLE -> {
                deleteRow(fragment4)
            }
            fragment3.dane1.visibility == EditText.VISIBLE -> {
                deleteRow(fragment3)
            }
            fragment2.dane1.visibility == EditText.VISIBLE -> {
                deleteRow(fragment2)
            }
            fragment.dane1.visibility == EditText.VISIBLE -> {
                deleteRow(fragment)
            }
        }
    }

    private fun deleteRow(fragmentcurrent: Fragment){
        fragmentcurrent.dane1.visibility = EditText.INVISIBLE
        fragmentcurrent.dane2.visibility = EditText.INVISIBLE
        fragmentcurrent.dane3.visibility = EditText.INVISIBLE
        fragmentcurrent.dane4.visibility = EditText.INVISIBLE
    }

    private fun addRow(fragmentcurrent: Fragment){
        fragmentcurrent.dane1.visibility = EditText.VISIBLE
        fragmentcurrent.dane2.visibility = EditText.VISIBLE
        fragmentcurrent.dane3.visibility = EditText.VISIBLE
        fragmentcurrent.dane4.visibility = EditText.VISIBLE
    }

    fun nextPage(view: View) {
        getJson()
        val intent = Intent(applicationContext, FourthActivity::class.java)
        startActivity(intent)
    }

    fun backPage(view: View) {
        var intent = Intent(applicationContext, DetailsActivity::class.java)
        startActivity(intent)
    }
}