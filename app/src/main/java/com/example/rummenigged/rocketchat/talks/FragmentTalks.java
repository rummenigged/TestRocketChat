package com.example.rummenigged.rocketchat.talks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rummenigged.rocketchat.App;
import com.example.rummenigged.rocketchat.R;
import com.rocketchat.common.data.model.ErrorObject;
import com.rocketchat.core.RocketChatAPI;
import com.rocketchat.core.callback.GetSubscriptionListener;
import com.rocketchat.core.model.SubscriptionObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentTalks extends Fragment implements GetSubscriptionListener {

    private OnTalksFragmentInteractionListener mListener;

    private RecyclerView rvTalks;
    private AdapterTalks talksAdapter;
    private AdapterTest adapterTest;

    private Context context;

    private RocketChatAPI client;

    public FragmentTalks() {
    }

    public static FragmentTalks show(AppCompatActivity host) {
        FragmentTalks fragment = new FragmentTalks();
        host.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container_host, fragment)
                .commit();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        client = App.client;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talks, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Talks");
        setUpUiElement(view);
        setUpRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Talks");
        client.getSubscriptions(this);
        Toast.makeText(getActivity(), "Talks Fragment", Toast.LENGTH_SHORT).show();
    }

    private void setUpUiElement(View host) {
        rvTalks = host.findViewById(R.id.rv_talks);
    }

    private void setUpRecyclerView() {
        rvTalks.setLayoutManager(new LinearLayoutManager(getActivity()));
        talksAdapter = new AdapterTalks(new ArrayList<SubscriptionObject>(), mListener, context);
//        adapterTest = new AdapterTest(new ArrayList<String>(), mListener);

        rvTalks.setAdapter(talksAdapter);
//        adapterTest.swapData(getMockedSubscriptions());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTalksFragmentInteractionListener) {
            mListener = (OnTalksFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTalksFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onGetSubscriptions(final List<SubscriptionObject> subscriptions, ErrorObject error) {
        if (error == null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    talksAdapter.swapData(subscriptions);
                }
            });

        } else {
            Toast.makeText(getActivity(), "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> getMockedSubscriptions() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testList.add("Teste" + i);
        }

        return testList;
    }

    public interface OnTalksFragmentInteractionListener {
        void onTalksFragmentInteraction(SubscriptionObject item);

        void onTalksFragmentInteraction(String item);
    }

    public List<SubscriptionObject> getSubscriptionsList() {
        return new ArrayList<>(talksAdapter.getTalksList());
    }
}
