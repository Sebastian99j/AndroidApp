package com.zadanierekrutacyjne

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zadanierekrutacyjne.OkHttp.OkHttpSend

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.zadanierekrutacyjne", appContext.packageName)
    }

    @Test
    fun testing(){
        OkHttpSend.sendDataToLogin("uzytkownik1@gmail.com", "uzytkownik1")
    }

    @Test
    fun testing2(){
        OkHttpSend.sendDataToLogin("error", "bad")
    }
}