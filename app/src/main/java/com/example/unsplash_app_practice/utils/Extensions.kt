package com.example.unsplash_app_practice.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.unsplash_app_practice.utils.Constants.TAG

//문자열 Json확인
fun String?.isJsonObject():Boolean {
    return this?.startsWith("{") == true && this.endsWith("}")
}
//문자열이 제이슨 배열인지
fun String?.isJsonArray() : Boolean{
    return this?.startsWith("[") == true && this.endsWith("]")
}

//에딧 텍스트에 대한 확장
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit){
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)
        }

    })
}