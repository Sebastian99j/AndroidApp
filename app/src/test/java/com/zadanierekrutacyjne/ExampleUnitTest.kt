package com.zadanierekrutacyjne

import com.zadanierekrutacyjne.Interfaces.Json
import com.zadanierekrutacyjne.OkHttp.OkHttpSend
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import java.lang.AssertionError

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun correctGetStatistic(){
        OkHttpSend.getStatistic()
    }

    @Test
    fun correctSendData(){
        val addeddEmail = "uzytkownik1@gmail.com"
        val addedPassword = "uzytkownik1"
        OkHttpSend.sendDataToLogin(addeddEmail, addedPassword)
    }

    @Test
    fun getJson(){
    }

    @Test
    fun parseJson(){
        val data1 = "Ulewa"
        val data2 = "Przymrozek"
        val json = JSONObject()
        json.put("Action", data1)
        json.put("Action", data2)
    }
}