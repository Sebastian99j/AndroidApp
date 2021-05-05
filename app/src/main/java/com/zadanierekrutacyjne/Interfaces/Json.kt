package com.zadanierekrutacyjne.Interfaces

import org.json.JSONObject

interface Json {

    fun getJson(){}
    fun saveJson(js: JSONObject)
}