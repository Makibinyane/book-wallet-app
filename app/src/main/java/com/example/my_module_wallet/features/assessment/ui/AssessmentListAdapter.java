package com.example.my_module_wallet.features.assessment.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.assessment.model.Assessment;

public class AssessmentListAdapter extends ListAdapter<Assessment, AssessmentListAdapter.ViewHolder> {
    private AssessmentItemClickListener itemClickListener;

    AssessmentListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Assessment> DIFF_CALLBACK = new DiffUtil.ItemCallback<Assessment>() {
        @Override
        public boolean areItemsTheSame(Assessment oldItem, Assessment newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(Assessment oldItem, Assessment newItem) {
            return oldItem.getAssessmentName().equals(newItem.getAssessmentName()) &&
                    oldItem.getAssessmentDescription().equals(newItem.getAssessmentDescription());
        }
    };

    @NonNull
    @Override
    public AssessmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentListAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListAdapter.ViewHolder holder, int position) {
        Assessment assessment = getItem(position);
        holder.displayAssessmentNameTextView.setText(assessment.getAssessmentName());
        holder.displayAssessmentDueDateTextView.setText(assessment.getAssessmentDueDate());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displayAssessmentNameTextView;
        public TextView displayAssessmentDueDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            displayAssessmentDueDateTextView = (TextView) itemView.findViewById(R.id.txtDisplayAssessmentDueDate);
            displayAssessmentNameTextView = (TextView) itemView.findViewById(R.id.txtDisplayAssessmentName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClicked(getItem(position));
                    }
                }
            });
        }
    }

    public void setItemClickListener(AssessmentItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface AssessmentItemClickListener {
        void onItemClicked(Assessment assessment);
    }
}

