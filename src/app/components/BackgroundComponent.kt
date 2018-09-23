package app.components

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv

@JsModule("src/app/images/cover.jpg")
external val coverImage: dynamic

interface BackgroundComponentProps : RProps {
    var windowWidth: Int
    var windowHeight: Int
    var backgroundImageUrl: String
}

class BackgroundComponent : RComponent<BackgroundComponentProps, RState>() {

    override fun RBuilder.render() {
        val componentStyles = ComponentStyles(props)

        styledDiv {
            css { +componentStyles.style }
        }
    }

    private inner class ComponentStyles(private val props: BackgroundComponentProps) : StyleSheet("ComponentStyles") {

        val style by css {
            backgroundImage = Image("url('${props.backgroundImageUrl}')")
            backgroundRepeat = BackgroundRepeat.noRepeat
            backgroundPosition = "center"
            backgroundSize = "cover"

            width = props.windowWidth.px
            height = props.windowHeight.px

            position = Position.fixed
            left = LinearDimension.initial
            top = LinearDimension.initial
            zIndex = -999999
        }
    }
}

fun RBuilder.background(windowWidth: Int, windowHeight: Int) = child(BackgroundComponent::class) {
    attrs.windowWidth = windowWidth
    attrs.windowHeight = windowHeight
    attrs.backgroundImageUrl = coverImage
}