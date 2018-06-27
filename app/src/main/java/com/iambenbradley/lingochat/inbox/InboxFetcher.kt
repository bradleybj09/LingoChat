package com.iambenbradley.lingochat.inbox

import android.content.Context
import android.net.Uri
import com.iambenbradley.lingochat.dagger.ApplicationComponent
import com.iambenbradley.lingochat.dagger.LingoChatApplication
import com.iambenbradley.lingochat.utils.Contact
import com.iambenbradley.lingochat.utils.Conversation
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Ben on 10/8/2017.
 */
class InboxFetcher() {

    val context = LingoChatApplication.component.getContext()

    val contentResolver = context.contentResolver
    val projection: Array<String> = arrayOf("*")
    val uri = Uri.parse("content://mms-sms/conversations")
    var conversations = ArrayList<Conversation>()

    fun refreshInbox() : Observable<ArrayList<Conversation>> {
        conversations.clear()
        val cursor = contentResolver.query(uri,projection,null,null,"date DESC")
        val addressColumn = cursor.getColumnIndex("address")
        val bodyColumn = cursor.getColumnIndex("body")
        val dateColumn = cursor.getColumnIndex("date")
        val personContactIdColumn = cursor.getColumnIndex("person")
        val readColumn = cursor.getColumnIndex("read")
        val seenColumn = cursor.getColumnIndex("seen")

        //TODO: handle finding the person in contacts, use that person to create contact

        while (cursor.moveToNext()) {
            val contact = Contact(cursor.getString(addressColumn))
            val body = cursor.getString(bodyColumn)

            val dateInt = cursor.getLong(dateColumn)
            val date = Date(dateInt)
            val format = SimpleDateFormat.getDateInstance()
            val dateText = format.format(date)

            val read = cursor.getInt(readColumn)
//            val seen = cursor.getInt(seenColumn)
            val readBool = read == 1

            conversations.add(Conversation(contact, body, dateText, readBool, false))
        }
        return Observable.just(conversations)
    }
}