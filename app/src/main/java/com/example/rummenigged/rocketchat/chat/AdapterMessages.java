package com.example.rummenigged.rocketchat.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rummenigged.rocketchat.R;
import com.example.rummenigged.rocketchat.chat.FragmentChat.OnChatFragmentInteractionListener;
import com.example.rummenigged.rocketchat.util.DateTimeUtil;
import com.rocketchat.core.model.RocketChatMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.SentMessageHolder> {

    private List<RocketChatMessage> messagesList;
    private final OnChatFragmentInteractionListener mListener;

    private static final int SENT_MESSAGE_TYPE = 0;
    private static final int RECEIVED_MESSAGE_TYPE = 1;
    AdapterMessages(List<RocketChatMessage> items, OnChatFragmentInteractionListener listener) {
        messagesList = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public SentMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Adapter Message", "onCreateViewHolder: ");
        View view;
        if (viewType == SENT_MESSAGE_TYPE){
             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false);

        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_friend_message, parent, false);
        }

        return new SentMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SentMessageHolder holder, int position) {
        RocketChatMessage currentMessage= messagesList.get(position);
        holder.bind(currentMessage);
    }

    @Override
    public int getItemCount() {
        int size = messagesList.size();
        Log.d("Adapter Message", "getItemCount: " + size);
        return messagesList == null ? 0 :messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messagesList.get(position)
                .getSender()
                .getUserId()
                .equals("kLjYFd6MJckMpBhqc")
                    ? SENT_MESSAGE_TYPE
                    : RECEIVED_MESSAGE_TYPE;
//        if (messagesList.get(position).getSender().getUserId().equals("kLjYFd6MJckMpBhqc")){
//            return SENT_MESSAGE_TYPE;
//        }
//
//        if (!messagesList.get(position).getSender().getUserId().equals("kLjYFd6MJckMpBhqc")){
//            return RECEIVED_MESSAGE_TYPE;
//        }
//
//        if (messagesList.get(position).getMsgType().equals(RocketChatMessage.Type.ATTACHMENT)){
//
//        }
    }

    public void swapData(List<RocketChatMessage> list){
        int size = list.size();
        Log.d("Adapter Message", "swapData: list " + size);
        messagesList = new ArrayList<>(list);
        Collections.reverse(messagesList);
        Log.d("Adapter Message", "swapData: message " + messagesList.size());
        notifyDataSetChanged();
    }

    public void add(RocketChatMessage message){
        messagesList.add(message);
        notifyDataSetChanged();
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;
        final TextView mTvMessageTime;

        SentMessageHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mTvMessageTime = view.findViewById(R.id.tv_message_time);
        }

        void bind(RocketChatMessage message){
            mContentView.setText(message.getMessage());
            mTvMessageTime.setText(DateTimeUtil.extractHourAndMinute(message.getMsgTimestamp()));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public class ReceiverMessageHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;
        public RocketChatMessage mItem;
        final TextView mTvMessageTime;

        public ReceiverMessageHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mTvMessageTime = view.findViewById(R.id.tv_message_time);
        }

        void bind(RocketChatMessage message){
            mContentView.setText(message.getMessage());
            mTvMessageTime.setText(message.getMsgTimestamp().toString());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
