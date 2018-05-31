@file:JsModule("semantic-ui-react")

package app.semanticui

import react.RClass
import react.RProps

external val Container: RClass<ContainerProps>

external interface ContainerProps : RProps {
    var className: String
    var fluid: Boolean
    var textAlign: String
}

external val Image: RClass<ImageProps>

external interface ImageProps : RProps {
    var className: String
    var src: String
    var fluid: Boolean
}