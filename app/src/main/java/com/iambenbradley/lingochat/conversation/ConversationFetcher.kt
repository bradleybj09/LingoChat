package com.iambenbradley.lingochat.conversation

import android.content.Context
import android.provider.Telephony
import android.util.Log
import com.iambenbradley.lingochat.dagger.LingoChatApplication
import com.iambenbradley.lingochat.utils.Contact
import com.iambenbradley.lingochat.utils.Message
import io.reactivex.Observable
import kotlin.collections.ArrayList

class ConversationFetcher() {

    val context = LingoChatApplication.component.getContext()

    lateinit var number: String
    lateinit var args: Array<String>

    val contentResolver = context.contentResolver

    fun getConversation(number: String) : Observable<ArrayList<Message>> {
        this.number = number
        args = arrayOf(number)
        val messages = ArrayList<Message>()
        messages.addAll(getReceivedSms())
        messages.addAll(getSentSms())
        messages.sort()
        for (message in messages) {
            Log.e("apptag",message.content)
        }
        return Observable.just(messages)
    }

    val smsReceivedUri = Telephony.Sms.Inbox.CONTENT_URI
    val smsSentUri = Telephony.Sms.Sent.CONTENT_URI
    val mmsReceivedUri = Telephony.Mms.Inbox.CONTENT_URI
    val mmsSentUri = Telephony.Mms.Sent.CONTENT_URI

    val smsReceivedProjection = arrayOf(Telephony.Sms.Inbox.BODY,Telephony.Sms.Inbox.DATE)
    val smsSentProjection = arrayOf(Telephony.Sms.Sent.BODY, Telephony.Sms.Sent.DATE)
    val mmsReceivedProjection = arrayOf(Telephony.Mms.Inbox.CONTENT_LOCATION, Telephony.Mms.Inbox.DATE)
    val mmsSentProjection = arrayOf(Telephony.Mms.Sent.CONTENT_LOCATION, Telephony.Mms.Sent.DATE)

    val smsReceivedSelection = Telephony.Sms.ADDRESS + " = ?"
    val smsSentSelection = Telephony.Sms.ADDRESS + " = ?"
    val mmsReceivedSelection = Telephony.Mms.CREATOR + " = ?"
    val mmsSentSelection = Telephony.Mms.CREATOR + " = ?"

    val smsReceivedSortOrder = Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
    val smsSentSortOrder = Telephony.Sms.Sent.DEFAULT_SORT_ORDER
    val mmsReceivedSortOrder = Telephony.Mms.Inbox.DEFAULT_SORT_ORDER
    val mmsSentSortOrder = Telephony.Mms.Sent.DEFAULT_SORT_ORDER

    fun getReceivedSms() : ArrayList<Message> {
        val returnMessages = ArrayList<Message>()
        val cursor = contentResolver.query(smsReceivedUri,smsReceivedProjection,smsReceivedSelection,args,smsReceivedSortOrder)
        if (cursor.moveToFirst()) {
            val count = cursor.count
            for (index in 0 until count) {
                val body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY))
                val time = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.DATE))
                returnMessages.add(Message(body, Contact("1"), time))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return returnMessages
    }

    fun getSentSms() : ArrayList<Message> {
        val returnMessages = ArrayList<Message>()
        val cursor = contentResolver.query(smsSentUri,smsSentProjection,smsSentSelection,args,smsSentSortOrder)
        if (cursor.moveToFirst()) {
            val count = cursor.count
            for (index in 0 until count) {
                val body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Sent.BODY))
                val time = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Sent.DATE))
                returnMessages.add(Message(body, Contact("0"),time))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return returnMessages
    }

    fun getReceivedMms() : ArrayList<Message> {
        val returnMessages = ArrayList<Message>()
        val cursor = contentResolver.query(mmsReceivedUri,mmsReceivedProjection,mmsReceivedSelection,args,mmsReceivedSortOrder)
        if (cursor.moveToFirst()) {
            val count = cursor.count
            for (index in 0 until count) {
                val content = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Inbox.CONTENT_LOCATION))
                val time = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Inbox.DATE))
                //TODO figure this out
                Log.e("message " + index, cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY)))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return returnMessages
    }

    fun getSentMms() : ArrayList<Message> {
        val returnMessages = ArrayList<Message>()
        val cursor = contentResolver.query(mmsSentUri,mmsSentProjection,mmsSentSelection,args,mmsSentSortOrder)
        if (cursor.moveToFirst()) {
            val count = cursor.count
            for (index in 0 until count) {
                val content = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Sent.CONTENT_LOCATION))
                val time = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Sent.DATE))
                //TODO figure this out
                Log.e("message " + index, cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY)))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return returnMessages
    }
}