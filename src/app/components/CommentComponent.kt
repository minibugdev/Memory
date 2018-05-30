package app.components

import app.models.Message
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h3
import react.dom.span

interface CommentProps : RProps {
    var message: Message
}

class CommentComponent(props: CommentProps) : RComponent<CommentProps, RState>(props) {
    override fun RBuilder.render() {
        val message = props.message

        div {
            h3 {
                +message.value
            }
            span {
                +message.created_date.toString()
            }
        }
    }
}

fun RBuilder.comment(message: Message) = child(CommentComponent::class) {
    attrs.message = message
}