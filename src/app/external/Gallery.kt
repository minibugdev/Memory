package app.external

import react.RClass
import react.RProps

@JsModule("react-grid-gallery")
external val reactGallery: RClass<GalleryProps>

external interface GalleryProps : RProps {
    var id: String
    var enableImageSelection: Boolean
    var margin: Int
    var images: Array<GalleryImage>
}

data class GalleryImage(
        val src: String,
        val thumbnail: String,
        val thumbnailWidth: Int,
        val thumbnailHeight: Int)
