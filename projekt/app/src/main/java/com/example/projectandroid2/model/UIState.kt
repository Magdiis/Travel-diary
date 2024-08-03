package com.example.projectandroid2.model

import java.io.Serializable

open class UIState<T,E>(
    var loading: Boolean = false,
    var data: T? = null,
    var errors: E? = null
):Serializable {

}