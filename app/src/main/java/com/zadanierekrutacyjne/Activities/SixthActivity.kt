package com.zadanierekrutacyjne.Activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zadanierekrutacyjne.Interfaces.Json
import com.zadanierekrutacyjne.OkHttp.OkHttpSend
import com.zadanierekrutacyjne.R
import kotlinx.android.synthetic.main.activity_sixth.*
import kotlinx.android.synthetic.main.fragment_table2.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class SixthActivity : AppCompatActivity(), Json {

    private var bitmap: Bitmap? = null

    var jsonFromFile: String? = null
    var jsonFromFile2: String? = null
    var jsonFromFile3: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth)

        val adapter = getAdapter()
        fragmentTable1.actionInputTable.adapter = adapter

        start()
    }

    private fun getAdapter(): ArrayAdapter<CharSequence> {
        var adapter = ArrayAdapter.createFromResource(this, R.array.bioaction, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    private fun start() {
        fragmentTable1.dataInputTable.visibility = TextView.VISIBLE
        fragmentTable1.actionInputTable.visibility = Spinner.VISIBLE
        fragmentTable1.lossInputTable.visibility = TextView.VISIBLE
        fragmentTable1.addPhotoButton.visibility = Button.VISIBLE
    }

    fun addPhotoToTable(view: View) {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, 10)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> {
                if (resultCode == RESULT_OK) {
                    val uri = data!!.data
                    try {
//                        var input = contentResolver.openInputStream(uri)
//                        bitmap = BitmapFactory.decodeStream(input)
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        OkHttpSend.uploadImage(bitmap, this)

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun sendData(view: View) {
        getJson()
        load()

        val json = JSONObject(loadDataUser())
        val id = json.get("id")
        val token = json.get("access_token")

        OkHttpSend.sendJson(jsonFromFile, "getjson/1", id.toString(), token.toString())
        OkHttpSend.sendJson(jsonFromFile2, "getjson/2", id.toString(), token.toString())
        OkHttpSend.sendJson(jsonFromFile3, "getjson/3", id.toString(), token.toString())

        Toast.makeText(applicationContext, "Wysłano", Toast.LENGTH_SHORT).show()
    }

    fun backPage(view: View) {
        val intent = Intent(applicationContext, FifthActivity::class.java)
        startActivity(intent)
    }

    private fun load() {
        val fis: FileInputStream
        val fis2: FileInputStream
        val fis3: FileInputStream
        try {
            fis = openFileInput("data.json")
            fis2 = openFileInput("data2.json")
            fis3 = openFileInput("data3.json")

            val isr = InputStreamReader(fis)
            val isr2 = InputStreamReader(fis2)
            val isr3 = InputStreamReader(fis3)

            val br = BufferedReader(isr)
            val br2 = BufferedReader(isr2)
            val br3 = BufferedReader(isr3)

            jsonFromFile = br.readText()
            jsonFromFile2 = br2.readText()
            jsonFromFile3 = br3.readText()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
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

    private fun deleteRow(fragmentcurrent: Fragment) {
        fragmentcurrent.dataInputTable.visibility = TextView.INVISIBLE
        fragmentcurrent.actionInputTable.visibility = Spinner.INVISIBLE
        fragmentcurrent.lossInputTable.visibility = TextView.INVISIBLE
        fragmentcurrent.addPhotoButton.visibility = Button.INVISIBLE
    }

    private fun addRow(fragmentcurrent: Fragment) {
        fragmentcurrent.dataInputTable.visibility = TextView.VISIBLE
        fragmentcurrent.actionInputTable.visibility = Spinner.VISIBLE
        fragmentcurrent.lossInputTable.visibility = TextView.VISIBLE
        fragmentcurrent.addPhotoButton.visibility = Button.VISIBLE
    }

    fun addRowTable2(view: View) {
        when {
            fragmentTable2.dataInputTable.visibility == EditText.INVISIBLE -> {
                addRow(fragmentTable2)
                var adapter = getAdapter()
                fragmentTable2.actionInputTable.adapter = adapter
            }
            fragmentTable3.dataInputTable.visibility == EditText.INVISIBLE -> {
                addRow(fragmentTable3)
                var adapter = getAdapter()
                fragmentTable3.actionInputTable.adapter = adapter
            }
            fragmentTable4.dataInputTable.visibility == EditText.INVISIBLE -> {
                addRow(fragmentTable4)
                var adapter = getAdapter()
                fragmentTable4.actionInputTable.adapter = adapter
            }
            fragmentTable5.dataInputTable.visibility == EditText.INVISIBLE -> {
                addRow(fragmentTable5)
                var adapter = getAdapter()
                fragmentTable5.actionInputTable.adapter = adapter
            }
            fragmentTable6.dataInputTable.visibility == EditText.INVISIBLE -> {
                addRow(fragmentTable6)
                var adapter = getAdapter()
                fragmentTable6.actionInputTable.adapter = adapter
            }
            fragmentTable7.dataInputTable.visibility == EditText.INVISIBLE -> {
                addRow(fragmentTable7)
                var adapter = getAdapter()
                fragmentTable7.actionInputTable.adapter = adapter
            }
        }
    }

    fun deleteRowTable2(view: View) {
        when {
            fragmentTable7.dataInputTable.visibility == EditText.VISIBLE -> {
                deleteRow(fragmentTable7)
            }
            fragmentTable6.dataInputTable.visibility == EditText.VISIBLE -> {
                deleteRow(fragmentTable6)
            }
            fragmentTable5.dataInputTable.visibility == EditText.VISIBLE -> {
                deleteRow(fragmentTable5)
            }
            fragmentTable4.dataInputTable.visibility == EditText.VISIBLE -> {
                deleteRow(fragmentTable4)
            }
            fragmentTable3.dataInputTable.visibility == EditText.VISIBLE -> {
                deleteRow(fragmentTable3)
            }
            fragmentTable2.dataInputTable.visibility == EditText.VISIBLE -> {
                deleteRow(fragmentTable2)
            }
        }
    }

    override fun getJson() {
        val js = JSONObject()
        val jsonarray1 = JSONArray()
        val jsonarray2 = JSONArray()
        val jsonarray3 = JSONArray()

        if (fragmentTable1.dataInputTable.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragmentTable1.dataInputTable.text.toString())
            jsonarray2.put(fragmentTable1.actionInputTable.selectedItem)
            jsonarray3.put(fragmentTable1.lossInputTable.text.toString())
        }
        if (fragmentTable2.dataInputTable.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragmentTable2.dataInputTable.text.toString())
            jsonarray2.put(fragmentTable2.actionInputTable.selectedItem)
            jsonarray3.put(fragmentTable2.lossInputTable.text.toString())
        }
        if (fragmentTable3.dataInputTable.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragmentTable3.dataInputTable.text.toString())
            jsonarray2.put(fragmentTable3.actionInputTable.selectedItem)
            jsonarray3.put(fragmentTable3.lossInputTable.text.toString())
        }
        if (fragmentTable4.dataInputTable.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragmentTable4.dataInputTable.text.toString())
            jsonarray2.put(fragmentTable4.actionInputTable.selectedItem)
            jsonarray3.put(fragmentTable4.lossInputTable.text.toString())
        }
        if (fragmentTable5.dataInputTable.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragmentTable5.dataInputTable.text.toString())
            jsonarray2.put(fragmentTable5.actionInputTable.selectedItem)
            jsonarray3.put(fragmentTable5.lossInputTable.text.toString())
        }
        if (fragmentTable6.dataInputTable.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragmentTable6.dataInputTable.text.toString())
            jsonarray2.put(fragmentTable6.actionInputTable.selectedItem)
            jsonarray3.put(fragmentTable6.lossInputTable.text.toString())
        }
        if (fragmentTable7.dataInputTable.visibility == EditText.VISIBLE) {
            jsonarray1.put(fragmentTable7.dataInputTable.text.toString())
            jsonarray2.put(fragmentTable7.actionInputTable.selectedItem)
            jsonarray3.put(fragmentTable7.lossInputTable.text.toString())
        }

        jsonDetails(js, jsonarray1, jsonarray2, jsonarray3)

        saveJson(js)
    }

    override fun saveJson(js: JSONObject) {
        var fp: FileOutputStream? = null
        try {
            fp = openFileOutput("data3.json", MODE_PRIVATE)
            fp.write(js.toString().encodeToByteArray())
            fp.close()
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
    }

    private fun jsonDetails(js: JSONObject, dane1: JSONArray, dane2: JSONArray, dane3: JSONArray) {
        js.put("datatime", dane1)
        js.put("incident", dane2)
        js.put("loss", dane3)
    }
}