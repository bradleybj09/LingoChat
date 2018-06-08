package com.iambenbradley.lingochat.utils

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

/**
 * Created by Ben on 10/15/2017.
 */
class SmsReceiver: BroadcastReceiver() {

    private val SMS_BUNDLE = "pdus"

    override fun onReceive(p0: Context?, p1: Intent?) {
        val intentExtras = p1?.extras

        if (intentExtras != null) {
            val sms = intentExtras.get(SMS_BUNDLE) as Array<Object>
            val message = Message("", Contact(""), "")
            for (index in sms) {
                val format = intentExtras.getString("format")
                val smsMessage: SmsMessage = SmsMessage.createFromPdu(index as ByteArray,format)

                message.content = smsMessage.displayMessageBody
                message.sender = Contact(smsMessage.displayOriginatingAddress)
                message.time = smsMessage.timestampMillis.toString()
                Log.e("apptag", message.content)
                Log.e("apptag",message.sender.number)
            }
            val contentValues = ContentValues()
            contentValues.put("address",message.sender.number)
            contentValues.put("body",message.content)
            contentValues.put("read","0")
            contentValues.put("date",message.time)
            val contentResolver = p0?.contentResolver
            if (contentResolver == null) {
                Toast.makeText(p0,"content resolver failed", Toast.LENGTH_SHORT).show()
            } else {
                contentResolver.insert(Uri.parse("content://sms/inbox"),contentValues)
            }
            val i = Intent()
            i.setAction(".SmsReceiver")
            i.putExtra("message","refresh")
            p0?.sendBroadcast(i)
            //TODO: check if app is open (to this specific contact's conversation) and make notification if not, update conversation if is, and refresh.
        }
    }
}