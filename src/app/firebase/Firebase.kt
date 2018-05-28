package app.firebase

@JsModule("firebase")
external val firebase: Firebase

@JsModule("firebase")
abstract external class Firebase {
    fun initializeApp(config: FirebaseConfig)

    fun database(): Database

    class Database {
        fun ref(table: String): Reference
    }

    class Reference {

        @JsName("on")
        fun <T> on(action: String, callback: (Data<T>) -> Unit)
    }

    class Data<out T> {

        @JsName("val")
        fun value(): T
    }
}

data class FirebaseConfig(
        val projectId: String,
        val apiKey: String,
        val authDomain: String,
        val databaseURL: String,
        val storageBucket: String,
        val messagingSenderId: String)

object FirebaseOperation {
    const val VALUE = "value"
    const val CHILD_ADDED = "child_added"
    const val CHILD_CHANGED = "child_changed"
    const val CHILD_REMOVED = "child_removed"
}
