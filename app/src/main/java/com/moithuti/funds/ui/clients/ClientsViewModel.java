package com.moithuti.funds.ui.clients;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.PaymentEntity;
import com.moithuti.funds.data.repository.ClientRepository;
import com.moithuti.funds.data.repository.LoanRepository;
import com.moithuti.funds.data.repository.PaymentRepository;
import com.moithuti.funds.ui.common.UiUtils;
import com.moithuti.funds.util.Constants;

import java.util.List;

/**
 * Clients ViewModel - ViewModel for Clients Fragment
 * Manages client data, search, filtering, and client operations
 */
public class ClientsViewModel extends AndroidViewModel {

    private final ClientRepository clientRepository;
    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;
    
    // LiveData for client data
    private final LiveData<List<ClientEntity>> allClientsLiveData;
    private final MutableLiveData<List<ClientEntity>> filteredClientsLiveData = new MutableLiveData<>();
    private final MutableLiveData<ClientEntity> selectedClientLiveData = new MutableLiveData<>();
    
    // LiveData for UI state
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> searchQueryLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> selectedStatusFilterLiveData = new MutableLiveData<>();
    
    // Constructor
    public ClientsViewModel(Application application) {
        super(application);
        
        this.clientRepository = ClientRepository.getInstance(application);
        this.loanRepository = LoanRepository.getInstance(application);
        this.paymentRepository = PaymentRepository.getInstance(application);
        
        // Get all clients LiveData
        this.allClientsLiveData = clientRepository.getAllClientsLive();
        
        // Initialize with all clients
        filteredClientsLiveData.setValue(null); // Will be set by filter logic
        
        // Initialize search query
        searchQueryLiveData.setValue("");
        
        // Initialize status filter
        selectedStatusFilterLiveData.setValue("All");
    }

    /**
     * Get all clients LiveData
     */
    public LiveData<List<ClientEntity>> getAllClientsLiveData() {
        return allClientsLiveData;
    }

    /**
     * Get filtered clients LiveData
     */
    public LiveData<List<ClientEntity>> getFilteredClientsLiveData() {
        return filteredClientsLiveData;
    }

    /**
     * Get selected client LiveData
     */
    public LiveData<ClientEntity> getSelectedClientLiveData() {
        return selectedClientLiveData;
    }

    /**
     * Get loading state LiveData
     */
    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    /**
     * Get error message LiveData
     */
    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    /**
     * Get success message LiveData
     */
    public LiveData<String> getSuccessMessageLiveData() {
        return successMessageLiveData;
    }

    /**
     * Get search query LiveData
     */
    public LiveData<String> getSearchQueryLiveData() {
        return searchQueryLiveData;
    }

    /**
     * Get selected status filter LiveData
     */
    public LiveData<String> getSelectedStatusFilterLiveData() {
        return selectedStatusFilterLiveData;
    }

    /**
     * Set search query and apply filters
     */
    public void setSearchQuery(String query) {
        searchQueryLiveData.setValue(query);
        applyFilters();
    }

    /**
     * Set status filter and apply filters
     */
    public void setStatusFilter(String status) {
        selectedStatusFilterLiveData.setValue(status);
        applyFilters();
    }

    /**
     * Apply search and status filters
     */
    private void applyFilters() {
        List<ClientEntity> allClients = allClientsLiveData.getValue();
        if (allClients == null) {
            filteredClientsLiveData.setValue(null);
            return;
        }

        String searchQuery = searchQueryLiveData.getValue();
        String statusFilter = selectedStatusFilterLiveData.getValue();

        // Apply filters in background thread
        new Thread(() -> {
            try {
                List<ClientEntity> filteredList = allClients;

                // Apply status filter
                if (statusFilter != null && !"All".equals(statusFilter)) {
                    filteredList = filterByStatus(filteredList, statusFilter);
                }

                // Apply search filter
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    filteredList = filterBySearch(filteredList, searchQuery.trim());
                }

                // Post result to main thread
                List<ClientEntity> finalFilteredList = filteredList;
                UiUtils.runOnUiThread(() -> {
                    filteredClientsLiveData.setValue(finalFilteredList);
                });

            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error filtering clients: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Filter clients by status
     */
    private List<ClientEntity> filterByStatus(List<ClientEntity> clients, String status) {
        List<ClientEntity> filtered = new java.util.ArrayList<>();
        for (ClientEntity client : clients) {
            if (status.equals(client.getStatus())) {
                filtered.add(client);
            }
        }
        return filtered;
    }

    /**
     * Filter clients by search query
     */
    private List<ClientEntity> filterBySearch(List<ClientEntity> clients, String query) {
        List<ClientEntity> filtered = new java.util.ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (ClientEntity client : clients) {
            // Search in name, phone, and notes
            if ((client.getName() != null && client.getName().toLowerCase().contains(lowerQuery)) ||
                (client.getPhone() != null && client.getPhone().toLowerCase().contains(lowerQuery)) ||
                (client.getNotes() != null && client.getNotes().toLowerCase().contains(lowerQuery))) {
                filtered.add(client);
            }
        }
        return filtered;
    }

    /**
     * Select a client
     */
    public void selectClient(ClientEntity client) {
        selectedClientLiveData.setValue(client);
    }

    /**
     * Clear selected client
     */
    public void clearSelectedClient() {
        selectedClientLiveData.setValue(null);
    }

    /**
     * Delete client
     */
    public void deleteClient(String clientId) {
        isLoadingLiveData.setValue(true);
        errorMessageLiveData.setValue(null);

        try {
            // Get client details before deletion for confirmation
            ClientEntity client = clientRepository.getClientById(clientId);
            if (client == null) {
                errorMessageLiveData.setValue("Client not found");
                isLoadingLiveData.setValue(false);
                return;
            }

            // Delete client (soft delete)
            clientRepository.deleteClient(clientId);

            successMessageLiveData.setValue("Client deleted successfully");
            isLoadingLiveData.setValue(false);

            // Clear selected client if it was the deleted one
            ClientEntity selectedClient = selectedClientLiveData.getValue();
            if (selectedClient != null && selectedClient.getUuid().equals(clientId)) {
                clearSelectedClient();
            }

        } catch (Exception e) {
            errorMessageLiveData.setValue("Error deleting client: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Update client status
     */
    public void updateClientStatus(String clientId, String newStatus) {
        isLoadingLiveData.setValue(true);
        errorMessageLiveData.setValue(null);

        try {
            // Validate status
            if (!isValidStatus(newStatus)) {
                errorMessageLiveData.setValue("Invalid status");
                isLoadingLiveData.setValue(false);
                return;
            }

            // Update status
            clientRepository.updateClientStatus(clientId, newStatus);

            successMessageLiveData.setValue("Client status updated successfully");
            isLoadingLiveData.setValue(false);

            // Update selected client if it's the one being updated
            ClientEntity selectedClient = selectedClientLiveData.getValue();
            if (selectedClient != null && selectedClient.getUuid().equals(clientId)) {
                selectedClient.setStatus(newStatus);
                selectedClientLiveData.setValue(selectedClient);
            }

        } catch (Exception e) {
            errorMessageLiveData.setValue("Error updating client status: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Get client loans
     */
    public LiveData<List<LoanEntity>> getClientLoans(String clientId) {
        return loanRepository.getLoansByClientLive(clientId);
    }

    /**
     * Get client total loan amount
     */
    public void getClientTotalLoaned(String clientId) {
        new Thread(() -> {
            try {
                List<LoanEntity> loans = loanRepository.getLoansByClient(clientId);
                double totalLoaned = 0.0;

                if (loans != null) {
                    for (LoanEntity loan : loans) {
                        totalLoaned += loan.getAmount();
                    }
                }

                // Post result (could be exposed via LiveData if needed)
                final double finalTotalLoaned = totalLoaned;
                UiUtils.runOnUiThread(() -> {
                    // For now, just log the result
                    android.util.Log.d("ClientsViewModel", "Client total loaned: " + finalTotalLoaned);
                });

            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error calculating total loaned: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Get client total paid amount
     */
    public void getClientTotalPaid(String clientId) {
        new Thread(() -> {
            try {
                List<LoanEntity> loans = loanRepository.getLoansByClient(clientId);
                double totalPaid = 0.0;

                if (loans != null) {
                    for (LoanEntity loan : loans) {
                        double loanPaid = paymentRepository.getTotalPaidForLoan(loan.getUuid());
                        totalPaid += loanPaid;
                    }
                }

                // Post result
                final double finalTotalPaid = totalPaid;
                UiUtils.runOnUiThread(() -> {
                    android.util.Log.d("ClientsViewModel", "Client total paid: " + finalTotalPaid);
                });

            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error calculating total paid: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Get client statistics
     */
    public void getClientStatistics(String clientId) {
        new Thread(() -> {
            try {
                List<LoanEntity> loans = loanRepository.getLoansByClient(clientId);
                
                int totalLoans = loans != null ? loans.size() : 0;
                int paidLoans = 0;
                int overdueLoans = 0;
                double totalAmount = 0.0;
                double totalPaid = 0.0;

                if (loans != null) {
                    for (LoanEntity loan : loans) {
                        totalAmount += loan.getAmount();
                        double loanPaid = paymentRepository.getTotalPaidForLoan(loan.getUuid());
                        totalPaid += loanPaid;

                        if (Constants.LOAN_STATUS_PAID.equals(loan.getStatus())) {
                            paidLoans++;
                        } else if (Constants.LOAN_STATUS_OVERDUE.equals(loan.getStatus())) {
                            overdueLoans++;
                        }
                    }
                }

                // Post results
                final int finalTotalLoans = totalLoans;
                final int finalPaidLoans = paidLoans;
                final int finalOverdueLoans = overdueLoans;
                final double finalTotalAmount = totalAmount;
                final double finalTotalPaid = totalPaid;
                UiUtils.runOnUiThread(() -> {
                    // Update UI with statistics (could be exposed via LiveData)
                    android.util.Log.d("ClientsViewModel", "Client stats - Loans: " + finalTotalLoans + 
                            ", Paid: " + finalPaidLoans + ", Overdue: " + finalOverdueLoans + 
                            ", Total: " + finalTotalAmount + ", Paid: " + finalTotalPaid);
                });

            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error calculating client statistics: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Refresh client data
     */
    public void refreshClients() {
        // The LiveData will automatically update when database changes
        // Just apply current filters to ensure UI is updated
        applyFilters();
    }

    /**
     * Get all available statuses
     */
    public String[] getAvailableStatuses() {
        return new String[]{
            "All",
            Constants.CLIENT_STATUS_PAID,
            Constants.CLIENT_STATUS_PARTIAL,
            Constants.CLIENT_STATUS_OVERDUE,
            Constants.CLIENT_STATUS_BLACKLISTED,
            Constants.CLIENT_STATUS_OWING
        };
    }

    /**
     * Validate status
     */
    private boolean isValidStatus(String status) {
        return status != null && (
            Constants.CLIENT_STATUS_PAID.equals(status) ||
            Constants.CLIENT_STATUS_PARTIAL.equals(status) ||
            Constants.CLIENT_STATUS_OVERDUE.equals(status) ||
            Constants.CLIENT_STATUS_BLACKLISTED.equals(status) ||
            Constants.CLIENT_STATUS_OWING.equals(status)
        );
    }

    /**
     * Get client count by status
     */
    public void getClientCountByStatus() {
        new Thread(() -> {
            try {
                int totalClients = clientRepository.getClientCount();
                int paidClients = clientRepository.getClientCountByStatus(Constants.CLIENT_STATUS_PAID);
                int partialClients = clientRepository.getClientCountByStatus(Constants.CLIENT_STATUS_PARTIAL);
                int overdueClients = clientRepository.getClientCountByStatus(Constants.CLIENT_STATUS_OVERDUE);
                int blacklistedClients = clientRepository.getClientCountByStatus(Constants.CLIENT_STATUS_BLACKLISTED);
                int owingClients = clientRepository.getClientCountByStatus(Constants.CLIENT_STATUS_OWING);

                // Post results
                UiUtils.runOnUiThread(() -> {
                    android.util.Log.d("ClientsViewModel", "Client counts - Total: " + totalClients +
                            ", Paid: " + paidClients + ", Partial: " + partialClients +
                            ", Overdue: " + overdueClients + ", Blacklisted: " + blacklistedClients +
                            ", Owing: " + owingClients);
                });

            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error getting client counts: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        errorMessageLiveData.setValue(null);
    }

    /**
     * Clear success message
     */
    public void clearSuccessMessage() {
        successMessageLiveData.setValue(null);
    }

    /**
     * Initialize the ViewModel
     */
    public void initialize() {
        // Load initial data
        refreshClients();
        
        // Get client counts
        getClientCountByStatus();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cleanup resources if needed
    }
}
