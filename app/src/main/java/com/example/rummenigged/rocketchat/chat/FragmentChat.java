package com.example.rummenigged.rocketchat.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rummenigged.rocketchat.App;
import com.example.rummenigged.rocketchat.R;
import com.rocketchat.common.data.model.ErrorObject;
import com.rocketchat.common.listener.SubscribeListener;
import com.rocketchat.common.listener.TypingListener;
import com.rocketchat.core.RocketChatAPI;
import com.rocketchat.core.callback.HistoryListener;
import com.rocketchat.core.callback.MessageListener;
import com.rocketchat.core.model.RocketChatMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentChat extends Fragment implements HistoryListener
        , View.OnClickListener
        , MessageListener.SubscriptionListener
        , TextWatcher, TypingListener {

    private OnChatFragmentInteractionListener mListener;

    private RocketChatAPI.ChatRoom room;

    private RecyclerView rvMessages;
    private EditText etMessage;
    private ImageButton ibSendMessage;
    private TextView tvTyping;
    private AdapterMessages adapterMessages;

    private RocketChatAPI client;

    public FragmentChat() {
    }

    public static FragmentChat show(AppCompatActivity host) {
        FragmentChat fragment = new FragmentChat();
        host.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container_host, fragment)
                .addToBackStack(null)
                .commit();
        return fragment;
    }

    public FragmentChat room(RocketChatAPI.ChatRoom room){
        this.room = room;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = App.client;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(room.getRoomData().getRoomName());
        setUpUiElements(view);
        setUpRecyclerView();
        setUpListeners();

        if (room != null){
            room.getChatHistory(50, new Date(), null, this);
            room.subscribeRoomMessageEvent(new SubscribeListener() {
                @Override
                public void onSubscribe(Boolean isSubscribed, String subId) {
                    if (isSubscribed) {
                        Toast.makeText(getContext(), "Subscribed", Toast.LENGTH_SHORT).show();
                    }
                }
            }, this);
            room.subscribeRoomTypingEvent(new SubscribeListener() {
                @Override
                public void onSubscribe(Boolean isSubscribed, String subId) {
                    if (isSubscribed){
                        Toast.makeText(getContext(), "Subscribed", Toast.LENGTH_SHORT).show();
                    }
                }
            }, this);
        }
        return view;
    }

    private void setUpUiElements(View view){
        rvMessages = view.findViewById(R.id.rv_messages);
        etMessage = view.findViewById(R.id.et_message);
        ibSendMessage = view.findViewById(R.id.ib_send_message);
        tvTyping = view.findViewById(R.id.tv_typing);
    }

    private void setUpListeners(){
        ibSendMessage.setOnClickListener(this);
        etMessage.addTextChangedListener(this);
    }

    private void setUpRecyclerView(){
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMessages = new AdapterMessages(new ArrayList<RocketChatMessage>(), mListener);
        rvMessages.setAdapter(adapterMessages);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChatFragmentInteractionListener) {
            mListener = (OnChatFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChatFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoadHistory(final List<RocketChatMessage> list, int unreadNotLoaded, ErrorObject error) {
        Log.d("Fragment Chat", "onLoadHistory: ");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterMessages.swapData(list);
                rvMessages.smoothScrollToPosition(adapterMessages.getItemCount());
            }
        });

    }

    @Override
    public void onMessage(String roomId, RocketChatMessage message) {
        adapterMessages.add(message);
        rvMessages.smoothScrollToPosition(adapterMessages.getItemCount());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_send_message:
                String message = etMessage.getText().toString();
                if (!message.isEmpty()){
                    sendMessage(message);
                    etMessage.setText("");
                    rvMessages.smoothScrollToPosition(adapterMessages.getItemCount());
                }

            break;
        }
    }

    private void sendMessage(String message){
        room.sendMessage(message);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        boolean isTyping = !etMessage.getText().toString().equals("");
        Toast.makeText(getActivity(),  " Eu Estou digitando...", Toast.LENGTH_SHORT).show();
        room.sendIsTyping(isTyping);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onTyping(String roomId, final String user, Boolean istyping) {
//        if (istyping){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTyping.setVisibility(View.VISIBLE);
                String userIsTyping = String.format("%s está digitando...", user);
                tvTyping.setText(userIsTyping);
            }
        });

//        Toast.makeText(getActivity(), user + " está digitando...", Toast.LENGTH_SHORT).show();
//        }
    }

    public interface OnChatFragmentInteractionListener {
        void onChatFragmentInteraction(RocketChatMessage item);
    }
}
