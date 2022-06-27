package com.example.my_module_wallet.features.module.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.module.model.Module;

public class ModuleListAdapter extends ListAdapter<Module, ModuleListAdapter.ViewHolder> {
    private ModuleItemClickListener itemClickListener;

    ModuleListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Module> DIFF_CALLBACK = new DiffUtil.ItemCallback<Module>() {
        @Override
        public boolean areItemsTheSame(Module oldItem, Module newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(Module oldItem, Module newItem) {
            return oldItem.getModuleName().equals(newItem.getModuleName()) &&
                    oldItem.getModuleDescription().equals(newItem.getModuleDescription());
        }
    };

    @NonNull
    @Override
    public ModuleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.module_list_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleListAdapter.ViewHolder holder, int position) {
        Module module = getItem(position);
        holder.displayModuleSemesterTextView.setText(module.getModuleSemester());
        holder.displayModuleCodeTextView.setText(module.getModuleCode());
        holder.displayModuleNameTextView.setText(module.getModuleName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displayModuleNameTextView;
        public TextView displayModuleCodeTextView;
        public TextView displayModuleSemesterTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            displayModuleNameTextView = (TextView) itemView.findViewById(R.id.txtDisplayModuleName);
            displayModuleCodeTextView = (TextView) itemView.findViewById(R.id.txtDisplayModuleCode);
            displayModuleSemesterTextView = (TextView) itemView.findViewById(R.id.txtDisplayModuleSemester);

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

    public void setItemClickListener(ModuleItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ModuleItemClickListener {
        void onItemClicked(Module module);
    }

}
