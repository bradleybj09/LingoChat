package com.iambenbradley.lingochat.utils

/**
 * Created by Ben on 9/6/2017.
 */
data class Conversation(val contact: Contact, val preview: String, val timestamp: String, val read: Boolean, val seen: Boolean)