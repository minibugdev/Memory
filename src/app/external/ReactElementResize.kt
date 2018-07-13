package app.external

import react.RClass
import react.RProps
import react.ReactElement

@JsModule("react-resize-render")
external val reactRenderResize: ReactRenderResize

@JsModule("react-resize-render")
external interface ReactRenderResize {

    @JsName("RenderResize")
    val component: RenderResize
}

external interface RenderResize : RClass<RenderResizeProps>

external interface RenderResizeProps : RProps {
    var render: (Window) -> ReactElement
}

data class Window(val width: Int, val height: Int)
