package app.models

data class Message(val from: String,
                   val value: String,
                   val created_date: Long)