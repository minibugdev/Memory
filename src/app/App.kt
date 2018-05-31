package app

import app.firebase.FirebaseConfig
import app.firebase.FirebaseOperation
import app.firebase.firebase
import app.models.Message
import app.semanticui.Container
import app.semanticui.Image
import app.semanticui.TextAlign
import react.*

interface AppState : RState {
    var messages: Array<Message>
}

class App : RComponent<RProps, AppState>() {
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
        Container {
            attrs {
                fluid = true
                textAlign = TextAlign.RIGHT
            }

            Image {
                attrs {
                    src = "https://react.semantic-ui.com/assets/images/wireframe/image.png"
                    fluid = true
                }
            }
        }
//        if (state.messages.isNotEmpty()) {
//            comments(state.messages)
//        }
    }
}

fun RBuilder.app() = child(App::class) {}
