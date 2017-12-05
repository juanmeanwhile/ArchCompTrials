package com.meanwhile.flatmates;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {


    private View mEmptyView;
    private RecyclerView mRecyclerView;
    private EditText mAddTaskField;
    private Button mAddButton;
    private TaskViewModel viewModel;

    public static Fragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        viewModel.init();

        viewModel.getPendingTasks().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> pendingTasks) {
                //Update UI

                ((TaskAdapter) mRecyclerView.getAdapter()).setData(pendingTasks);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmptyView = view.findViewById(R.id.empty_view);

        mAddTaskField = view.findViewById(R.id.add_task_field);
        mAddButton = view.findViewById(R.id.add_button);

        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new TaskAdapter());
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<String> data = null;

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TaskHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false));
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            String task = data.get(position);

            holder.name.setText(task);
        }

        @Override
        public int getItemCount() {
            return data == null? 0: data.size();
        }

        public void setData(List<String> data) {
            this.data = data;

            notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        TextView name;

        public TaskHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
        }
    }
}
