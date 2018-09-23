package app.components

import app.external.GalleryImage
import app.external.Transformation
import app.external.cloudinary
import app.external.reactGallery
import kotlinext.js.jsObject
import kotlinx.css.Color
import kotlinx.css.Overflow
import kotlinx.css.padding
import kotlinx.css.px
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import styled.css
import styled.styledDiv

interface GalleryComponentProps : RProps {
    var title: String
    var imagePublicIds: Array<String>
}

class GalleryComponent : RComponent<GalleryComponentProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                padding(80.px, 0.px, 0.px, 0.px)
                backgroundColor = Color("#ffffff")
                overflow = Overflow.auto
            }

            if (props.imagePublicIds.isNotEmpty()) {
                h1 {
                    +props.title
                }

                val cl = cloudinary.Core.new(jsObject {
                    cloudName = "ting-pop"
                })
                val galleryImages = props.imagePublicIds.mapIndexed { index, publicId ->

                    val ratio = getImageRatio(index)
                    val ratioTransformation = jsObject<Transformation> {
                        aspectRatio = "${ratio.width}:${ratio.height}"
                        crop = "fill"
                    }
                    val widthTransformation = jsObject<Transformation> {
                        width = "400"
                        dpr = "auto"
                        crop = "scale"
                    }
                    GalleryImage(
                            src = cl.url(publicId, jsObject {
                                transformation = arrayOf(jsObject {
                                    width = "2048"
                                    crop = "fit"
                                })
                            }),
                            thumbnail = cl.url(publicId, jsObject {
                                quality = 60
                                transformation = arrayOf(ratioTransformation, widthTransformation)
                            }),
                            thumbnailWidth = ratio.width, thumbnailHeight = ratio.height)
                }.toTypedArray()

                reactGallery {
                    attrs {
                        id = "wedding"
                        enableImageSelection = false
                        margin = 1
                        images = galleryImages
                    }
                }
            }
        }
    }
}

private fun getImageRatio(index: Int): ImageRatio = when (index % 9) {
    2, 5, 7 -> ImageRatio(4, 3)
    1, 3, 4, 6 -> ImageRatio(1, 1)
    else -> ImageRatio(16, 9)
}

private data class ImageRatio(val width: Int, val height: Int)

fun RBuilder.gallery(title: String, imagePublicIds: Array<String>) = child(GalleryComponent::class) {
    attrs.title = title
    attrs.imagePublicIds = imagePublicIds
}