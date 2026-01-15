package com.moithuti.funds.ui.addclient;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.LoanFundingEntity;
import com.moithuti.funds.data.repository.ClientRepository;
import com.moithuti.funds.data.repository.InvestorRepository;
import com.moithuti.funds.data.repository.LoanRepository;
import com.moithuti.funds.data.repository.ProfitTrackerRepository;
import com.moithuti.funds.ui.common.UiUtils;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.UuidUtil;

/**
 * Add Client ViewModel - ViewModel for Add Client Fragment
 * Manages client creation and optional loan issuance
 */
public class AddClientViewModel extends AndroidViewModel {

    private final ClientRepository clientRepository;
    private final LoanRepository loanRepository;
    private final InvestorRepository investorRepository;
    private final ProfitTrackerRepository profitTrackerRepository;
    
    // Form data
    private final MutableLiveData<String> clientNameLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> clientPhoneLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> clientNotesLiveData = new MutableLiveData<>();
    
    // Loan form data
    private final MutableLiveData<Boolean> includeLoanLiveData = new MutableLiveData<>();
    private final MutableLiveData<Double> loanAmountLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> loanDueDateLiveData = new MutableLiveData<>();
    
    // UI state
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> formValidLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();
    
    // Results
    private final MutableLiveData<ClientEntity> createdClientLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoanEntity> createdLoanLiveData = new MutableLiveData<>();
    
    // Constructor
    public AddClientViewModel(Application application) {
        super(application);
        
        this.clientRepository = ClientRepository.getInstance(application);
        this.loanRepository = LoanRepository.getInstance(application);
        this.investorRepository = InvestorRepository.getInstance(application);
        this.profitTrackerRepository = ProfitTrackerRepository.getInstance(application);
        
        // Initialize form data
        clientNameLiveData.setValue("");
        clientPhoneLiveData.setValue("");
        clientNotesLiveData.setValue("");
        includeLoanLiveData.setValue(false);
        loanAmountLiveData.setValue(Constants.DEFAULT_LOAN_AMOUNT);
        loanDueDateLiveData.setValue(System.currentTimeMillis() + (Constants.DEFAULT_LOAN_TERM_DAYS * 24L * 60 * 60 * 1000));
        
        // Initialize UI state
        isLoadingLiveData.setValue(false);
        errorMessageLiveData.setValue(null);
        successMessageLiveData.setValue(null);
        formValidLiveData.setValue(false);
    }

    // Form data getters
    public LiveData<String> getClientNameLiveData() { return clientNameLiveData; }
    public LiveData<String> getClientPhoneLiveData() { return clientPhoneLiveData; }
    public LiveData<String> getClientNotesLiveData() { return clientNotesLiveData; }
    public LiveData<Boolean> getIncludeLoanLiveData() { return includeLoanLiveData; }
    public LiveData<Double> getLoanAmountLiveData() { return loanAmountLiveData; }
    public LiveData<Long> getLoanDueDateLiveData() { return loanDueDateLiveData; }
    
    // UI state getters
    public LiveData<Boolean> getIsLoadingLiveData() { return isLoadingLiveData; }
    public LiveData<String> getErrorMessageLiveData() { return errorMessageLiveData; }
    public LiveData<String> getSuccessMessageLiveData() { return successMessageLiveData; }
    public LiveData<Boolean> getFormValidLiveData() { return formValidLiveData; }
    
    // Results getters
    public LiveData<ClientEntity> getCreatedClientLiveData() { return createdClientLiveData; }
    public LiveData<LoanEntity> getCreatedLoanLiveData() { return createdLoanLiveData; }

    /**
     * Update client name
     */
    public void updateClientName(String name) {
        clientNameLiveData.setValue(name);
        validateForm();
    }

    /**
     * Update client phone
     */
    public void updateClientPhone(String phone) {
        clientPhoneLiveData.setValue(phone);
        validateForm();
    }

    /**
     * Update client notes
     */
    public void updateClientNotes(String notes) {
        clientNotesLiveData.setValue(notes);
        validateForm();
    }

    /**
     * Update include loan option
     */
    public void updateIncludeLoan(boolean includeLoan) {
        includeLoanLiveData.setValue(includeLoan);
        validateForm();
    }

    /**
     * Update loan amount
     */
    public void updateLoanAmount(double amount) {
        loanAmountLiveData.setValue(amount);
        validateForm();
    }

    /**
     * Update loan due date
     */
    public void updateLoanDueDate(long dueDate) {
        loanDueDateLiveData.setValue(dueDate);
        validateForm();
    }

    /**
     * Validate the form
     */
    private void validateForm() {
        String name = clientNameLiveData.getValue();
        String phone = clientPhoneLiveData.getValue();
        boolean includeLoan = includeLoanLiveData.getValue() != null && includeLoanLiveData.getValue();
        Double loanAmount = loanAmountLiveData.getValue();
        Long loanDueDate = loanDueDateLiveData.getValue();

        boolean isValid = true;
        String errorMessage = null;

        // Validate client name
        if (name == null || name.trim().isEmpty()) {
            isValid = false;
            errorMessage = "Client name is required";
        } else if (name.trim().length() > Constants.MAX_CLIENT_NAME_LENGTH) {
            isValid = false;
            errorMessage = "Client name too long";
        }

        // Validate phone (optional but if provided, must be valid)
        if (isValid && phone != null && !phone.trim().isEmpty()) {
            if (phone.length() < Constants.MIN_PHONE_LENGTH || phone.length() > Constants.MAX_PHONE_LENGTH) {
                isValid = false;
                errorMessage = "Invalid phone number length";
            }
        }

        // Validate loan if included
        if (isValid && includeLoan) {
            if (loanAmount == null || loanAmount <= 0) {
                isValid = false;
                errorMessage = "Loan amount must be greater than 0";
            } else if (loanAmount < Constants.MIN_LOAN_AMOUNT || loanAmount > Constants.MAX_LOAN_AMOUNT) {
                isValid = false;
                errorMessage = "Loan amount out of allowed range";
            }

            if (loanDueDate == null || loanDueDate <= System.currentTimeMillis()) {
                isValid = false;
                errorMessage = "Due date must be in the future";
            }
        }

        formValidLiveData.setValue(isValid);
        if (errorMessage != null) {
            errorMessageLiveData.setValue(errorMessage);
        } else {
            errorMessageLiveData.setValue(null);
        }
    }

    /**
     * Create client only (without loan)
     */
    public void createClient(String name, String phone, String email, String address) {
        try {
            // Create client entity
            ClientEntity client = new ClientEntity();
            client.setUuid(UuidUtil.generateClientUuid());
            client.setName(name.trim());
            client.setPhone(phone.trim());
            client.setNotes(email != null ? email.trim() : ""); // Using notes field for email temporarily
            client.setStatus(Constants.CLIENT_STATUS_OWING);
            client.setCreatedDate(System.currentTimeMillis());
            client.setLastModified(System.currentTimeMillis());
            client.setSyncStatus(com.moithuti.funds.sync.SyncStatus.PENDING.name());
            client.setDeleted(false);

            // Validate client
            if (!clientRepository.validateClient(client)) {
                errorMessageLiveData.setValue("Invalid client data");
                successLiveData.setValue(false);
                return;
            }

            // Save client
            clientRepository.insertClient(client);
            createdClientLiveData.setValue(client);
            successLiveData.setValue(true);
            successMessageLiveData.setValue("Client saved successfully");
            
        } catch (Exception e) {
            Log.e("AddClientViewModel", "Error creating client", e);
            errorMessageLiveData.setValue("Failed to save client: " + e.getMessage());
            successLiveData.setValue(false);
        }
    }

    /**
     * Create client with loan
     */
    public void createClientWithLoan(String name, String phone, String email, String address, 
                                   double loanAmount, double interestRate, int loanTerm) {
        try {
            // First create the client
            createClient(name, phone, email, address);
            
            // Then create the loan if client creation was successful
            if (Boolean.TRUE.equals(successLiveData.getValue())) {
                ClientEntity client = createdClientLiveData.getValue();
                if (client != null) {
                    // Set loan data for the existing saveClient method to use
                    clientNameLiveData.setValue(name);
                    clientPhoneLiveData.setValue(phone);
                    clientNotesLiveData.setValue(email);
                    includeLoanLiveData.setValue(true);
                    loanAmountLiveData.setValue(loanAmount);
                    // Calculate due date from loan term (months)
                    long dueDate = System.currentTimeMillis() + (loanTerm * 30L * 24 * 60 * 60 * 1000);
                    loanDueDateLiveData.setValue(dueDate);
                    
                    createLoanForClient(client);
                }
            }
            
        } catch (Exception e) {
            Log.e("AddClientViewModel", "Error creating client with loan", e);
            errorMessageLiveData.setValue("Failed to save client and loan: " + e.getMessage());
            successLiveData.setValue(false);
        }
    }

    /**
     * Save client and optional loan
     */
    public void saveClient() {
        if (!Boolean.TRUE.equals(formValidLiveData.getValue())) {
            errorMessageLiveData.setValue("Please fix form errors before saving");
            return;
        }

        isLoadingLiveData.setValue(true);
        errorMessageLiveData.setValue(null);

        try {
            // Create client entity
            ClientEntity client = new ClientEntity();
            client.setUuid(UuidUtil.generateClientUuid());
            client.setName(clientNameLiveData.getValue().trim());
            client.setPhone(clientPhoneLiveData.getValue() != null ? clientPhoneLiveData.getValue().trim() : "");
            client.setNotes(clientNotesLiveData.getValue() != null ? clientNotesLiveData.getValue().trim() : "");
            client.setStatus(Constants.CLIENT_STATUS_OWING);
            client.setCreatedDate(System.currentTimeMillis());
            client.setLastModified(System.currentTimeMillis());
            client.setSyncStatus(com.moithuti.funds.sync.SyncStatus.PENDING.name());
            client.setDeleted(false);

            // Validate client
            if (!clientRepository.validateClient(client)) {
                errorMessageLiveData.setValue("Invalid client data");
                isLoadingLiveData.setValue(false);
                return;
            }

            // Save client
            clientRepository.insertClient(client);
            createdClientLiveData.setValue(client);

            // Create loan if requested
            Boolean includeLoan = includeLoanLiveData.getValue();
            if (includeLoan != null && includeLoan) {
                createLoanForClient(client);
            } else {
                // Complete without loan
                successMessageLiveData.setValue("Client saved successfully");
                successLiveData.setValue(true);
                isLoadingLiveData.setValue(false);
            }

        } catch (Exception e) {
            errorMessageLiveData.setValue("Error saving client: " + e.getMessage());
            successLiveData.setValue(false);
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Create loan for client with funding source
     */
    private void createLoanForClient(ClientEntity client) {
        try {
            // Create loan entity
            LoanEntity loan = new LoanEntity();
            loan.setUuid(UuidUtil.generateLoanUuid());
            loan.setClientId(client.getUuid());
            loan.setAmount(loanAmountLiveData.getValue());
            loan.setDateIssued(System.currentTimeMillis());
            loan.setDueDate(loanDueDateLiveData.getValue());
            loan.setStatus(Constants.LOAN_STATUS_OWING);
            loan.setLastModified(System.currentTimeMillis());
            loan.setSyncStatus(com.moithuti.funds.sync.SyncStatus.PENDING.name());
            loan.setDeleted(false);

            // Validate loan
            if (!loanRepository.validateLoan(loan)) {
                errorMessageLiveData.setValue("Invalid loan data");
                isLoadingLiveData.setValue(false);
                return;
            }

            // Save loan
            loanRepository.insertLoan(loan);
            createdLoanLiveData.setValue(loan);

            // Create loan funding from Main Account (default behavior)
            createLoanFunding(loan.getUuid(), null, loan.getAmount());

            // Update profit tracker for loan issuance
            updateProfitTrackerForLoan(loan.getAmount());

            // Update client status to partial since they now have a loan
            clientRepository.updateClientStatus(client.getUuid(), Constants.CLIENT_STATUS_PARTIAL);

            successMessageLiveData.setValue("Client and loan saved successfully");
            isLoadingLiveData.setValue(false);

        } catch (Exception e) {
            errorMessageLiveData.setValue("Error creating loan: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Create loan funding record
     */
    private void createLoanFunding(String loanId, String investorId, double amount) {
        try {
            LoanFundingEntity funding = new LoanFundingEntity();
            funding.setUuid(UuidUtil.generateLoanFundingUuid());
            funding.setLoanId(loanId);
            funding.setInvestorId(investorId); // null for Main Account
            funding.setAmount(amount);
            funding.setCreatedDate(System.currentTimeMillis());
            funding.setLastModified(System.currentTimeMillis());
            funding.setSyncStatus(com.moithuti.funds.sync.SyncStatus.PENDING.name());
            funding.setDeleted(false);

            loanRepository.insertLoanFunding(funding);
            Log.d("AddClientViewModel", "Created loan funding: " + funding.getUuid() + " amount: " + amount);

        } catch (Exception e) {
            Log.e("AddClientViewModel", "Error creating loan funding", e);
        }
    }

    /**
     * Clear form
     */
    public void clearForm() {
        clientNameLiveData.setValue("");
        clientPhoneLiveData.setValue("");
        clientNotesLiveData.setValue("");
        includeLoanLiveData.setValue(false);
        loanAmountLiveData.setValue(Constants.DEFAULT_LOAN_AMOUNT);
        loanDueDateLiveData.setValue(System.currentTimeMillis() + (Constants.DEFAULT_LOAN_TERM_DAYS * 24L * 60 * 60 * 1000));
        
        errorMessageLiveData.setValue(null);
        successMessageLiveData.setValue(null);
        createdClientLiveData.setValue(null);
        createdLoanLiveData.setValue(null);
        
        validateForm();
    }

    /**
     * Reset to initial state
     */
    public void reset() {
        clearForm();
        isLoadingLiveData.setValue(false);
    }

    /**
     * Get default loan amount
     */
    public double getDefaultLoanAmount() {
        return Constants.DEFAULT_LOAN_AMOUNT;
    }

    /**
     * Get minimum loan amount
     */
    public double getMinLoanAmount() {
        return Constants.MIN_LOAN_AMOUNT;
    }

    /**
     * Get maximum loan amount
     */
    public double getMaxLoanAmount() {
        return Constants.MAX_LOAN_AMOUNT;
    }

    /**
     * Get default loan term in days
     */
    public int getDefaultLoanTermDays() {
        return Constants.DEFAULT_LOAN_TERM_DAYS;
    }

    /**
     * Format currency amount
     */
    public String formatCurrency(double amount) {
        return UiUtils.formatCurrency(amount);
    }

    /**
     * Format date
     */
    public String formatDate(long timestamp) {
        return UiUtils.formatDate(timestamp);
    }

    /**
     * Get formatted due date text
     */
    public String getDueDateText() {
        Long dueDate = loanDueDateLiveData.getValue();
        if (dueDate != null) {
            return "Due: " + UiUtils.formatDate(dueDate);
        }
        return "";
    }

    /**
     * Get loan summary text
     */
    public String getLoanSummaryText() {
        Boolean includeLoan = includeLoanLiveData.getValue();
        Double amount = loanAmountLiveData.getValue();
        Long dueDate = loanDueDateLiveData.getValue();

        if (includeLoan != null && includeLoan && amount != null && dueDate != null) {
            return "Loan: " + formatCurrency(amount) + " due " + UiUtils.formatRelativeTime(dueDate);
        }
        return "No loan will be issued";
    }

    /**
     * Check if phone number is valid
     */
    public boolean isPhoneValid(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // Phone is optional
        }
        
        return phone.length() >= Constants.MIN_PHONE_LENGTH && 
               phone.length() <= Constants.MAX_PHONE_LENGTH &&
               phone.matches("^[+]?[0-9\\-\\(\\)\\s]+$");
    }

    /**
     * Get form completion percentage
     */
    public int getFormCompletionPercentage() {
        int completedFields = 0;
        int totalFields = 3; // name, phone, notes

        String name = clientNameLiveData.getValue();
        String phone = clientPhoneLiveData.getValue();
        String notes = clientNotesLiveData.getValue();

        if (name != null && !name.trim().isEmpty()) completedFields++;
        if (phone != null && !phone.trim().isEmpty()) completedFields++;
        if (notes != null && !notes.trim().isEmpty()) completedFields++;

        Boolean includeLoan = includeLoanLiveData.getValue();
        if (includeLoan != null && includeLoan) {
            totalFields += 2; // amount, due date
            
            Double amount = loanAmountLiveData.getValue();
            Long dueDate = loanDueDateLiveData.getValue();
            
            if (amount != null && amount > 0) completedFields++;
            if (dueDate != null && dueDate > System.currentTimeMillis()) completedFields++;
        }

        return totalFields > 0 ? (completedFields * 100) / totalFields : 0;
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
     * Get save success LiveData
     */
    public LiveData<Boolean> getSaveSuccess() {
        return successLiveData;
    }

    /**
     * Get save error LiveData
     */
    public LiveData<String> getSaveError() {
        return errorMessageLiveData;
    }

    /**
     * Update profit tracker for loan issuance
     */
    private void updateProfitTrackerForLoan(double loanAmount) {
        try {
            String currentMonth = ProfitTrackerRepository.getCurrentYearMonth();
            
            // Get existing profit tracker for current month or create new
            // For now, we'll update with the loan amount as monthly loans issued
            // The repository will handle the cumulative calculations
            profitTrackerRepository.updateMonthlyProfit(
                null, // Main Account (null investorId)
                currentMonth,
                loanAmount, // monthlyLoansIssued
                0.0, // monthlyRepaymentsReceived (no repayment yet)
                0.0  // monthlyInterest (no interest yet)
            );
            
            Log.d("AddClientViewModel", "Updated profit tracker for loan issuance: " + loanAmount);
            
        } catch (Exception e) {
            Log.e("AddClientViewModel", "Error updating profit tracker for loan", e);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cleanup resources if needed
    }
}
