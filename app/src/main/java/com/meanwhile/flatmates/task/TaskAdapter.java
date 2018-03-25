package com.meanwhile.flatmates.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.meanwhile.flatmates.R;
import com.meanwhile.flatmates.repository.model.Task;

import java.util.List;

/**
 * Created by mengujua on 08/12/17.
 */

class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface TaskAdapterListener {
        void onTaskEstimated(Task task);

        void onEstimatedTaskClick(Task task);
    }

    private static final int TYPE_ESTIMATED = 1;
    private static final int TYPE_TO_ESTIMATE = 2;

    private List<Task> mData = null;
    private TaskAdapterListener mListener;

    View.OnClickListener mOnEstimationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Task task = mData.get((int) view.getTag());
            float estimation = 0;
            if (view.getId() == R.id.estimation_half) {
                estimation = 0.5f;
            } else if (view.getId() == R.id.estimation_one) {
                estimation = 1;
            } else if (view.getId() == R.id.estimation_two) {
                estimation = 2;
            } else if (view.getId() == R.id.estimation_five) {
                estimation = 5;
            } else if (view.getId() == R.id.estimation_ten) {
                estimation = 10;
            }

            task.setEstimation(estimation);

            mListener.onTaskEstimated(task);
        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mListener.onEstimatedTaskClick(mData.get((Integer) view.getTag()));
        }
    };

    public TaskAdapter(TaskAdapterListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getEstimation() > 0) {
            return TYPE_ESTIMATED;
        } else {
            return TYPE_TO_ESTIMATE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_ESTIMATED:
                return new EstimatedHolder(inflater.inflate(R.layout.list_item_estimated_task, parent, false));

            default: //TYPE_TO_ESTIMATE
                return new TaskHolder(inflater.inflate(R.layout.list_item_task, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Task task = mData.get(position);

        if (holder instanceof EstimatedHolder) {
            EstimatedHolder eh = ((EstimatedHolder) holder);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(mOnClickListener);
            eh.name.setText(task.getName());
            eh.estimation.setText(String.valueOf(task.getEstimation()));

        } else {
            TaskHolder th = (TaskHolder) holder;
            th.name.setText(task.getName());

            setupListener(th.buttonHalf, position);
            setupListener(th.buttonOne, position);
            setupListener(th.buttonTwo, position);
            setupListener(th.buttonFive, position);
            setupListener(th.buttonTen, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Task> mData) {
        this.mData = mData;

        //TODO change only the ones modified or inserted
        notifyDataSetChanged();
    }

    private void setupListener(Button button, int pos) {
        button.setOnClickListener(mOnEstimationClickListener);
        button.setTag(pos);
    }

    /**
     * Holder for task that are already estimated
     */
    class EstimatedHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView estimation;  //TODO clickable to see which amount each one has estimated

        public EstimatedHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.estimated_username);
            estimation = itemView.findViewById(R.id.estimation);

        }
    }

    /**
     * Task which estimation is not complete
     */
    class TaskHolder extends RecyclerView.ViewHolder {

        TextView name;
        HorizontalScrollView estimationSelector;
        Button buttonHalf;
        Button buttonOne;
        Button buttonTwo;
        Button buttonFive;
        Button buttonTen;

        public TaskHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (estimationSelector.getVisibility() == View.GONE) {
                        estimationSelector.setVisibility(View.VISIBLE);
                    } else {
                        estimationSelector.setVisibility(View.GONE);
                    }
                }
            });

            estimationSelector = itemView.findViewById(R.id.estimation_container);
            buttonHalf = itemView.findViewById(R.id.estimation_half);
            buttonOne = itemView.findViewById(R.id.estimation_one);
            buttonTwo = itemView.findViewById(R.id.estimation_two);
            buttonFive = itemView.findViewById(R.id.estimation_five);
            buttonTen = itemView.findViewById(R.id.estimation_ten);
        }
    }
}
