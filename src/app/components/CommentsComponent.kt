package app.components

import app.models.Message
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

interface CommentsProps : RProps {
    var messages: Array<Message>
}

class CommentsComponent(props: CommentsProps) : RComponent<CommentsProps, RState>(props) {
    override fun RBuilder.render() {
        val messages = props.messages
        if (messages.isEmpty())
            return

        div {
            messages.forEach {
                comment(it)
            }
        }
    }
}

fun RBuilder.comments(messages: Array<Message>) = child(CommentsComponent::class) {
    attrs.messages = messages
}