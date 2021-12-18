package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonationHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonationHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    DonationHistoryRecyclerViewAdapter adapter;
    List<DonationWithUserAndCharity> d;
    AppDatabase db;

    public DonationHistoryFragment() {
    }

    public DonationHistoryFragment(List<DonationWithUserAndCharity> d, AppDatabase db) {
        // Required empty public constructor
        this.d = d;
        MainActivity main = (MainActivity) getActivity();
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DonationHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DonationHistoryFragment newInstance(String param1, String param2) {
        DonationHistoryFragment fragment = new DonationHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donation_history, container, false);
        recyclerView = view.findViewById(R.id.DonationHistoryRecyclerView);

        DonationHistoryActivity activity = (DonationHistoryActivity) getActivity();

        Log.e("view", "view main in donationhistoryfragment " + activity.toString());

        adapter = new DonationHistoryRecyclerViewAdapter(view.getContext(),d,activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}