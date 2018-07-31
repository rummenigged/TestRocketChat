package com.example.rummenigged.rocketchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.rummenigged.rocketchat.chat.FragmentChat;
import com.example.rummenigged.rocketchat.talks.FragmentTalks;
import com.rocketchat.core.RocketChatAPI;
import com.rocketchat.core.factory.ChatRoomFactory;
import com.rocketchat.core.model.RocketChatMessage;
import com.rocketchat.core.model.SubscriptionObject;

import java.util.List;

public class HostActivity extends AppCompatActivity implements
        FragmentTalks.OnTalksFragmentInteractionListener
        , FragmentChat.OnChatFragmentInteractionListener{

    private ChatRoomFactory factory;
    private RocketChatAPI client;

    private FragmentTalks fragmentTalks;
    private FragmentChat fragmentChat;

    private Toolbar tbMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        setUpUiElements();
        setUpToolbar(tbMain);
        client = App.client;
        factory = client.getChatRoomFactory();
        fragmentTalks = FragmentTalks.show(this);
    }

    private void setUpUiElements(){
        tbMain = findViewById(R.id.tb_main);
    }

    private void setUpToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);

    }

    @Override
    public void onTalksFragmentInteraction(SubscriptionObject item) {
        List<SubscriptionObject> list = fragmentTalks.getSubscriptionsList();
        RocketChatAPI.ChatRoom room = factory.createChatRooms(list)
                .getChatRoomById(item.getRoomId());
        if (room != null){
            fragmentChat = FragmentChat.show(this).room(room);
        }
//        FragmentTalks.show(this);
    }

    @Override
    public void onTalksFragmentInteraction(String item) {
        FragmentTalks.show(this);
    }

    @Override
    public void onChatFragmentInteraction(RocketChatMessage item) {

    }
}
