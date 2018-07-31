package com.example.rummenigged.rocketchat.talks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rummenigged.rocketchat.R;
import com.example.rummenigged.rocketchat.talks.FragmentTalks.OnTalksFragmentInteractionListener;
import com.rocketchat.core.model.SubscriptionObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterTest extends RecyclerView.Adapter<AdapterTest.ViewHolder> {

    private List<String> talksList;
    private final OnTalksFragmentInteractionListener mListener;

    public AdapterTest(List<String> items, OnTalksFragmentInteractionListener listener) {
        talksList = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Adapter Test", "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_talk, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.d("Adapter Test", "onBindViewHolder: ");
        String item = talksList.get(position);
        holder.mContentView.setText(item);
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter Test", "getItemCount: " + talksList.size());
        return talksList.size();
    }

    public void swapData(List<String> list){
        Log.d("Adapter Test", "swapData: ");
        talksList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public List<String> getTalksList() {
        return talksList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView mContentView;
        public String mItem;

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
//                mListener.onTalksFragmentInteraction();
                mListener.onTalksFragmentInteraction(talksList.get(getAdapterPosition()));
            }
        }
    }
}
