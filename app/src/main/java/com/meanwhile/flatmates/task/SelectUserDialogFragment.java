package com.meanwhile.flatmates.task;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meanwhile.flatmates.R;
import com.meanwhile.flatmates.repository.db.MyDatabase;
import com.meanwhile.flatmates.repository.model.Task;
import com.meanwhile.flatmates.repository.db.UserStats;

import java.util.List;
import java.util.concurrent.Executors;

import static com.meanwhile.flatmates.repository.db.MyDatabase.DB_NAME;

/**
 * User selector dialog
 */
public class SelectUserDialogFragment extends DialogFragment {

    private static final String ARG_TASK = "task";
    private static final int NUM_COLUMNS = 2;
    private Task mTask;

    private RecyclerView mRecyclerView;

    private OnSelectUserListener mListener;
    private TaskRepository mTaskRepo;

    public SelectUserDialogFragment() {
    }

    public static SelectUserDialogFragment newInstance(Task task) {
        SelectUserDialogFragment fragment = new SelectUserDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTask = getArguments().getParcelable(ARG_TASK);
        }

        MyDatabase db = Room.databaseBuilder(getContext(), MyDatabase.class, DB_NAME).build();
        mTaskRepo = new TaskRepository(db.taskDao(), db.userDao(), Executors.newSingleThreadExecutor());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estimation_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUM_COLUMNS));
        mRecyclerView.setAdapter(new UserAdapter());
    }

    @Override
    public void onStart() {
        super.onStart();

        mTaskRepo.getUserStats().observe(this, new Observer<List<UserStats>>() {
            @Override
            public void onChanged(@Nullable List<UserStats> userStats) {
                ((UserAdapter) mRecyclerView.getAdapter()).setData(userStats);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectUserListener) {
            mListener = (OnSelectUserListener) context;
        } if (getParentFragment() != null && getParentFragment() instanceof  OnSelectUserListener) {
            mListener = (OnSelectUserListener) getParentFragment();
        }else {

            throw new RuntimeException(context.toString()
                    + " must implement OnSelectUserListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSelectUserListener {
        void onUserSelected(Task task, UserStats user);
    }

    class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
        private List<UserStats> mData;

        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onUserSelected(mTask, mData.get((Integer) view.getTag()));
                dismiss();
            }
        };

        public UserAdapter () {
        }

        public void setData(List<UserStats> userStats) {
            mData = userStats;
        }

        @Override
        public UserAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UserHolder(LayoutInflater.from(getContext()).inflate(R.layout.grid_item_user, parent, false));
        }

        @Override
        public void onBindViewHolder(UserAdapter.UserHolder holder, int position) {
            UserStats userStats = mData.get(position);

            holder.username.setText(userStats.getUsername());

            //TODO balance

            //TODO image

            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(mOnClickListener);

        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        class UserHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView username;
            TextView balance;

            public UserHolder(View view) {
                super(view);

                image = view.findViewById(R.id.image);
                username = view.findViewById(R.id.select_user_name);
                balance = view.findViewById(R.id.balance);
            }
        }
    }
}
