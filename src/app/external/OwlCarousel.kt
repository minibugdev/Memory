package app.external

import react.RClass
import react.RProps

@JsModule("react-owl-carousel2")
external val OwlCarousel: RClass<OwlCarouselProps>

external interface OwlCarouselProps : RProps {
    var options: Options
}

data class Options(
        val items: Int = 1,
        val autoWidth: Boolean = false,
        val nav: Boolean = false,
        val rewind: Boolean = true,
        val dots: Boolean = true)