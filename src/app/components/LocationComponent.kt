package app.components

import kotlinx.css.Color
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.px
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv
import styled.styledIframe

class LocationComponent : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                paddingTop = 80.px
                backgroundColor = Color.white
            }
            styledIframe {
                css {
                    border = "0"
                    margin(0.px)
                    padding(0.px)
                }
                attrs {
                    src = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3751.74940046674!2d99.84863131491393!3d19.892800986624636!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x30d7064f17cbc839%3A0x486f6aedfce304cc!2sKong+Garden+View+Resort+Chiang+Rai!5e0!3m2!1sen!2sth!4v1536967871345"
                    width = "100%"
                    height = "400"
                }
            }
        }
    }
}

fun RBuilder.location() = child(LocationComponent::class) {
}