package com.meanwhile.flatmates.task;


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

import com.meanwhile.flatmates.R;
import com.meanwhile.flatmates.ViewModelFactory;
import com.meanwhile.flatmates.repository.model.Task;
import com.meanwhile.flatmates.repository.db.UserStats;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TaskAdapter.TaskAdapterListener, SelectUserDialogFragment.OnSelectUserListener {

    private View mEmptyView;
    private RecyclerView mRecyclerView;
    private EditText mAddTaskField;
    private Button mAddButton;
    private TaskViewModel mViewModel;

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

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(TaskViewModel.class);

        mViewModel.init();

        mViewModel.getPendingTask().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                //Update UI

                ((TaskAdapter) mRecyclerView.getAdapter()).setData(tasks);
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
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = mAddTaskField.getText().toString();
                mViewModel.onCreateTask(taskName);
            }
        });

        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new TaskAdapter(this));
    }

    @Override
    public void onTaskEstimated(Task task) {
        mViewModel.onTaskEstimated(task);
    }

    @Override
    public void onEstimatedTaskClick(Task task) {
        SelectUserDialogFragment.newInstance(task).show(getChildFragmentManager(), "selectUser");
    }

    @Override
    public void onUserSelected(Task task, UserStats user) {
        mViewModel.onTaskAssigned(task, user);
    }
}
