package app

import app.external.reactRenderResize
import app.firebase.FirebaseConfig
import app.firebase.FirebaseOperation
import app.firebase.firebase
import app.models.Message
import kotlinx.css.*
import react.*
import react.dom.div
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
            css { +componentStyles.coverBackground }
        }

        div {
            styledDiv {
                css { +componentStyles.cover }
            }

            styledSection {
                css {
                    padding(20.px)
                    backgroundColor = Color.gray
                    height = 120.px
                }
            }
        }
    }

    private inner class ComponentStyles(private val props: AppProps) : StyleSheet("ComponentStyles") {

        val coverBackground by css {
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
                    coverImageUrl = "https://savvyapps.com/runtime/uploads/blog/3425/2017.04_17-kotlinTips_170419_091147_871d0356adaf9e2d853d7f997a76ee78.jpg"
                }
            }
        }
    }
}