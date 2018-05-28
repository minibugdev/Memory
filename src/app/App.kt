package app

import app.firebase.FirebaseConfig
import app.firebase.FirebaseOperation
import app.firebase.firebase
import app.models.Message
import logo.logo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.code
import react.dom.div
import react.dom.h2
import react.dom.p
import ticker.ticker

class App : RComponent<RProps, RState>() {
    override fun RState.init() {
        val database = FirebaseConfig(projectId = "tingpop-project",
                apiKey = "AIzaSyDvGg75Qax-8DYEsvcK2cTQr6AQ4v4tHQk",
                authDomain = "tingpop-project.firebaseapp.com",
                databaseURL = "https://tingpop-project.firebaseio.com",
                storageBucket = "",
                messagingSenderId = "471059879010")
                .run {
                    firebase.initializeApp(config = this)
                    firebase.database()
                }

        database.ref("messages")
                .on<Array<Message>>(FirebaseOperation.VALUE) {
                    it.value().forEach {
                        console.log(it.message)
                    }
                }
    }

    override fun RBuilder.render() {
        div("App-header") {
            logo()
            h2 {
                +"Welcome to React with Kotlin"
            }
        }
        p("App-intro") {
            +"To get started, edit "
            code { +"app/App.kt" }
            +" and save to reload."
        }
        p("App-ticker") {
            ticker()
        }
    }
}

fun RBuilder.app() = child(App::class) {}
