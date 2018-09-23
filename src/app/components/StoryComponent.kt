package app.components

import app.external.OwlCarousel
import app.models.Story
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledH1

@JsModule("src/app/images/our_story.jpg")
external val ourStoryImage: dynamic
@JsModule("src/app/images/next.png")
external val iconNext: dynamic
@JsModule("src/app/images/prev.png")
external val iconPrev: dynamic
@JsModule("src/app/images/ring.png")
external val iconRing: dynamic

interface StoryProps : RProps {
    var windowWidth: Int
    var windowHeight: Int
    var storyImageUrl: String
}

class StoryComponent : RComponent<StoryProps, RState>() {

    override fun RBuilder.render() {
        val style = ComponentStyles()

        styledDiv {
            css { +style.layout }

            styledDiv {
                css { +style.cover }

                styledH1 {
                    css {
                        color = Color.white
                    }
                    +"OUR STORY"
                }

                OwlCarousel {
                    attrs {
                        className = "owl-theme"
                        items = props.windowWidth / 250
                        slideBy = 2
                        dots = false
                        nav = true
                        navText = arrayOf("<img src='$iconPrev'/>", "<img src='$iconNext'/>")
                    }

                    val stories = listOf(
                            Story("March 1990", "Pop was born in Chiang Rai"),
                            Story("October 1990", "Ting was born in Chiang Mai"),
                            Story("2008", "Pop arrive at Computer Science, Chiang Mai University"),
                            Story("2009", "Ting arrive at Computer Science, Chiang Mai University"),
                            Story("2009 - 2012", "Special care senior-junior relationship by lucky draw"),
                            Story("2012", "Pop graduate from Chiang Mai University"),
                            Story("2013", "Ting graduate from Chiang Mai University"),
                            Story("2013", "Pop moves from Chiang Mai to Bangkok, Thailand"),
                            Story("2014", "Ting moves to Muscut, Oman"),
                            Story("July 2017", "Meet again after 4 years"),
                            Story("September 2017", "First date :)"),
                            Story("October 2017", "In Relationship"),
                            Story("27 March 2018", "Will you marry me?"),
                            Story("15 September 2018", "Happy wedding day", iconRing as? String))

                    stories.forEachIndexed { index, story ->
                        val position = if (index % 2 == 0)
                            StoryBlockComponent.BlockPosition.BOTTOM
                        else
                            StoryBlockComponent.BlockPosition.TOP
                        storyBlock(story, position)
                    }
                }
            }
        }
    }

    private inner class ComponentStyles : StyleSheet("ComponentStyles") {
        val layout by css {
            backgroundImage = Image("url('${props.storyImageUrl}')")
            backgroundRepeat = BackgroundRepeat.noRepeat
            backgroundPosition = "center"
            backgroundSize = "cover"
        }

        val cover by css {
            padding(40.px, 0.px)
            backgroundColor = Color.black.withAlpha(0.5)
        }
    }
}

fun RBuilder.story(windowWidth: Int, windowHeight: Int) = child(StoryComponent::class) {
    attrs.windowWidth = windowWidth
    attrs.windowHeight = windowHeight
    attrs.storyImageUrl = ourStoryImage
}