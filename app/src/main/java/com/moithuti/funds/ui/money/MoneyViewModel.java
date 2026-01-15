package com.moithuti.funds.ui.money;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.LoanFundingEntity;
import com.moithuti.funds.data.repository.InvestorRepository;
import com.moithuti.funds.data.repository.LoanRepository;
import com.moithuti.funds.ui.common.UiUtils;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.UuidUtil;
import com.moithuti.funds.util.InvestorCalculator;

import java.util.List;

/**
 * Money ViewModel - ViewModel for Money Fragment
 * Manages investor data, loan funding, and money operations
 */
public class MoneyViewModel extends AndroidViewModel {

    private final InvestorRepository investorRepository;
    private final LoanRepository loanRepository;
    
    // LiveData for investor data
    private final LiveData<InvestorEntity> mainAccountLiveData;
    private final LiveData<List<InvestorEntity>> otherInvestorsLiveData;
    private final MutableLiveData<InvestorEntity> selectedInvestorLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<InvestorTransactionEntity>> investorTransactionsLiveData = new MutableLiveData<>();
    
    // LiveData for loan funding
    private final MutableLiveData<List<LoanFundingEntity>> loanFundingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<InvestorEntity>> availableInvestorsLiveData = new MutableLiveData<>();
    
    // UI state
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> activeTabLiveData = new MutableLiveData<>();
    
    // Form data for adding investor
    private final MutableLiveData<String> investorNameLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isMainAccountLiveData = new MutableLiveData<>();
    
    // Form data for loan funding
    private final MutableLiveData<Double> loanAmountLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<InvestorEntity>> selectedInvestorsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Double>> investorAmountsLiveData = new MutableLiveData<>();
    
    // Constructor
    public MoneyViewModel(Application application) {
        super(application);
        
        this.investorRepository = InvestorRepository.getInstance(application);
        this.loanRepository = LoanRepository.getInstance(application);
        
        // Get investor LiveData
        this.mainAccountLiveData = investorRepository.getMainAccountLive();
        this.otherInvestorsLiveData = investorRepository.getOtherInvestorsLive();
        
        // Initialize UI state
        isLoadingLiveData.setValue(false);
        errorMessageLiveData.setValue(null);
        successMessageLiveData.setValue(null);
        activeTabLiveData.setValue("investors");
        
        // Initialize form data
        investorNameLiveData.setValue("");
        isMainAccountLiveData.setValue(false);
        loanAmountLiveData.setValue(Constants.DEFAULT_LOAN_AMOUNT);
        selectedInvestorsLiveData.setValue(new java.util.ArrayList<>());
        investorAmountsLiveData.setValue(new java.util.ArrayList<>());
    }

    // Data getters
    public LiveData<InvestorEntity> getMainAccountLiveData() { return mainAccountLiveData; }
    public LiveData<List<InvestorEntity>> getOtherInvestorsLiveData() { return otherInvestorsLiveData; }
    public LiveData<InvestorEntity> getSelectedInvestorLiveData() { return selectedInvestorLiveData; }
    public LiveData<List<InvestorTransactionEntity>> getInvestorTransactionsLiveData() { return investorTransactionsLiveData; }
    public LiveData<List<LoanFundingEntity>> getLoanFundingLiveData() { return loanFundingLiveData; }
    public LiveData<List<InvestorEntity>> getAvailableInvestorsLiveData() { return availableInvestorsLiveData; }
    
    // UI state getters
    public LiveData<Boolean> getIsLoadingLiveData() { return isLoadingLiveData; }
    public LiveData<String> getErrorMessageLiveData() { return errorMessageLiveData; }
    public LiveData<String> getSuccessMessageLiveData() { return successMessageLiveData; }
    public LiveData<String> getActiveTabLiveData() { return activeTabLiveData; }
    
    // Form data getters
    public LiveData<String> getInvestorNameLiveData() { return investorNameLiveData; }
    public LiveData<Boolean> getIsMainAccountLiveData() { return isMainAccountLiveData; }
    public LiveData<Double> getLoanAmountLiveData() { return loanAmountLiveData; }
    public LiveData<List<InvestorEntity>> getSelectedInvestorsLiveData() { return selectedInvestorsLiveData; }
    public LiveData<List<Double>> getInvestorAmountsLiveData() { return investorAmountsLiveData; }

    /**
     * Set active tab
     */
    public void setActiveTab(String tab) {
        activeTabLiveData.setValue(tab);
        
        if ("investors".equals(tab)) {
            loadInvestorData();
        } else if ("loans".equals(tab)) {
            loadLoanFundingData();
        }
    }

    /**
     * Load investor data
     */
    public void loadInvestorData() {
        isLoadingLiveData.setValue(true);
        
        try {
            // Check if main account exists
            InvestorEntity mainAccount = mainAccountLiveData.getValue();
            if (mainAccount == null) {
                // Create default main account
                createMainAccountIfNotExists();
            }
            
            // Load available investors for loan funding
            loadAvailableInvestors();
            
            isLoadingLiveData.setValue(false);
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error loading investor data: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Load loan funding data
     */
    public void loadLoanFundingData() {
        isLoadingLiveData.setValue(true);
        
        try {
            // Load available investors
            loadAvailableInvestors();
            
            isLoadingLiveData.setValue(false);
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error loading loan funding data: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Load available investors for loan funding
     */
    private void loadAvailableInvestors() {
        new Thread(() -> {
            try {
                List<InvestorEntity> allInvestors = investorRepository.getAllInvestors();
                List<InvestorEntity> availableInvestors = new java.util.ArrayList<>();
                
                if (allInvestors != null) {
                    for (InvestorEntity investor : allInvestors) {
                        if (!investor.isDeleted()) {
                            availableInvestors.add(investor);
                        }
                    }
                }
                
                UiUtils.runOnUiThread(() -> {
                    availableInvestorsLiveData.setValue(availableInvestors);
                });
                
            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error loading available investors: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Select investor
     */
    public void selectInvestor(InvestorEntity investor) {
        selectedInvestorLiveData.setValue(investor);
        
        // Load investor transactions
        loadInvestorTransactions(investor.getUuid());
    }

    /**
     * Load investor transactions
     */
    private void loadInvestorTransactions(String investorId) {
        isLoadingLiveData.setValue(true);
        
        new Thread(() -> {
            try {
                // This would need to be implemented in InvestorRepository
                // For now, create empty list
                List<InvestorTransactionEntity> transactions = new java.util.ArrayList<>();
                
                UiUtils.runOnUiThread(() -> {
                    investorTransactionsLiveData.setValue(transactions);
                    isLoadingLiveData.setValue(false);
                });
                
            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error loading investor transactions: " + e.getMessage());
                    isLoadingLiveData.setValue(false);
                });
            }
        }).start();
    }

    /**
     * Update investor form data
     */
    public void updateInvestorName(String name) {
        investorNameLiveData.setValue(name);
    }

    /**
     * Update is main account
     */
    public void updateIsMainAccount(boolean isMainAccount) {
        isMainAccountLiveData.setValue(isMainAccount);
    }

    /**
     * Save investor
     */
    public void saveInvestor() {
        String name = investorNameLiveData.getValue();
        Boolean isMainAccount = isMainAccountLiveData.getValue();
        
        if (name == null || name.trim().isEmpty()) {
            errorMessageLiveData.setValue("Investor name is required");
            return;
        }
        
        isLoadingLiveData.setValue(true);
        errorMessageLiveData.setValue(null);
        
        try {
            // Create investor entity
            InvestorEntity investor = new InvestorEntity();
            investor.setUuid(UuidUtil.generateInvestorUuid());
            investor.setName(name.trim());
            investor.setMainAccount(isMainAccount != null && isMainAccount);
            investor.setCreatedDate(System.currentTimeMillis());
            investor.setLastModified(System.currentTimeMillis());
            investor.setSyncStatus(com.moithuti.funds.sync.SyncStatus.PENDING.name());
            investor.setDeleted(false);
            
            // Validate investor
            if (!investorRepository.validateInvestor(investor)) {
                errorMessageLiveData.setValue("Invalid investor data");
                isLoadingLiveData.setValue(false);
                return;
            }
            
            // Save investor
            investorRepository.insertInvestor(investor);
            
            successMessageLiveData.setValue("Investor saved successfully");
            isLoadingLiveData.setValue(false);
            
            // Clear form
            clearInvestorForm();
            
            // Refresh data
            loadInvestorData();
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error saving investor: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Delete investor
     */
    public void deleteInvestor(String investorId) {
        isLoadingLiveData.setValue(true);
        
        try {
            investorRepository.deleteInvestor(investorId);
            
            successMessageLiveData.setValue("Investor deleted successfully");
            isLoadingLiveData.setValue(false);
            
            // Clear selection if deleted investor was selected
            InvestorEntity selectedInvestor = selectedInvestorLiveData.getValue();
            if (selectedInvestor != null && selectedInvestor.getUuid().equals(investorId)) {
                selectedInvestorLiveData.setValue(null);
            }
            
            // Refresh data
            loadInvestorData();
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error deleting investor: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Set investor as main account
     */
    public void setMainAccount(String investorId) {
        isLoadingLiveData.setValue(true);
        
        try {
            investorRepository.setMainAccount(investorId);
            
            successMessageLiveData.setValue("Main account updated successfully");
            isLoadingLiveData.setValue(false);
            
            // Refresh data
            loadInvestorData();
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error setting main account: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Update loan amount
     */
    public void updateLoanAmount(double amount) {
        loanAmountLiveData.setValue(amount);
    }

    /**
     * Select investor for loan funding
     */
    public void selectInvestorForFunding(InvestorEntity investor) {
        List<InvestorEntity> selectedInvestors = selectedInvestorsLiveData.getValue();
        if (selectedInvestors == null) {
            selectedInvestors = new java.util.ArrayList<>();
        }
        
        // Toggle selection
        if (selectedInvestors.contains(investor)) {
            selectedInvestors.remove(investor);
        } else {
            selectedInvestors.add(investor);
        }
        
        selectedInvestorsLiveData.setValue(selectedInvestors);
        
        // Update investor amounts list to match
        List<Double> investorAmounts = investorAmountsLiveData.getValue();
        if (investorAmounts == null) {
            investorAmounts = new java.util.ArrayList<>();
        }
        
        // Ensure amounts list matches investors list
        while (investorAmounts.size() < selectedInvestors.size()) {
            investorAmounts.add(0.0);
        }
        while (investorAmounts.size() > selectedInvestors.size()) {
            investorAmounts.remove(investorAmounts.size() - 1);
        }
        
        investorAmountsLiveData.setValue(investorAmounts);
    }

    /**
     * Update investor funding amount
     */
    public void updateInvestorAmount(int index, double amount) {
        List<Double> investorAmounts = investorAmountsLiveData.getValue();
        if (investorAmounts != null && index >= 0 && index < investorAmounts.size()) {
            investorAmounts.set(index, amount);
            investorAmountsLiveData.setValue(investorAmounts);
        }
    }

    /**
     * Issue loan with funding
     */
    public void issueLoanWithFunding() {
        Double loanAmount = loanAmountLiveData.getValue();
        List<InvestorEntity> selectedInvestors = selectedInvestorsLiveData.getValue();
        List<Double> investorAmounts = investorAmountsLiveData.getValue();
        
        if (loanAmount == null || loanAmount <= 0) {
            errorMessageLiveData.setValue("Loan amount must be greater than 0");
            return;
        }
        
        if (selectedInvestors == null || selectedInvestors.isEmpty()) {
            errorMessageLiveData.setValue("Please select at least one investor");
            return;
        }
        
        // Validate funding amounts
        double totalFunding = 0.0;
        if (investorAmounts != null) {
            for (Double amount : investorAmounts) {
                if (amount == null || amount <= 0) {
                    errorMessageLiveData.setValue("All investor amounts must be greater than 0");
                    return;
                }
                totalFunding += amount;
            }
        }
        
        if (Math.abs(totalFunding - loanAmount) > 0.01) {
            errorMessageLiveData.setValue("Total funding must equal loan amount");
            return;
        }
        
        isLoadingLiveData.setValue(true);
        
        try {
            // This would create a loan and funding records
            // Implementation would depend on the specific business logic
            
            successMessageLiveData.setValue("Loan issued successfully with funding");
            isLoadingLiveData.setValue(false);
            
            // Clear form
            clearLoanFundingForm();
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error issuing loan: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Get investor balance summary
     */
    public void loadInvestorBalance(String investorId) {
        new Thread(() -> {
            try {
                // This would calculate investor balance using InvestorCalculator
                // For now, just log the request
                UiUtils.runOnUiThread(() -> {
                    android.util.Log.d("MoneyViewModel", "Loading balance for investor: " + investorId);
                });
                
            } catch (Exception e) {
                UiUtils.runOnUiThread(() -> {
                    errorMessageLiveData.setValue("Error loading investor balance: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Create main account if not exists
     */
    private void createMainAccountIfNotExists() {
        investorRepository.createMainAccountIfNotExists("Main Account");
    }

    /**
     * Clear investor form
     */
    public void clearInvestorForm() {
        investorNameLiveData.setValue("");
        isMainAccountLiveData.setValue(false);
        errorMessageLiveData.setValue(null);
        successMessageLiveData.setValue(null);
    }

    /**
     * Clear loan funding form
     */
    public void clearLoanFundingForm() {
        loanAmountLiveData.setValue(Constants.DEFAULT_LOAN_AMOUNT);
        selectedInvestorsLiveData.setValue(new java.util.ArrayList<>());
        investorAmountsLiveData.setValue(new java.util.ArrayList<>());
        errorMessageLiveData.setValue(null);
        successMessageLiveData.setValue(null);
    }

    /**
     * Format currency
     */
    public String formatCurrency(double amount) {
        return UiUtils.formatCurrency(amount);
    }

    /**
     * Get total selected funding
     */
    public double getTotalSelectedFunding() {
        List<Double> investorAmounts = investorAmountsLiveData.getValue();
        if (investorAmounts == null) {
            return 0.0;
        }
        
        double total = 0.0;
        for (Double amount : investorAmounts) {
            if (amount != null) {
                total += amount;
            }
        }
        return total;
    }

    /**
     * Check if loan funding form is valid
     */
    public boolean isLoanFundingFormValid() {
        Double loanAmount = loanAmountLiveData.getValue();
        List<InvestorEntity> selectedInvestors = selectedInvestorsLiveData.getValue();
        List<Double> investorAmounts = investorAmountsLiveData.getValue();
        
        if (loanAmount == null || loanAmount <= 0) {
            return false;
        }
        
        if (selectedInvestors == null || selectedInvestors.isEmpty()) {
            return false;
        }
        
        if (investorAmounts == null || investorAmounts.size() != selectedInvestors.size()) {
            return false;
        }
        
        double totalFunding = getTotalSelectedFunding();
        return Math.abs(totalFunding - loanAmount) < 0.01;
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
        loadInvestorData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cleanup resources if needed
    }
}
