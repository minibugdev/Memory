package app.external

import kotlin.js.Promise

@JsModule("axios")
external fun <T> axios(config: AxiosConfigSettings): Promise<AxiosResponse<T>>

external interface AxiosConfigSettings {
    var url: String
}

external interface AxiosResponse<T> {
    val data: T
    val status: Number
}
