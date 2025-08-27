package com.my_app.data.utils

import com.google.gson.Gson

object GsonUtils {

    inline fun  <reified T> String?.toClassValue(): T? {
        return Gson().fromJson(this, T::class.java)
    }

    inline fun <reified T> T.toStringValue(): String {
        return Gson().toJson(this, T::class.java)
    }

}