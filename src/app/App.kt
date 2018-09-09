package app

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
                styledH3 {
                    +"OUR STORY"
                }
            }
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
                }
            }
        }
    }
}