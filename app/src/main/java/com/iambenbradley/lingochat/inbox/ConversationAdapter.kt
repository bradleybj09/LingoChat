package com.iambenbradley.lingochat.inbox

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iambenbradley.lingochat.databinding.ConversationListItemBinding
import com.iambenbradley.lingochat.utils.Conversation

/**
 * Created by Ben on 9/19/2017.
 */
class ConversationAdapter(private var conversations: ArrayList<Conversation>,
                          private var listener: OnItemClickListener)
    : RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ConversationListItemBinding.inflate(layoutInflater, parent, false)
        return ConversationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bind(conversations[position], listener)
    }

    override fun getItemCount(): Int = conversations.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<Conversation>) {
        conversations = arrayList
        notifyDataSetChanged()
    }

    class ConversationViewHolder(private var binding: ConversationListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(conversation: Conversation, listener: OnItemClickListener?) {
            binding.conversation = conversation
            if (listener != null) {
                binding.root.setOnClickListener({_ -> listener.onItemClick(layoutPosition)})
            }
            binding.executePendingBindings()
            Log.e("apptag adapter",binding.conversation!!.preview)

        }
    }
}