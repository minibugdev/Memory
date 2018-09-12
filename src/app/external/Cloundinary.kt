package app.external

@JsModule("cloudinary-core")
external val cloudinary: Core

@JsModule("cloudinary-core")
external interface Core {

    @JsName("Cloudinary")
    val Core: Sdk

    interface Sdk {
        fun new(options: Options): Cloudinary
    }

    interface Cloudinary {
        fun url(publicId: String): String
        fun url(publicId: String, transform: Transform): String
    }
}

external interface Transform {
    var quality: Int
    var transformation: Array<Transformation>
}

external interface Transformation {
    @JsName("aspect_ratio")
    var aspectRatio: String
    var crop: String
    var width: String
    var dpr: String
}

external interface ResourceResponse {
    val resources: Array<Resource>
}

external interface Resource {

    @JsName("public_id")
    val publicId: String
}

external interface Options {

    @JsName("cloud_name")
    var cloudName: String
}
