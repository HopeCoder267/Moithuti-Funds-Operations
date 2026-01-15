package com.moithuti.funds.data.remote.sheets;

import android.content.Context;
import android.util.Log;

import com.google.api.services.sheets.v4.model.*;
import com.moithuti.funds.data.local.entity.*;
import com.moithuti.funds.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Sheets Client - Low-level client for Google Sheets operations
 * Handles entity-specific CRUD operations with Google Sheets
 */
public class SheetsClient {

    private static final String TAG = "SheetsClient";
    
    private final GoogleSheetsService sheetsService;
    
    /**
     * Constructor
     * @param context application context
     */
    public SheetsClient(Context context) {
        this.sheetsService = new GoogleSheetsService(context);
    }

    /**
     * Set spreadsheet ID
     * @param spreadsheetId the Google Sheets spreadsheet ID
     */
    public void setSpreadsheetId(String spreadsheetId) {
        sheetsService.setSpreadsheetId(spreadsheetId);
    }

    /**
     * Check if client is ready
     * @return true if ready
     */
    public boolean isReady() {
        return sheetsService.isReady();
    }

    // CLIENT OPERATIONS

    /**
     * Read all clients from sheets
     * @return list of client entities
     */
    public List<ClientEntity> readAllClients() {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for reading clients");
            return null;
        }

        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_CLIENTS_TAB, "A2:Z1000");
            
            if (values == null) {
                return new ArrayList<>();
            }
            
            List<ClientEntity> clients = new ArrayList<>();
            
            for (List<Object> row : values) {
                ClientEntity client = SheetsMapper.mapRowToClient(row);
                if (client != null) {
                    clients.add(client);
                }
            }
            
            Log.d(TAG, "Read " + clients.size() + " clients from sheets");
            return clients;
            
        } catch (Exception e) {
            Log.e(TAG, "Error reading clients from sheets", e);
            return null;
        }
    }

    /**
     * Write client to sheets
     * @param client the client to write
     * @return true if successful
     */
    public boolean writeClient(ClientEntity client) {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for writing client");
            return false;
        }

        try {
            List<Object> row = SheetsMapper.mapClientToRow(client);
            
            // Find if client already exists (by UUID)
            int existingRow = findClientRow(client.getUuid());
            
            if (existingRow > 0) {
                // Update existing row
                String range = "A" + existingRow;
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.writeRange(Constants.SHEETS_CLIENTS_TAB, range, data);
            } else {
                // Append new row
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.appendRange(Constants.SHEETS_CLIENTS_TAB, "A", data);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error writing client to sheets", e);
            return false;
        }
    }

    /**
     * Find row number for client by UUID
     * @param clientUuid the client UUID
     * @return row number (1-based), or -1 if not found
     */
    private int findClientRow(String clientUuid) {
        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_CLIENTS_TAB, "A2:A1000");
            
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (!row.isEmpty() && clientUuid.equals(row.get(0).toString())) {
                        return i + 2; // +2 because we start from A2 and want 1-based row number
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error finding client row", e);
        }
        
        return -1;
    }

    // LOAN OPERATIONS

    /**
     * Read all loans from sheets
     * @return list of loan entities
     */
    public List<LoanEntity> readAllLoans() {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for reading loans");
            return null;
        }

        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_LOANS_TAB, "A2:Z1000");
            
            if (values == null) {
                return new ArrayList<>();
            }
            
            List<LoanEntity> loans = new ArrayList<>();
            
            for (List<Object> row : values) {
                LoanEntity loan = SheetsMapper.mapRowToLoan(row);
                if (loan != null) {
                    loans.add(loan);
                }
            }
            
            Log.d(TAG, "Read " + loans.size() + " loans from sheets");
            return loans;
            
        } catch (Exception e) {
            Log.e(TAG, "Error reading loans from sheets", e);
            return null;
        }
    }

    /**
     * Write loan to sheets
     * @param loan the loan to write
     * @return true if successful
     */
    public boolean writeLoan(LoanEntity loan) {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for writing loan");
            return false;
        }

        try {
            List<Object> row = SheetsMapper.mapLoanToRow(loan);
            
            // Find if loan already exists (by UUID)
            int existingRow = findLoanRow(loan.getUuid());
            
            if (existingRow > 0) {
                // Update existing row
                String range = "A" + existingRow;
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.writeRange(Constants.SHEETS_LOANS_TAB, range, data);
            } else {
                // Append new row
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.appendRange(Constants.SHEETS_LOANS_TAB, "A", data);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error writing loan to sheets", e);
            return false;
        }
    }

    /**
     * Find row number for loan by UUID
     * @param loanUuid the loan UUID
     * @return row number (1-based), or -1 if not found
     */
    private int findLoanRow(String loanUuid) {
        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_LOANS_TAB, "A2:A1000");
            
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (!row.isEmpty() && loanUuid.equals(row.get(0).toString())) {
                        return i + 2;
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error finding loan row", e);
        }
        
        return -1;
    }

    // PAYMENT OPERATIONS

    /**
     * Read all payments from sheets
     * @return list of payment entities
     */
    public List<PaymentEntity> readAllPayments() {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for reading payments");
            return null;
        }

        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_PAYMENTS_TAB, "A2:Z1000");
            
            if (values == null) {
                return new ArrayList<>();
            }
            
            List<PaymentEntity> payments = new ArrayList<>();
            
            for (List<Object> row : values) {
                PaymentEntity payment = SheetsMapper.mapRowToPayment(row);
                if (payment != null) {
                    payments.add(payment);
                }
            }
            
            Log.d(TAG, "Read " + payments.size() + " payments from sheets");
            return payments;
            
        } catch (Exception e) {
            Log.e(TAG, "Error reading payments from sheets", e);
            return null;
        }
    }

    /**
     * Write payment to sheets
     * @param payment the payment to write
     * @return true if successful
     */
    public boolean writePayment(PaymentEntity payment) {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for writing payment");
            return false;
        }

        try {
            List<Object> row = SheetsMapper.mapPaymentToRow(payment);
            
            // Find if payment already exists (by UUID)
            int existingRow = findPaymentRow(payment.getUuid());
            
            if (existingRow > 0) {
                // Update existing row
                String range = "A" + existingRow;
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.writeRange(Constants.SHEETS_PAYMENTS_TAB, range, data);
            } else {
                // Append new row
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.appendRange(Constants.SHEETS_PAYMENTS_TAB, "A", data);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error writing payment to sheets", e);
            return false;
        }
    }

    /**
     * Find row number for payment by UUID
     * @param paymentUuid the payment UUID
     * @return row number (1-based), or -1 if not found
     */
    private int findPaymentRow(String paymentUuid) {
        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_PAYMENTS_TAB, "A2:A1000");
            
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (!row.isEmpty() && paymentUuid.equals(row.get(0).toString())) {
                        return i + 2;
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error finding payment row", e);
        }
        
        return -1;
    }

    // INVESTOR OPERATIONS

    /**
     * Read all investors from sheets
     * @return list of investor entities
     */
    public List<InvestorEntity> readAllInvestors() {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for reading investors");
            return null;
        }

        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_INVESTORS_TAB, "A2:Z1000");
            
            if (values == null) {
                return new ArrayList<>();
            }
            
            List<InvestorEntity> investors = new ArrayList<>();
            
            for (List<Object> row : values) {
                InvestorEntity investor = SheetsMapper.mapRowToInvestor(row);
                if (investor != null) {
                    investors.add(investor);
                }
            }
            
            Log.d(TAG, "Read " + investors.size() + " investors from sheets");
            return investors;
            
        } catch (Exception e) {
            Log.e(TAG, "Error reading investors from sheets", e);
            return null;
        }
    }

    /**
     * Write investor to sheets
     * @param investor the investor to write
     * @return true if successful
     */
    public boolean writeInvestor(InvestorEntity investor) {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for writing investor");
            return false;
        }

        try {
            List<Object> row = SheetsMapper.mapInvestorToRow(investor);
            
            // Find if investor already exists (by UUID)
            int existingRow = findInvestorRow(investor.getUuid());
            
            if (existingRow > 0) {
                // Update existing row
                String range = "A" + existingRow;
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.writeRange(Constants.SHEETS_INVESTORS_TAB, range, data);
            } else {
                // Append new row
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.appendRange(Constants.SHEETS_INVESTORS_TAB, "A", data);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error writing investor to sheets", e);
            return false;
        }
    }

    /**
     * Find row number for investor by UUID
     * @param investorUuid the investor UUID
     * @return row number (1-based), or -1 if not found
     */
    private int findInvestorRow(String investorUuid) {
        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_INVESTORS_TAB, "A2:A1000");
            
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (!row.isEmpty() && investorUuid.equals(row.get(0).toString())) {
                        return i + 2;
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error finding investor row", e);
        }
        
        return -1;
    }

    // INVESTOR TRANSACTION OPERATIONS

    /**
     * Read all investor transactions from sheets
     * @return list of investor transaction entities
     */
    public List<InvestorTransactionEntity> readAllInvestorTransactions() {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for reading investor transactions");
            return null;
        }

        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_INVESTOR_TRANSACTIONS_TAB, "A2:Z1000");
            
            if (values == null) {
                return new ArrayList<>();
            }
            
            List<InvestorTransactionEntity> transactions = new ArrayList<>();
            
            for (List<Object> row : values) {
                InvestorTransactionEntity transaction = SheetsMapper.mapRowToInvestorTransaction(row);
                if (transaction != null) {
                    transactions.add(transaction);
                }
            }
            
            Log.d(TAG, "Read " + transactions.size() + " investor transactions from sheets");
            return transactions;
            
        } catch (Exception e) {
            Log.e(TAG, "Error reading investor transactions from sheets", e);
            return null;
        }
    }

    /**
     * Write investor transaction to sheets
     * @param transaction the transaction to write
     * @return true if successful
     */
    public boolean writeInvestorTransaction(InvestorTransactionEntity transaction) {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for writing investor transaction");
            return false;
        }

        try {
            List<Object> row = SheetsMapper.mapInvestorTransactionToRow(transaction);
            
            // Find if transaction already exists (by UUID)
            int existingRow = findInvestorTransactionRow(transaction.getUuid());
            
            if (existingRow > 0) {
                // Update existing row
                String range = "A" + existingRow;
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.writeRange(Constants.SHEETS_INVESTOR_TRANSACTIONS_TAB, range, data);
            } else {
                // Append new row
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.appendRange(Constants.SHEETS_INVESTOR_TRANSACTIONS_TAB, "A", data);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error writing investor transaction to sheets", e);
            return false;
        }
    }

    /**
     * Find row number for investor transaction by UUID
     * @param transactionUuid the transaction UUID
     * @return row number (1-based), or -1 if not found
     */
    private int findInvestorTransactionRow(String transactionUuid) {
        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_INVESTOR_TRANSACTIONS_TAB, "A2:A1000");
            
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (!row.isEmpty() && transactionUuid.equals(row.get(0).toString())) {
                        return i + 2;
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error finding investor transaction row", e);
        }
        
        return -1;
    }

    // LOAN FUNDING OPERATIONS

    /**
     * Read all loan funding from sheets
     * @return list of loan funding entities
     */
    public List<LoanFundingEntity> readAllLoanFunding() {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for reading loan funding");
            return null;
        }

        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_LOAN_FUNDING_TAB, "A2:Z1000");
            
            if (values == null) {
                return new ArrayList<>();
            }
            
            List<LoanFundingEntity> funding = new ArrayList<>();
            
            for (List<Object> row : values) {
                LoanFundingEntity loanFunding = SheetsMapper.mapRowToLoanFunding(row);
                if (loanFunding != null) {
                    funding.add(loanFunding);
                }
            }
            
            Log.d(TAG, "Read " + funding.size() + " loan funding records from sheets");
            return funding;
            
        } catch (Exception e) {
            Log.e(TAG, "Error reading loan funding from sheets", e);
            return null;
        }
    }

    /**
     * Write loan funding to sheets
     * @param loanFunding the loan funding to write
     * @return true if successful
     */
    public boolean writeLoanFunding(LoanFundingEntity loanFunding) {
        if (!isReady()) {
            Log.w(TAG, "Client not ready for writing loan funding");
            return false;
        }

        try {
            List<Object> row = SheetsMapper.mapLoanFundingToRow(loanFunding);
            
            // Find if loan funding already exists (by UUID)
            int existingRow = findLoanFundingRow(loanFunding.getUuid());
            
            if (existingRow > 0) {
                // Update existing row
                String range = "A" + existingRow;
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.writeRange(Constants.SHEETS_LOAN_FUNDING_TAB, range, data);
            } else {
                // Append new row
                List<List<Object>> data = Arrays.asList(row);
                return sheetsService.appendRange(Constants.SHEETS_LOAN_FUNDING_TAB, "A", data);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error writing loan funding to sheets", e);
            return false;
        }
    }

    /**
     * Find row number for loan funding by UUID
     * @param fundingUuid the funding UUID
     * @return row number (1-based), or -1 if not found
     */
    private int findLoanFundingRow(String fundingUuid) {
        try {
            List<List<Object>> values = sheetsService.readRange(Constants.SHEETS_LOAN_FUNDING_TAB, "A2:A1000");
            
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (!row.isEmpty() && fundingUuid.equals(row.get(0).toString())) {
                        return i + 2;
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error finding loan funding row", e);
        }
        
        return -1;
    }

    /**
     * Get client status information
     * @return status string
     */
    public String getClientStatus() {
        return sheetsService.getServiceStatus();
    }
}
