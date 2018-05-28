package app.models

data class Message(val from: String,
                   val message: String,
                   val created_date: Long)