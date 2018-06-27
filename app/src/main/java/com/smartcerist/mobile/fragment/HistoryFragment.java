package com.smartcerist.mobile.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.RoomActivity;
import com.smartcerist.mobile.activity.RoomsActivity;
import com.smartcerist.mobile.adapter.HistoryCustomAdapter;
import com.smartcerist.mobile.model.ObjectEvent;
import com.smartcerist.mobile.model.Room;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private List<ObjectEvent> events = new ArrayList<ObjectEvent>();
    RecyclerView mRecyclerView;

    private View view;

    public HistoryFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null)
            view = inflater.inflate(R.layout.fragment_history, container, false);

        Activity activity = getActivity();
        Room room = null;
        if(activity instanceof RoomsActivity)
            room = ((RoomsActivity) activity).getRoom();
        else if(activity instanceof RoomActivity)
            room = ((RoomActivity) activity).getRoom();

        assert room != null;
        events = room.getEvents();


        mRecyclerView = view.findViewById(R.id.eventsList);
        HistoryCustomAdapter historyCustomAdapter = new HistoryCustomAdapter(getActivity(), events);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity() ,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(historyCustomAdapter);
        return view;
    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
