package com.moithuti.funds.data.remote.sheets;

import android.util.Log;

import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.PaymentEntity;
import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;
import com.moithuti.funds.data.local.entity.LoanFundingEntity;
import com.moithuti.funds.sync.SyncStatus;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Sheets Mapper - Maps between entities and Google Sheets rows
 * Handles conversion between local entities and spreadsheet data
 */
public class SheetsMapper {

    private static final String TAG = "SheetsMapper";

    // CLIENT MAPPING

    /**
     * Map client entity to spreadsheet row
     * @param client the client entity
     * @return list of objects representing the row
     */
    public static List<Object> mapClientToRow(ClientEntity client) {
        List<Object> row = new ArrayList<>();
        
        try {
            row.add(client.getUuid());
            row.add(client.getName());
            row.add(client.getPhone());
            row.add(client.getNotes());
            row.add(client.getStatus());
            row.add(client.getCreatedDate());
            row.add(client.getLastModified());
            row.add(client.getSyncStatus());
            row.add(client.isDeleted());
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping client to row", e);
            return null;
        }
        
        return row;
    }

    /**
     * Map spreadsheet row to client entity
     * @param row the spreadsheet row
     * @return client entity or null if mapping fails
     */
    public static ClientEntity mapRowToClient(List<Object> row) {
        if (row == null || row.size() < 9) {
            Log.w(TAG, "Invalid client row size: " + (row != null ? row.size() : "null"));
            return null;
        }

        try {
            ClientEntity client = new ClientEntity();
            
            client.setUuid(getStringValue(row.get(0)));
            client.setName(getStringValue(row.get(1)));
            client.setPhone(getStringValue(row.get(2)));
            client.setNotes(getStringValue(row.get(3)));
            client.setStatus(getStringValue(row.get(4)));
            client.setCreatedDate(getLongValue(row.get(5)));
            client.setLastModified(getLongValue(row.get(6)));
            client.setSyncStatus(getStringValue(row.get(7)));
            client.setDeleted(getBooleanValue(row.get(8)));
            
            return client;
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping row to client", e);
            return null;
        }
    }

    // LOAN MAPPING

    /**
     * Map loan entity to spreadsheet row
     * @param loan the loan entity
     * @return list of objects representing the row
     */
    public static List<Object> mapLoanToRow(LoanEntity loan) {
        List<Object> row = new ArrayList<>();
        
        try {
            row.add(loan.getUuid());
            row.add(loan.getClientId());
            row.add(loan.getAmount());
            row.add(loan.getDateIssued());
            row.add(loan.getDueDate());
            row.add(loan.getStatus());
            row.add(loan.getLastModified());
            row.add(loan.getSyncStatus());
            row.add(loan.isDeleted());
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping loan to row", e);
            return null;
        }
        
        return row;
    }

    /**
     * Map spreadsheet row to loan entity
     * @param row the spreadsheet row
     * @return loan entity or null if mapping fails
     */
    public static LoanEntity mapRowToLoan(List<Object> row) {
        if (row == null || row.size() < 9) {
            Log.w(TAG, "Invalid loan row size: " + (row != null ? row.size() : "null"));
            return null;
        }

        try {
            LoanEntity loan = new LoanEntity();
            
            loan.setUuid(getStringValue(row.get(0)));
            loan.setClientId(getStringValue(row.get(1)));
            loan.setAmount(getDoubleValue(row.get(2)));
            loan.setDateIssued(getLongValue(row.get(3)));
            loan.setDueDate(getLongValue(row.get(4)));
            loan.setStatus(getStringValue(row.get(5)));
            loan.setLastModified(getLongValue(row.get(6)));
            loan.setSyncStatus(getStringValue(row.get(7)));
            loan.setDeleted(getBooleanValue(row.get(8)));
            
            return loan;
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping row to loan", e);
            return null;
        }
    }

    // PAYMENT MAPPING

    /**
     * Map payment entity to spreadsheet row
     * @param payment the payment entity
     * @return list of objects representing the row
     */
    public static List<Object> mapPaymentToRow(PaymentEntity payment) {
        List<Object> row = new ArrayList<>();
        
        try {
            row.add(payment.getUuid());
            row.add(payment.getLoanId());
            row.add(payment.getAmount());
            row.add(payment.getDate());
            row.add(payment.getLastModified());
            row.add(payment.getSyncStatus());
            row.add(payment.isDeleted());
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping payment to row", e);
            return null;
        }
        
        return row;
    }

    /**
     * Map spreadsheet row to payment entity
     * @param row the spreadsheet row
     * @return payment entity or null if mapping fails
     */
    public static PaymentEntity mapRowToPayment(List<Object> row) {
        if (row == null || row.size() < 7) {
            Log.w(TAG, "Invalid payment row size: " + (row != null ? row.size() : "null"));
            return null;
        }

        try {
            PaymentEntity payment = new PaymentEntity();
            
            payment.setUuid(getStringValue(row.get(0)));
            payment.setLoanId(getStringValue(row.get(1)));
            payment.setAmount(getDoubleValue(row.get(2)));
            payment.setDate(getLongValue(row.get(3)));
            payment.setLastModified(getLongValue(row.get(4)));
            payment.setSyncStatus(getStringValue(row.get(5)));
            payment.setDeleted(getBooleanValue(row.get(6)));
            
            return payment;
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping row to payment", e);
            return null;
        }
    }

    // INVESTOR MAPPING

    /**
     * Map investor entity to spreadsheet row
     * @param investor the investor entity
     * @return list of objects representing the row
     */
    public static List<Object> mapInvestorToRow(InvestorEntity investor) {
        List<Object> row = new ArrayList<>();
        
        try {
            row.add(investor.getUuid());
            row.add(investor.getName());
            row.add(investor.isMainAccount());
            row.add(investor.getCreatedDate());
            row.add(investor.getLastModified());
            row.add(investor.getSyncStatus());
            row.add(investor.isDeleted());
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping investor to row", e);
            return null;
        }
        
        return row;
    }

    /**
     * Map spreadsheet row to investor entity
     * @param row the spreadsheet row
     * @return investor entity or null if mapping fails
     */
    public static InvestorEntity mapRowToInvestor(List<Object> row) {
        if (row == null || row.size() < 7) {
            Log.w(TAG, "Invalid investor row size: " + (row != null ? row.size() : "null"));
            return null;
        }

        try {
            InvestorEntity investor = new InvestorEntity();
            
            investor.setUuid(getStringValue(row.get(0)));
            investor.setName(getStringValue(row.get(1)));
            investor.setMainAccount(getBooleanValue(row.get(2)));
            investor.setCreatedDate(getLongValue(row.get(3)));
            investor.setLastModified(getLongValue(row.get(4)));
            investor.setSyncStatus(getStringValue(row.get(5)));
            investor.setDeleted(getBooleanValue(row.get(6)));
            
            return investor;
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping row to investor", e);
            return null;
        }
    }

    // INVESTOR TRANSACTION MAPPING

    /**
     * Map investor transaction entity to spreadsheet row
     * @param transaction the investor transaction entity
     * @return list of objects representing the row
     */
    public static List<Object> mapInvestorTransactionToRow(InvestorTransactionEntity transaction) {
        List<Object> row = new ArrayList<>();
        
        try {
            row.add(transaction.getUuid());
            row.add(transaction.getInvestorId());
            row.add(transaction.getType());
            row.add(transaction.getAmount());
            row.add(transaction.getRelatedLoanId());
            row.add(transaction.getTimestamp());
            row.add(transaction.getYearMonth());
            row.add(transaction.getLastModified());
            row.add(transaction.getSyncStatus());
            row.add(transaction.isDeleted());
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping investor transaction to row", e);
            return null;
        }
        
        return row;
    }

    /**
     * Map spreadsheet row to investor transaction entity
     * @param row the spreadsheet row
     * @return investor transaction entity or null if mapping fails
     */
    public static InvestorTransactionEntity mapRowToInvestorTransaction(List<Object> row) {
        if (row == null || row.size() < 10) {
            Log.w(TAG, "Invalid investor transaction row size: " + (row != null ? row.size() : "null"));
            return null;
        }

        try {
            InvestorTransactionEntity transaction = new InvestorTransactionEntity();
            
            transaction.setUuid(getStringValue(row.get(0)));
            transaction.setInvestorId(getStringValue(row.get(1)));
            transaction.setType(getStringValue(row.get(2)));
            transaction.setAmount(getDoubleValue(row.get(3)));
            transaction.setRelatedLoanId(getStringValue(row.get(4)));
            transaction.setTimestamp(getLongValue(row.get(5)));
            transaction.setYearMonth(getStringValue(row.get(6)));
            transaction.setLastModified(getLongValue(row.get(7)));
            transaction.setSyncStatus(getStringValue(row.get(8)));
            transaction.setDeleted(getBooleanValue(row.get(9)));
            
            return transaction;
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping row to investor transaction", e);
            return null;
        }
    }

    // LOAN FUNDING MAPPING

    /**
     * Map loan funding entity to spreadsheet row
     * @param loanFunding the loan funding entity
     * @return list of objects representing the row
     */
    public static List<Object> mapLoanFundingToRow(LoanFundingEntity loanFunding) {
        List<Object> row = new ArrayList<>();
        
        try {
            row.add(loanFunding.getUuid());
            row.add(loanFunding.getLoanId());
            row.add(loanFunding.getInvestorId());
            row.add(loanFunding.getAmount());
            row.add(loanFunding.getCreatedDate());
            row.add(loanFunding.getLastModified());
            row.add(loanFunding.getSyncStatus());
            row.add(loanFunding.isDeleted());
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping loan funding to row", e);
            return null;
        }
        
        return row;
    }

    /**
     * Map spreadsheet row to loan funding entity
     * @param row the spreadsheet row
     * @return loan funding entity or null if mapping fails
     */
    public static LoanFundingEntity mapRowToLoanFunding(List<Object> row) {
        if (row == null || row.size() < 8) {
            Log.w(TAG, "Invalid loan funding row size: " + (row != null ? row.size() : "null"));
            return null;
        }

        try {
            LoanFundingEntity loanFunding = new LoanFundingEntity();
            
            loanFunding.setUuid(getStringValue(row.get(0)));
            loanFunding.setLoanId(getStringValue(row.get(1)));
            loanFunding.setInvestorId(getStringValue(row.get(2)));
            loanFunding.setAmount(getDoubleValue(row.get(3)));
            loanFunding.setCreatedDate(getLongValue(row.get(4)));
            loanFunding.setLastModified(getLongValue(row.get(5)));
            loanFunding.setSyncStatus(getStringValue(row.get(6)));
            loanFunding.setDeleted(getBooleanValue(row.get(7)));
            
            return loanFunding;
            
        } catch (Exception e) {
            Log.e(TAG, "Error mapping row to loan funding", e);
            return null;
        }
    }

    // UTILITY METHODS

    /**
     * Get string value from object
     * @param value the object
     * @return string value or empty string
     */
    private static String getStringValue(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * Get long value from object
     * @param value the object
     * @return long value or 0
     */
    private static long getLongValue(Object value) {
        if (value == null) {
            return 0L;
        }
        
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            Log.w(TAG, "Error parsing long value: " + value);
            return 0L;
        }
    }

    /**
     * Get double value from object
     * @param value the object
     * @return double value or 0.0
     */
    private static double getDoubleValue(Object value) {
        if (value == null) {
            return 0.0;
        }
        
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            Log.w(TAG, "Error parsing double value: " + value);
            return 0.0;
        }
    }

    /**
     * Get boolean value from object
     * @param value the object
     * @return boolean value or false
     */
    private static boolean getBooleanValue(Object value) {
        if (value == null) {
            return false;
        }
        
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        
        String stringValue = value.toString().toLowerCase();
        return "true".equals(stringValue) || "1".equals(stringValue);
    }

    /**
     * Validate entity mapping
     * @param entity the entity to validate
     * @return true if valid for mapping
     */
    public static boolean validateEntityForMapping(Object entity) {
        if (entity == null) {
            return false;
        }
        
        // Check UUID for all entities
        if (entity instanceof ClientEntity) {
            ClientEntity client = (ClientEntity) entity;
            return !UuidUtil.isEmpty(client.getUuid());
        } else if (entity instanceof LoanEntity) {
            LoanEntity loan = (LoanEntity) entity;
            return !UuidUtil.isEmpty(loan.getUuid()) && !UuidUtil.isEmpty(loan.getClientId());
        } else if (entity instanceof PaymentEntity) {
            PaymentEntity payment = (PaymentEntity) entity;
            return !UuidUtil.isEmpty(payment.getUuid()) && !UuidUtil.isEmpty(payment.getLoanId());
        } else if (entity instanceof InvestorEntity) {
            InvestorEntity investor = (InvestorEntity) entity;
            return !UuidUtil.isEmpty(investor.getUuid());
        } else if (entity instanceof InvestorTransactionEntity) {
            InvestorTransactionEntity transaction = (InvestorTransactionEntity) entity;
            return !UuidUtil.isEmpty(transaction.getUuid()) && !UuidUtil.isEmpty(transaction.getInvestorId());
        } else if (entity instanceof LoanFundingEntity) {
            LoanFundingEntity funding = (LoanFundingEntity) entity;
            return !UuidUtil.isEmpty(funding.getUuid()) && 
                   !UuidUtil.isEmpty(funding.getLoanId()) && 
                   !UuidUtil.isEmpty(funding.getInvestorId());
        }
        
        return false;
    }

    /**
     * Get column index for field name
     * @param entityType the entity type
     * @param fieldName the field name
     * @return column index or -1 if not found
     */
    public static int getColumnIndex(String entityType, String fieldName) {
        switch (entityType) {
            case "ClientEntity":
                return getClientColumnIndex(fieldName);
            case "LoanEntity":
                return getLoanColumnIndex(fieldName);
            case "PaymentEntity":
                return getPaymentColumnIndex(fieldName);
            case "InvestorEntity":
                return getInvestorColumnIndex(fieldName);
            case "InvestorTransactionEntity":
                return getInvestorTransactionColumnIndex(fieldName);
            case "LoanFundingEntity":
                return getLoanFundingColumnIndex(fieldName);
            default:
                return -1;
        }
    }

    private static int getClientColumnIndex(String fieldName) {
        switch (fieldName) {
            case "uuid": return 0;
            case "name": return 1;
            case "phone": return 2;
            case "notes": return 3;
            case "status": return 4;
            case "createdDate": return 5;
            case "lastModified": return 6;
            case "syncStatus": return 7;
            case "deleted": return 8;
            default: return -1;
        }
    }

    private static int getLoanColumnIndex(String fieldName) {
        switch (fieldName) {
            case "uuid": return 0;
            case "clientId": return 1;
            case "amount": return 2;
            case "dateIssued": return 3;
            case "dueDate": return 4;
            case "status": return 5;
            case "lastModified": return 6;
            case "syncStatus": return 7;
            case "deleted": return 8;
            default: return -1;
        }
    }

    private static int getPaymentColumnIndex(String fieldName) {
        switch (fieldName) {
            case "uuid": return 0;
            case "loanId": return 1;
            case "amount": return 2;
            case "date": return 3;
            case "lastModified": return 4;
            case "syncStatus": return 5;
            case "deleted": return 6;
            default: return -1;
        }
    }

    private static int getInvestorColumnIndex(String fieldName) {
        switch (fieldName) {
            case "uuid": return 0;
            case "name": return 1;
            case "isMainAccount": return 2;
            case "createdDate": return 3;
            case "lastModified": return 4;
            case "syncStatus": return 5;
            case "deleted": return 6;
            default: return -1;
        }
    }

    private static int getInvestorTransactionColumnIndex(String fieldName) {
        switch (fieldName) {
            case "uuid": return 0;
            case "investorId": return 1;
            case "type": return 2;
            case "amount": return 3;
            case "relatedLoanId": return 4;
            case "timestamp": return 5;
            case "yearMonth": return 6;
            case "lastModified": return 7;
            case "syncStatus": return 8;
            case "deleted": return 9;
            default: return -1;
        }
    }

    private static int getLoanFundingColumnIndex(String fieldName) {
        switch (fieldName) {
            case "uuid": return 0;
            case "loanId": return 1;
            case "investorId": return 2;
            case "amount": return 3;
            case "createdDate": return 4;
            case "lastModified": return 5;
            case "syncStatus": return 6;
            case "deleted": return 7;
            default: return -1;
        }
    }
}
