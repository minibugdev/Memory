package app.components

import kotlinx.css.Color
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.px
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv
import styled.styledH3
import styled.styledImg

@JsModule("src/app/images/knot.png")
external val welcomeKnot: dynamic

class WelcomeComponent : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                backgroundColor = Color("#EEEEEE")
                margin(0.px)
                padding(80.px, 30.px)
            }
            div {
                +"On the 15th of September, 2018"
            }
            styledH3 {
                css { margin(10.px, 0.px) }
                +"Unchalee Saechang (Ting) & Teeranai Buddee (Pop)"
            }
            div {
                +"eloped in Kong Garden View Resort, Chiang Rai"
            }
            styledImg(src = welcomeKnot as? String) {
                css { width = 100.px }
            }
        }
    }
}

fun RBuilder.welcome() = child(WelcomeComponent::class) {
}