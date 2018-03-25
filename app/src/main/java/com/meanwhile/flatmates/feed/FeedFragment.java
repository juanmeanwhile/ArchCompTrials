package com.meanwhile.flatmates.feed;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meanwhile.flatmates.R;
import com.meanwhile.flatmates.ViewModelFactory;
import com.meanwhile.flatmates.repository.db.UserTask;

import java.util.List;

public class FeedFragment extends Fragment {

    private FeedViewModel mViewModel;
    private RecyclerView mRecyclerView;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
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
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(FeedViewModel.class);

        mViewModel.init();

        mViewModel.getCompletedTasks().observe(this, new Observer<List<UserTask>>() {
            @Override
            public void onChanged(@Nullable List<UserTask> stats) {
                //Update UI
                ((FeedAdapter) mRecyclerView.getAdapter()).setData(stats);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new FeedAdapter());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public class FeedAdapter extends RecyclerView.Adapter<TaskHolder>{

        private List<UserTask> mData;

        public FeedAdapter(){
            mData = null;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TaskHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_user_task, parent, false));
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            UserTask task = mData.get(position);
            holder.text.setText(getString(R.string.done_task_text, task.getUsername(), task.getTaskName()));
            holder.estimation.setText(String.valueOf(task.getEstimation()));
        }

        @Override
        public int getItemCount() {
            return mData== null? 0 : mData.size();
        }

        public void setData(List<UserTask> data) {
            mData = data;
            notifyDataSetChanged();
        }
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        TextView text;
        TextView estimation;

        public TaskHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.title);
            estimation = itemView.findViewById(R.id.estimation);

        }
    }
}
