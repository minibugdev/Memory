package app

import app.external.Options
import app.external.OwlCarousel
import app.external.reactRenderResize
import app.firebase.FirebaseConfig
import app.firebase.FirebaseOperation
import app.firebase.firebase
import app.models.Message
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import react.*
import react.dom.br
import react.dom.div
import react.dom.strong
import styled.*

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
            }

            styledDiv {
                css {
                    padding(60.px, 0.px)
                    fontSize = 18.px
                    backgroundColor = Color("#EEEEEE")
                }
                styledP {
                    css {
                        lineHeight = LineHeight("1.8em")
                    }
                    +"On the 15th of September, 2018"
                    br {}
                    strong {
                        +"Unchalee Saechang & Teeranai Buddee"
                    }
                    br {}
                    +"eloped in Kong Garden View Resort, Chiang Rai"
                }
            }

            styledDiv {
                css { +storyStyles.layout }
                styledH2 {
                    css {
                        color = Color.white
                    }
                    +"OUR STORY"
                }

                OwlCarousel {
                    attrs {
                        options = Options(items = 6, autoWidth = true, nav = false, rewind = false, dots = true)
                    }

                    story(StoryPosition.TOP, storyStyles)
                    story(StoryPosition.BOTTOM, storyStyles)
                    story(StoryPosition.TOP, storyStyles)
                    story(StoryPosition.BOTTOM, storyStyles)
                    story(StoryPosition.TOP, storyStyles)
                    story(StoryPosition.BOTTOM, storyStyles)
                    story(StoryPosition.TOP, storyStyles)
                    story(StoryPosition.BOTTOM, storyStyles)
                    story(StoryPosition.TOP, storyStyles)
                    story(StoryPosition.BOTTOM, storyStyles)
                    story(StoryPosition.TOP, storyStyles)
                    story(StoryPosition.BOTTOM, storyStyles)
                }
            }
        }
    }

    private fun RBuilder.story(position: StoryPosition, style: StoryStyles): ReactElement {
        return styledDiv {
            if (position == StoryPosition.TOP) {
                styledDiv {
                    css { +style.fixheight }
                    styledDiv {
                        css { +style.storytop }
                        +"October 1990"
                    }

                    styledP {
                        css { +style.story }
                        +"Ting was born in Chiang Rai"
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
                        +"March 1990"
                    }
                    styledP {
                        css { +style.story }
                        +"Pop was born in Chiang Rai"
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
            padding(40.px, 0.px)
            backgroundImage = Image("url('${props.storyImageUrl}')")
            backgroundRepeat = BackgroundRepeat.noRepeat
            backgroundPosition = "center"
            backgroundSize = "cover"
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
            backgroundColor = Color.white.withAlpha(0.5)

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
                    coverImageUrl = "https://scontent.fbkk1-5.fna.fbcdn.net/v/t1.0-9/41045770_1179593592178688_4000621135526887424_o.jpg?_nc_fx=fbkk1-1&_nc_cat=0&_nc_eui2=AeEnLLjwWEnRHxRDFa1QUtN8ThTI2z0hC1MhtkpMPwrw-S2s2g-svImMrnPCFIWBUP0Sz7Es5-XDHVJKWZQCsQEj91aDt1maZtMzsKGRSlg5Lg&oh=80d04173611bbe2a64a086e55fd8c5cb&oe=5C2CCA50"
                    storyImageUrl = "https://scontent.fbkk1-4.fna.fbcdn.net/v/t1.0-9/40973628_1179594778845236_2662423675302576128_o.jpg?_nc_fx=fbkk1-1&_nc_cat=0&_nc_eui2=AeE_-vF_0Ga_vnzWfK2ZAc8VKzagHkm_sqwKqODoTkBlHPVWSS9QWu7OfP_ZMBrFWT-UHEXkKhuS4fBHaSSC3BmuPkxP1PXIiVAS3_lzDCh7sg&oh=722402406f816464c906f6cc446b880f&oe=5C244750"
                }
            }
        }
    }
}