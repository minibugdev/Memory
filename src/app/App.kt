package app

import app.external.OwlCarousel
import app.external.reactRenderResize
import app.firebase.FirebaseConfig
import app.firebase.FirebaseOperation
import app.firebase.firebase
import app.models.Message
import app.models.Story
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import react.*
import react.dom.div
import react.dom.img
import styled.*

@JsModule("src/app/images/logo.png")
external val logo: dynamic
@JsModule("src/app/images/knot.png")
external val knot: dynamic
@JsModule("src/app/images/next.png")
external val iconNext: dynamic
@JsModule("src/app/images/prev.png")
external val iconPrev: dynamic
@JsModule("src/app/images/ring.png")
external val iconRing: dynamic

interface AppState : RState {
    var messages: Array<Message>
}

interface AppProps : RProps {
    var windowWidth: Int
    var windowHeight: Int
    var coverImageUrl: String
    var storyImageUrl: String
}

class App : RComponent<AppProps, AppState>() {
    private val database by lazy {
        FirebaseConfig(projectId = "tingpop-project",
                apiKey = "AIzaSyDvGg75Qax-8DYEsvcK2cTQr6AQ4v4tHQk",
                authDomain = "tingpop-project.firebaseapp.com",
                databaseURL = "https://tingpop-project.firebaseio.com",
                storageBucket = "",
                messagingSenderId = "471059879010")
                .run {
                    firebase.initializeApp(config = this)
                    firebase.database()
                }
    }

    override fun AppState.init() {
        messages = emptyArray()
    }

    override fun componentDidMount() {
        database.ref("messages")
                .on<Array<Message>>(FirebaseOperation.VALUE) {
                    setState {
                        messages = it.value()
                    }
                }
    }

    override fun RBuilder.render() {
        val componentStyles = ComponentStyles(props)
        val storyStyles = StoryStyles()

        styledDiv {
            css { +componentStyles.coverImage }
        }

        div {
            styledDiv {
                css { +componentStyles.cover }
                styledImg(src = logo as? String) {
                    css {
                        position = Position.absolute
                        left = 0.px
                        right = 0.px
                        top = 0.px
                        bottom = 0.px
                        margin(LinearDimension.auto)
                    }
                }
            }

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
                styledImg(src = knot as? String){
                    css { width = 100.px }
                }
            }

            styledDiv {
                css { +storyStyles.layout }

                styledDiv {
                    css { +storyStyles.cover }

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
                            val position = if (index % 2 == 0) StoryPosition.BOTTOM else StoryPosition.TOP
                            story(story, position, storyStyles)
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.story(story: Story, position: StoryPosition, style: StoryStyles): ReactElement {
        return styledDiv {
            if (position == StoryPosition.TOP) {
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

    private enum class StoryPosition {
        TOP, BOTTOM
    }

    private inner class StoryStyles : StyleSheet("StoryStyles") {
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

    private inner class ComponentStyles(private val props: AppProps) : StyleSheet("ComponentStyles") {

        val coverImage by css {
            backgroundImage = Image("url('${props.coverImageUrl}')")
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

        val cover by css {
            backgroundColor = Color.black.withAlpha(0.35)

            width = props.windowWidth.px
            height = props.windowHeight.px
        }
    }
}

fun RBuilder.app() = reactRenderResize.component {
    attrs {
        render = { window ->
            child(App::class) {
                attrs {
                    windowWidth = window.width
                    windowHeight = window.height
                    coverImageUrl = "https://firebasestorage.googleapis.com/v0/b/tingpop-project.appspot.com/o/IMG_8061_opt.jpg?alt=media&token=dd7c6260-7c0e-41ed-9643-170a73c5b8f2"
                    storyImageUrl = "https://firebasestorage.googleapis.com/v0/b/tingpop-project.appspot.com/o/IMG_7743_opt.jpg?alt=media&token=1dfb723f-a9de-445f-8c45-089aac290835"
                }
            }
        }
    }
}