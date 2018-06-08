package com.iambenbradley.lingochat.conversation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iambenbradley.lingochat.databinding.MessageListItemBinding
import com.iambenbradley.lingochat.utils.Message

class MessageAdapter(private var messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MessageListItemBinding.inflate(layoutInflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    fun replaceData(arrayList: ArrayList<Message>) {
        messages = arrayList
        notifyDataSetChanged()
    }

    class MessageViewHolder(private var binding: MessageListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
        }
    }
}