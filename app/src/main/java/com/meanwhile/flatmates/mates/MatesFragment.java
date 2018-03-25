package com.meanwhile.flatmates.mates;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meanwhile.flatmates.R;
import com.meanwhile.flatmates.ViewModelFactory;
import com.meanwhile.flatmates.repository.db.UserStats;

import java.util.List;

public class MatesFragment extends Fragment {

    private EditText mNameField;
    private Button mAddButton;
    private MatesViewModel mViewModel;
    private RecyclerView mRecyclerView;

    public MatesFragment() {
        // Required empty public constructor
    }

    public static MatesFragment newInstance() {
        MatesFragment fragment = new MatesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MatesViewModel.class);

        mViewModel.init();

        mViewModel.getUserStats().observe(this, new Observer<List<UserStats>>() {
            @Override
            public void onChanged(@Nullable List<UserStats> stats) {
                //Update UI

                ((UserStatsAdapter) mRecyclerView.getAdapter()).setData(stats);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mates, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new UserStatsAdapter());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public class UserStatsAdapter extends RecyclerView.Adapter<StatsHolder>{

        private float mAveragePoints;
        private List<UserStats> mData;

        public UserStatsAdapter(){
            mData = null;
        }

        @Override
        public StatsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StatsHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_user_stats, parent, false));
        }

        @Override
        public void onBindViewHolder(StatsHolder holder, int position) {
            UserStats stats = mData.get(position);
            holder.name.setText(stats.getUsername());


            holder.balance.setText(String.valueOf(stats.getPoints() - mAveragePoints));
        }

        @Override
        public int getItemCount() {
            return mData== null? 0 : mData.size();
        }

        public void setData(List<UserStats> data) {
            mData = data;
            int total = 0;
            for (UserStats stats : data) {
                total += stats.getPoints();
            }
            mAveragePoints = total/data.size();
            notifyDataSetChanged();
        }
    }

    public class StatsHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView balance;

        public StatsHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            balance = itemView.findViewById(R.id.balance);

        }
    }
}
