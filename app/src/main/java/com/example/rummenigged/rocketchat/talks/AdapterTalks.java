package com.example.rummenigged.rocketchat.talks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rummenigged.rocketchat.R;
import com.example.rummenigged.rocketchat.talks.FragmentTalks.OnTalksFragmentInteractionListener;
import com.rocketchat.core.RocketChatAPI;
import com.rocketchat.core.model.SubscriptionObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterTalks extends RecyclerView.Adapter<AdapterTalks.ViewHolder> {

    private List<SubscriptionObject> talksList;
    private final OnTalksFragmentInteractionListener mListener;
    private Context context;

    public AdapterTalks(List<SubscriptionObject> items, FragmentTalks.OnTalksFragmentInteractionListener listener, Context context) {
        talksList = items;
        mListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Adapter Talks", "onCreateViewHolder: ");
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_talk, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.d("Adapter Talks", "onBindViewHolder: ");
        SubscriptionObject item = talksList.get(position);
        holder.mContentView.setText(item.getRoomName());
    }

    @Override
    public int getItemCount() {
        int size = talksList.size();
        Log.d("Adapter Talks", "getItemCount: " + size);
        return talksList == null ? 0 :talksList.size();
    }

    public void swapData(List<SubscriptionObject> list){
        int size = list.size();
        Log.d("Adapter Talks", "swapData: " + size);
        talksList = new ArrayList<>(list);
//        talksList = list;
        notifyDataSetChanged();
    }

    public List<SubscriptionObject> getTalksList() {
        return talksList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView mContentView;
        public SubscriptionObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            if (null != mListener) {
                if (getAdapterPosition() >= 0) {
                    mListener.onTalksFragmentInteraction(talksList.get(getAdapterPosition()));
                }
            }
        }
    }
}
