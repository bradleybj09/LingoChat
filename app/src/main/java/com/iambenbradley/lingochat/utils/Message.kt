package com.iambenbradley.lingochat.utils

data class Message(var content: String, var time: String) : Comparable<Message> {

    var sender: Contact = Contact("")
    constructor(content: String, sender: Contact, time: String) : this(content, time) {
        this.sender = sender
    }

    override fun compareTo(other: Message): Int {
        return (time.toDouble() - other.time.toDouble()).toInt()
    }
}