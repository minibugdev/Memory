package app.external

@JsModule("firebase")
external val firebase: Firebase

@JsModule("firebase")
external interface Firebase {
    fun initializeApp(config: FirebaseConfig)

    fun database(): Database

    interface Database {
        fun ref(table: String): Reference
    }

    interface Reference {
        fun <T> on(action: String, callback: (Data<T>) -> Unit)
    }

    interface Data<out T> {

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
