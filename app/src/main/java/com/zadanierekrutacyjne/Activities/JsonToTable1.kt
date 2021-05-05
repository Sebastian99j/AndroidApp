package com.zadanierekrutacyjne.Activities

import android.widget.EditText
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_table.*
import org.json.JSONObject

class JsonToTable1 {
    companion object{
        fun getJson(fragment: FragmentTable, fragment2: FragmentTable, fragment3: FragmentTable, fragment4: FragmentTable,
                    fragment5: FragmentTable, fragment6: FragmentTable, dateInput: EditText, actionInput: Spinner,
                    dataInput1: EditText, dataInput2: EditText): JSONObject{
            var js = JSONObject()
            var list1 = ArrayList<String>()
            var list2 = ArrayList<Any>()
            var list3 = ArrayList<String>()
            var list4 = ArrayList<String>()

            list1.add(dateInput.text.toString())
            list2.add(actionInput.selectedItem)
            list3.add(dataInput1.text.toString())
            list4.add(dataInput2.text.toString())
            jsonDetails(js, list1, list2, list3, list4)

            if(fragment.dane1.visibility == EditText.VISIBLE){
                list1.add(fragment.dane1.text.toString())
                list2.add(fragment.dane2.selectedItem)
                list3.add(fragment.dane3.text.toString())
                list4.add(fragment.dane4.text.toString())
                jsonDetails(js, list1, list2, list3, list4)
            }
            if(fragment2.dane1.visibility == EditText.VISIBLE) {
                list1.add(fragment2.dane1.text.toString())
                list2.add(fragment2.dane2.selectedItem)
                list3.add(fragment2.dane3.text.toString())
                list4.add(fragment2.dane4.text.toString())
                jsonDetails(js, list1, list2, list3, list4)
            }
            if(fragment3.dane1.visibility == EditText.VISIBLE) {
                list1.add(fragment3.dane1.text.toString())
                list2.add(fragment3.dane2.selectedItem)
                list3.add(fragment3.dane3.text.toString())
                list4.add(fragment3.dane4.text.toString())
                jsonDetails(js, list1, list2, list3, list4)
            }
            if (fragment4.dane1.visibility == EditText.VISIBLE) {
                list1.add(fragment4.dane1.text.toString())
                list2.add(fragment4.dane2.selectedItem)
                list3.add(fragment4.dane3.text.toString())
                list4.add(fragment4.dane4.text.toString())
                jsonDetails(js, list1, list2, list3, list4)
            }
            if(fragment5.dane1.visibility == EditText.VISIBLE){
                list1.add(fragment5.dane1.text.toString())
                list2.add(fragment5.dane2.selectedItem)
                list3.add(fragment5.dane3.text.toString())
                list4.add(fragment5.dane4.text.toString())
                jsonDetails(js, list1, list2, list3, list4)
            }
            if(fragment6.dane1.visibility == EditText.VISIBLE) {
                list1.add(fragment6.dane1.text.toString())
                list2.add(fragment6.dane2.selectedItem)
                list3.add(fragment6.dane3.text.toString())
                list4.add(fragment6.dane4.text.toString())
                jsonDetails(js, list1, list2, list3, list4)
            }
            return js
        }

        fun jsonDetails(js: JSONObject, dane1: ArrayList<String>, dane2: ArrayList<Any>, dane3: ArrayList<String>, dane4: ArrayList<String>){

            js.put("Data", dane1)
            js.put("Czynnosc", dane2)

//        var list = arrayListOf<String>()
//        list.addAll(dane3)
//        list.addAll(dane4)

            js.put("Dane1", dane3)
            js.put("Dane2", dane4)
        }
    }
}