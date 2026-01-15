package com.moithuti.funds.ui.clients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import com.moithuti.funds.R;
import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.ui.common.UiUtils;

/**
 * Clients Fragment - Searchable list of clients with filtering
 * Shows color-coded client cards and provides access to client profiles
 */
public class ClientsFragment extends Fragment {

    private ClientsViewModel viewModel;
    private ClientAdapter clientAdapter;

    // UI components
    private RecyclerView clientsRecyclerView;
    private ProgressBar loadingIndicator;
    private TextView emptyStateText;
    private TextInputEditText searchEditText;
    private MaterialButton filterButton;
    private TextView clientCountText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ClientsViewModel.class);
        
        // Initialize UI components
        initViews(view);
        
        // Setup RecyclerView
        setupRecyclerView();
        
        // Setup observers
        setupObservers();
        
        // Setup click listeners
        setupClickListeners(view);
        
        // Initialize ViewModel
        viewModel.initialize();
    }

    private void initViews(View view) {
        clientsRecyclerView = view.findViewById(R.id.clients_recycler_view);
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        emptyStateText = view.findViewById(R.id.empty_state_text);
        searchEditText = view.findViewById(R.id.search_clients);
        filterButton = view.findViewById(R.id.filter_status);
        clientCountText = view.findViewById(R.id.client_count);
    }

    private void setupRecyclerView() {
        clientAdapter = new ClientAdapter();
        clientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clientsRecyclerView.setAdapter(clientAdapter);
        
        // Set click listeners
        clientAdapter.setOnClientClickListener(client -> {
            // Navigate to client profile
            navigateToClientProfile(client);
        });
        
        clientAdapter.setOnClientLongClickListener(client -> {
            // Show client options (delete, change status, etc.)
            showClientOptions(client);
        });
    }

    private void setupObservers() {
        // Filtered clients observer
        viewModel.getFilteredClientsLiveData().observe(getViewLifecycleOwner(), clients -> {
            if (clients != null) {
                clientAdapter.updateClients(clients);
                updateEmptyState(clients.size());
                updateClientCount(clients.size());
            }
        });
        
        // Loading state observer
        viewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        
        // Error message observer
        viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                UiUtils.showSnackbar(getView(), errorMessage);
            }
        });
        
        // Success message observer
        viewModel.getSuccessMessageLiveData().observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null && !successMessage.trim().isEmpty()) {
                UiUtils.showSnackbar(getView(), successMessage);
            }
        });
        
        // Search query observer
        viewModel.getSearchQueryLiveData().observe(getViewLifecycleOwner(), query -> {
            // Update search EditText if needed
            if (searchEditText != null && !searchEditText.getText().toString().equals(query)) {
                searchEditText.setText(query);
            }
        });
    }

    private void setupClickListeners(View view) {
        // Search functionality
        if (searchEditText != null) {
            searchEditText.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    viewModel.setSearchQuery(s.toString());
                }

                @Override
                public void afterTextChanged(android.text.Editable s) {}
            });
        }
        
        // Filter button
        if (filterButton != null) {
            filterButton.setOnClickListener(v -> {
                showStatusFilterDialog();
            });
        }
    }

    private void updateEmptyState(int clientCount) {
        if (emptyStateText != null) {
            if (clientCount == 0) {
                emptyStateText.setVisibility(View.VISIBLE);
                emptyStateText.setText("No clients found");
            } else {
                emptyStateText.setVisibility(View.GONE);
            }
        }
    }

    private void updateClientCount(int count) {
        if (clientCountText != null) {
            clientCountText.setText(count + " clients");
        }
    }

    private void navigateToClientProfile(ClientEntity client) {
        if (client == null) return;
        
        // Navigate to client profile activity
        android.content.Intent intent = new android.content.Intent(getContext(), ClientProfileActivity.class);
        intent.putExtra("client_id", client.getUuid());
        startActivity(intent);
    }

    private void showClientOptions(ClientEntity client) {
        if (client == null) return;
        
        // Create options dialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Client Options")
                .setItems(new String[]{
                        "View Profile",
                        "Change Status",
                        "Delete Client"
                }, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            navigateToClientProfile(client);
                            break;
                        case 1:
                            showStatusChangeDialog(client);
                            break;
                        case 2:
                            showDeleteConfirmationDialog(client);
                            break;
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showStatusChangeDialog(ClientEntity client) {
        String[] statuses = viewModel.getAvailableStatuses();
        String currentStatus = client.getStatus();
        
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Change Status")
                .setItems(statuses, (dialog, which) -> {
                    String newStatus = statuses[which];
                    if (!newStatus.equals(currentStatus)) {
                        viewModel.updateClientStatus(client.getUuid(), newStatus);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmationDialog(ClientEntity client) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Delete Client")
                .setMessage("Are you sure you want to delete " + client.getName() + "? This will also delete all associated loans and payments.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteClient(client.getUuid());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showStatusFilterDialog() {
        String[] statuses = viewModel.getAvailableStatuses();
        
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Filter by Status")
                .setItems(statuses, (dialog, which) -> {
                    String selectedStatus = statuses[which];
                    viewModel.setStatusFilter(selectedStatus);
                    
                    // Update button text to show current filter
                    if (filterButton != null) {
                        filterButton.setText("Filter: " + selectedStatus);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when fragment becomes visible
        viewModel.refreshClients();
    }
}
