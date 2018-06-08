package com.iambenbradley.lingochat.utils

/**
 * Created by Ben on 9/6/2017.
 */
data class Contact(val number: String) {
    var firstName = ""
    var lastName = ""
    var description = ""
    var known = false
    constructor(number: String, firstName: String, lastName: String, description: String) : this(number) {
        this.firstName = firstName
        this.lastName = lastName
        this.description = description
        known = true
    }
}