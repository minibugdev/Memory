package app.components

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledImg

@JsModule("src/app/images/logo.png")
external val logo: dynamic

interface HeaderComponentProps : RProps {
    var windowWidth: Int
    var windowHeight: Int
}

class HeaderComponent : RComponent<HeaderComponentProps, RState>() {

    override fun RBuilder.render() {
        val componentStyles = ComponentStyles(props)

        styledDiv {
            css { +componentStyles.style }
            styledImg(src = logo as? String) {
                css {
                    position = Position.absolute
                    left = 0.px
                    right = 0.px
                    top = 50.px
                    bottom = 0.px
                    margin(LinearDimension.auto)
                }
            }
        }
    }

    private inner class ComponentStyles(private val props: HeaderComponentProps) : StyleSheet("ComponentStyles") {

        val style by css {
            backgroundColor = Color.black.withAlpha(0.35)

            width = props.windowWidth.px
            height = props.windowHeight.px
        }
    }
}

fun RBuilder.header(windowWidth: Int, windowHeight: Int) = child(HeaderComponent::class) {
    attrs.windowWidth = windowWidth
    attrs.windowHeight = windowHeight
}