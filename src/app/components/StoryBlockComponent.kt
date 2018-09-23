package app.components

import app.models.Story
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.img
import styled.*

interface StoryBlockProps : RProps {
    var story: Story
    var position: StoryBlockComponent.BlockPosition
}

class StoryBlockComponent : RComponent<StoryBlockProps, RState>() {

    override fun RBuilder.render() {
        val style = ComponentStyles()
        val story = props.story

        styledDiv {
            if (props.position == BlockPosition.TOP) {
                styledDiv {
                    css { +style.fixheight }
                    styledH4 {
                        css { +style.storytop }
                        story.icon?.let { icon ->
                            img(src = icon) {}
                        }
                        +story.title
                    }

                    styledP {
                        css { +style.story }
                        +story.details
                    }
                }

                styledH4 { css { +style.blankstory } }
                styledHr { css { +style.divider } }
                styledDiv { css { +style.storyemptyblock } }
            } else {
                styledDiv {

                    styledDiv { css { +style.storyemptyblock } }
                    styledHr { css { +style.divider } }
                    styledH4 { css { +style.blankstory } }
                    styledH4 {
                        css { +style.storybottom }
                        story.icon?.let { icon ->
                            img(src = icon) {}
                        }
                        +story.title
                    }
                    styledP {
                        css { +style.story }
                        +story.details
                    }
                }
            }
        }
    }

    private inner class ComponentStyles : StyleSheet("ComponentStyles") {
        val fixheight by css {
            height = 120.px
        }

        val storyemptyblock by css {
            height = 196.px
        }

        val storybottom by css {
            color = Color("#afc6d1")
            padding(10.px, 20.px, 0.px)
            margin(0.px)
        }

        val storytop by css {
            color = Color("#afc6d1")
            marginTop = 50.px
            marginBottom = 0.px
            padding(0.px, 20.px, 0.px)
        }

        val blankstory by css {
            marginBottom = 0.px
            marginTop = 0.px
            marginLeft = 50.pct
            width = 50.pct
            height = 26.px
            paddingTop = 0.px
            borderLeft = "1px solid #333"
        }

        val divider by css {
            width = 100.pct
            height = 0.px
            alignContent = Align.center
            alignItems = Align.center
            alignSelf = Align.center
            textAlign = TextAlign.center
            border = "0"
            borderTop = "1px solid #eee"
            borderColor = Color("#333")
            margin(0.px, LinearDimension.auto)
        }

        val story by css {
            color = Color("#fff");
            lineHeight = LineHeight("2em")
            padding(0.px, 20.px)
            margin(0.px)
        }
    }

    enum class BlockPosition {
        TOP, BOTTOM
    }
}

fun RBuilder.storyBlock(story: Story, position: StoryBlockComponent.BlockPosition) = child(StoryBlockComponent::class) {
    attrs.position = position
    attrs.story = story
}