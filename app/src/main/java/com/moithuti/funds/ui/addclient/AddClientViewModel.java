package com.moithuti.funds.ui.addclient;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.repository.ClientRepository;
import com.moithuti.funds.data.repository.LoanRepository;
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
    
    // Results
    private final MutableLiveData<ClientEntity> createdClientLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoanEntity> createdLoanLiveData = new MutableLiveData<>();
    
    // Constructor
    public AddClientViewModel(Application application) {
        super(application);
        
        this.clientRepository = ClientRepository.getInstance(application);
        this.loanRepository = LoanRepository.getInstance(application);
        
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
                isLoadingLiveData.setValue(false);
            }

        } catch (Exception e) {
            errorMessageLiveData.setValue("Error saving client: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Create loan for client
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

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cleanup resources if needed
    }
}
