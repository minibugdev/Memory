package app.external

import react.RClass
import react.RProps

@JsModule("react-owl-carousel")
external val OwlCarousel: RClass<OwlCarouselProps>

external interface OwlCarouselProps : RProps {
    var className: String
    var items: Int
    var nav: Boolean
    var autoWidth: Boolean
    var navText: Array<String>
}
