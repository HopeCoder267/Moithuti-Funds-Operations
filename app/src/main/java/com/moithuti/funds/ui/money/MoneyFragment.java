package com.moithuti.funds.ui.money;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moithuti.funds.R;
import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.ui.common.UiUtils;

import java.util.List;

/**
 * Money Fragment - Investor management and loan funding
 * Add/edit investors, set monthly investments, issue loans with funding
 */
public class MoneyFragment extends Fragment {

    private MoneyViewModel viewModel;
    
    // UI components
    private TabLayout tabLayout;
    private ProgressBar loadingIndicator;
    private TextView errorMessageText;
    private TextView successMessageText;
    
    // Investors tab components
    private RecyclerView investorsRecyclerView;
    private Button addInvestorButton;
    private TextInputEditText investorNameEditText;
    private TextInputLayout investorNameLayout;
    private Button saveInvestorButton;
    
    // Loan funding tab components
    private TextInputEditText loanAmountEditText;
    private TextInputLayout loanAmountLayout;
    private RecyclerView fundingInvestorsRecyclerView;
    private Button issueLoanButton;
    private TextView totalFundingText;
    
    // Adapters
    private InvestorsAdapter investorsAdapter;
    private FundingInvestorsAdapter fundingInvestorsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_money, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MoneyViewModel.class);
        
        // Setup UI components
        setupUI(view);
        
        // Setup observers
        setupObservers();
        
        // Initialize data
        viewModel.initialize();
    }

    private void setupUI(View view) {
        // Tab layout
        tabLayout = view.findViewById(R.id.money_tabs);
        
        // Loading and messages
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        errorMessageText = view.findViewById(R.id.error_message_text);
        successMessageText = view.findViewById(R.id.success_message_text);
        
        // Investors tab
        investorsRecyclerView = view.findViewById(R.id.investors_recycler_view);
        addInvestorButton = view.findViewById(R.id.add_investor_button);
        investorNameEditText = view.findViewById(R.id.investor_name_edit_text);
        investorNameLayout = view.findViewById(R.id.investor_name_layout);
        saveInvestorButton = view.findViewById(R.id.save_investor_button);
        
        // Loan funding tab
        loanAmountEditText = view.findViewById(R.id.loan_amount_edit_text);
        loanAmountLayout = view.findViewById(R.id.loan_amount_layout);
        fundingInvestorsRecyclerView = view.findViewById(R.id.funding_investors_recycler_view);
        issueLoanButton = view.findViewById(R.id.issue_loan_button);
        totalFundingText = view.findViewById(R.id.total_funding_text);
        
        // Setup RecyclerViews
        setupRecyclerViews();
        
        // Setup click listeners
        setupClickListeners();
        
        // Setup tab listener
        setupTabListener();
    }

    private void setupRecyclerViews() {
        // Investors RecyclerView
        investorsAdapter = new InvestorsAdapter();
        investorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        investorsRecyclerView.setAdapter(investorsAdapter);
        
        // Funding Investors RecyclerView
        fundingInvestorsAdapter = new FundingInvestorsAdapter();
        fundingInvestorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fundingInvestorsRecyclerView.setAdapter(fundingInvestorsAdapter);
    }

    private void setupClickListeners() {
        // Add investor button
        addInvestorButton.setOnClickListener(v -> {
            // Show investor form
            investorNameLayout.setVisibility(View.VISIBLE);
            saveInvestorButton.setVisibility(View.VISIBLE);
            addInvestorButton.setVisibility(View.GONE);
        });
        
        // Save investor button
        saveInvestorButton.setOnClickListener(v -> {
            String name = investorNameEditText.getText().toString().trim();
            if (name.isEmpty()) {
                investorNameLayout.setError("Investor name is required");
                return;
            }
            
            viewModel.updateInvestorName(name);
            viewModel.updateIsMainAccount(false); // Default to not main account
            viewModel.saveInvestor();
        });
        
        // Issue loan button
        issueLoanButton.setOnClickListener(v -> {
            String amountStr = loanAmountEditText.getText().toString().trim();
            if (amountStr.isEmpty()) {
                loanAmountLayout.setError("Loan amount is required");
                return;
            }
            
            try {
                double amount = Double.parseDouble(amountStr);
                viewModel.updateLoanAmount(amount);
                viewModel.issueLoanWithFunding();
            } catch (NumberFormatException e) {
                loanAmountLayout.setError("Invalid loan amount");
            }
        });
    }

    private void setupTabListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                
                // Hide all content first
                View investorsContent = getView().findViewById(R.id.investors_content);
                View loanContent = getView().findViewById(R.id.loan_content);
                
                if (investorsContent != null) {
                    investorsContent.setVisibility(View.GONE);
                }
                if (loanContent != null) {
                    loanContent.setVisibility(View.GONE);
                }
                
                if ("Investors".equals(tabText)) {
                    if (investorsContent != null) {
                        investorsContent.setVisibility(View.VISIBLE);
                    }
                    viewModel.setActiveTab("investors");
                } else if ("Issue Loan".equals(tabText)) {
                    if (loanContent != null) {
                        loanContent.setVisibility(View.VISIBLE);
                    }
                    viewModel.setActiveTab("loans");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselection if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselection if needed
            }
        });
        
        // Select first tab by default
        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        if (firstTab != null) {
            firstTab.select();
        }
    }

    private void setupObservers() {
        // Loading observer
        viewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        
        // Error message observer
        viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                errorMessageText.setText(errorMessage);
                errorMessageText.setVisibility(View.VISIBLE);
                successMessageText.setVisibility(View.GONE);
            } else {
                errorMessageText.setVisibility(View.GONE);
            }
        });
        
        // Success message observer
        viewModel.getSuccessMessageLiveData().observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null && !successMessage.isEmpty()) {
                successMessageText.setText(successMessage);
                successMessageText.setVisibility(View.VISIBLE);
                errorMessageText.setVisibility(View.GONE);
                
                // Hide success message after 3 seconds
                successMessageText.postDelayed(() -> {
                    successMessageText.setVisibility(View.GONE);
                }, 3000);
            } else {
                successMessageText.setVisibility(View.GONE);
            }
        });
        
        // Investors observer
        viewModel.getOtherInvestorsLiveData().observe(getViewLifecycleOwner(), investors -> {
            investorsAdapter.updateInvestors(investors);
        });
        
        // Available investors for funding observer
        viewModel.getAvailableInvestorsLiveData().observe(getViewLifecycleOwner(), investors -> {
            fundingInvestorsAdapter.updateInvestors(investors);
        });
        
        // Loan amount observer
        viewModel.getLoanAmountLiveData().observe(getViewLifecycleOwner(), amount -> {
            if (amount != null) {
                loanAmountEditText.setText(String.valueOf(amount));
            }
        });
        
        // Selected investors for funding observer
        viewModel.getSelectedInvestorsLiveData().observe(getViewLifecycleOwner(), investors -> {
            fundingInvestorsAdapter.updateSelectedInvestors(investors);
            updateTotalFunding();
        });
        
        // Investor amounts observer
        viewModel.getInvestorAmountsLiveData().observe(getViewLifecycleOwner(), amounts -> {
            fundingInvestorsAdapter.updateInvestorAmounts(amounts);
            updateTotalFunding();
        });
    }

    private void updateTotalFunding() {
        double total = viewModel.getTotalSelectedFunding();
        totalFundingText.setText("Total Funding: " + UiUtils.formatCurrency(total));
    }

    /**
     * Investors Adapter for displaying investors list
     */
    private static class InvestorsAdapter extends RecyclerView.Adapter<InvestorsAdapter.InvestorViewHolder> {
        
        private List<InvestorEntity> investors = new java.util.ArrayList<>();
        
        @NonNull
        @Override
        public InvestorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_investor_card, parent, false);
            return new InvestorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull InvestorViewHolder holder, int position) {
            InvestorEntity investor = investors.get(position);
            holder.bind(investor);
        }

        @Override
        public int getItemCount() {
            return investors.size();
        }

        public void updateInvestors(List<InvestorEntity> newInvestors) {
            this.investors = newInvestors != null ? newInvestors : new java.util.ArrayList<>();
            notifyDataSetChanged();
        }

        static class InvestorViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText;
            private TextView investedAmountText;
            private TextView loanedOutAmountText;
            private TextView repaidAmountText;
            private TextView availableAmountText;

            public InvestorViewHolder(@NonNull View itemView) {
                super(itemView);
                nameText = itemView.findViewById(R.id.investor_name);
                investedAmountText = itemView.findViewById(R.id.invested_amount);
                loanedOutAmountText = itemView.findViewById(R.id.loaned_out_amount);
                repaidAmountText = itemView.findViewById(R.id.repaid_amount);
                availableAmountText = itemView.findViewById(R.id.available_amount);
            }

            public void bind(InvestorEntity investor) {
                nameText.setText(investor.getName());
                
                // For now, show placeholder values - would calculate actual balances
                investedAmountText.setText(UiUtils.formatCurrency(0.0));
                loanedOutAmountText.setText(UiUtils.formatCurrency(0.0));
                repaidAmountText.setText(UiUtils.formatCurrency(0.0));
                availableAmountText.setText(UiUtils.formatCurrency(0.0));
                
                itemView.setOnClickListener(v -> {
                    // Handle investor click - could show details or edit
                    Toast.makeText(v.getContext(), "Clicked: " + investor.getName(), Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    /**
     * Funding Investors Adapter for loan funding
     */
    private static class FundingInvestorsAdapter extends RecyclerView.Adapter<FundingInvestorsAdapter.FundingInvestorViewHolder> {
        
        private List<InvestorEntity> investors = new java.util.ArrayList<>();
        private List<InvestorEntity> selectedInvestors = new java.util.ArrayList<>();
        private List<Double> investorAmounts = new java.util.ArrayList<>();
        
        @NonNull
        @Override
        public FundingInvestorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_funding_investor, parent, false);
            return new FundingInvestorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FundingInvestorViewHolder holder, int position) {
            InvestorEntity investor = investors.get(position);
            boolean isSelected = selectedInvestors.contains(investor);
            Double amount = null;
            
            if (isSelected) {
                int index = selectedInvestors.indexOf(investor);
                if (index < investorAmounts.size()) {
                    amount = investorAmounts.get(index);
                }
            }
            
            holder.bind(investor, isSelected, amount);
        }

        @Override
        public int getItemCount() {
            return investors.size();
        }

        public void updateInvestors(List<InvestorEntity> newInvestors) {
            this.investors = newInvestors != null ? newInvestors : new java.util.ArrayList<>();
            notifyDataSetChanged();
        }

        public void updateSelectedInvestors(List<InvestorEntity> newSelectedInvestors) {
            this.selectedInvestors = newSelectedInvestors != null ? newSelectedInvestors : new java.util.ArrayList<>();
            notifyDataSetChanged();
        }

        public void updateInvestorAmounts(List<Double> newInvestorAmounts) {
            this.investorAmounts = newInvestorAmounts != null ? newInvestorAmounts : new java.util.ArrayList<>();
            notifyDataSetChanged();
        }

        static class FundingInvestorViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText;
            private TextView balanceText;
            private EditText amountEditText;
            private MaterialCardView cardView;

            public FundingInvestorViewHolder(@NonNull View itemView) {
                super(itemView);
                nameText = itemView.findViewById(R.id.funding_investor_name_text);
                balanceText = itemView.findViewById(R.id.funding_investor_balance_text);
                amountEditText = itemView.findViewById(R.id.funding_amount_edit_text);
                cardView = itemView.findViewById(R.id.funding_investor_card);
            }

            public void bind(InvestorEntity investor, boolean isSelected, Double amount) {
                nameText.setText(investor.getName());
                balanceText.setText("Available: " + UiUtils.formatCurrency(0.0)); // Would calculate actual balance
                
                cardView.setCardBackgroundColor(isSelected ? 
                        itemView.getContext().getResources().getColor(android.R.color.holo_blue_light) :
                        itemView.getContext().getResources().getColor(android.R.color.white));
                
                amountEditText.setVisibility(isSelected ? View.VISIBLE : View.GONE);
                if (isSelected && amount != null) {
                    amountEditText.setText(String.valueOf(amount));
                }
                
                cardView.setOnClickListener(v -> {
                    // Toggle selection
                    // This would need to communicate back to the ViewModel
                    Toast.makeText(v.getContext(), "Toggle: " + investor.getName(), Toast.LENGTH_SHORT).show();
                });
            }
        }
    }
}
