package com.example.my_module_wallet.features.module.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.module.model.Module;
import com.example.my_module_wallet.features.module.viewmodel.ModuleViewModel;
import com.example.my_module_wallet.features.user.ui.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ModuleListFragment extends Fragment implements ModuleListAdapter.ModuleItemClickListener {
    private ModuleViewModel viewModel;
    private ModuleListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        setHasOptionsMenu(true);
        viewModel.getModules();
        return inflater.inflate(R.layout.fragment_module_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();

        FloatingActionButton button = requireView().findViewById(R.id.btnAddModule);
        SearchView searchView = requireView().findViewById(R.id.module_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_moduleListFragment_to_addModuleFragment);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.modules_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.actionLogout) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void filter(String text) {
        if (!text.isEmpty()) {
            ArrayList<Module> filteredlist = new ArrayList<>();
            viewModel.allModulesMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
                @Override
                public void onChanged(List<Module> modules) {
                    for (Module item : modules) {
                        if (item.getModuleName().toLowerCase().contains(text.toLowerCase())) {
                            filteredlist.add(item);
                        }
                    }
                    adapter.submitList(filteredlist);
                }
            });
        }
    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = requireView().findViewById(R.id.modulesRecyclerView);
        adapter = new ModuleListAdapter();

        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);

        viewModel.allModulesMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                adapter.submitList(modules);
            }
        });

    }

    @Override
    public void onItemClicked(Module module) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("module", module);
        Navigation.findNavController(requireView()).navigate(R.id.action_moduleListFragment_to_viewModuleDetailsFragment, bundle);
    }
}