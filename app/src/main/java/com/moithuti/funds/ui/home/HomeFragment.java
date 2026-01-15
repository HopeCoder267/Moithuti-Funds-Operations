package com.moithuti.funds.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.card.MaterialCardView;
import com.moithuti.funds.R;
import com.moithuti.funds.ui.common.UiUtils;

/**
 * Home Fragment - View-only dashboard
 * Shows overall finance cards, charts, and investor summaries
 */
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    
    // UI components
    private TextView syncStatusText;
    private TextView totalInvestedText;
    private TextView totalLoanedText;
    private TextView totalRepaidText;
    private TextView totalAvailableText;
    
    // Profit tracking components
    private TextView availableFundsText;
    private TextView monthlyProfitText;
    private TextView cumulativeProfitText;
    private TextView interestEarnedText;
    private PieChart profitPieChart;
    private BarChart monthlyProfitChart;
    
    private PieChart statusPieChart;
    private BarChart monthlyBarChart;
    private RecyclerView investorSummaryRecyclerView;
    private ProgressBar loadingIndicator;
    private TextView errorMessageText;
    
    // Adapters
    private InvestorSummaryAdapter investorSummaryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        
        // Initialize UI components
        initViews(view);
        
        // Setup observers
        setupObservers();
        
        // Setup click listeners
        setupClickListeners(view);
        
        // Initialize ViewModel
        viewModel.initialize();
    }

    private void initViews(View view) {
        syncStatusText = view.findViewById(R.id.sync_status);
        totalInvestedText = view.findViewById(R.id.total_invested);
        totalLoanedText = view.findViewById(R.id.total_loaned);
        totalRepaidText = view.findViewById(R.id.total_repaid);
        totalAvailableText = view.findViewById(R.id.total_available);
        
        // Profit tracking components will be added when layout is updated
        // For now, using existing text views to display profit metrics
        
        statusPieChart = view.findViewById(R.id.status_pie_chart);
        monthlyBarChart = view.findViewById(R.id.monthly_bar_chart);
        investorSummaryRecyclerView = view.findViewById(R.id.investor_summary_recycler_view);
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        errorMessageText = view.findViewById(R.id.error_message_text);
        
        // Setup RecyclerView
        investorSummaryAdapter = new InvestorSummaryAdapter();
        investorSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        investorSummaryRecyclerView.setAdapter(investorSummaryAdapter);
        
        // Setup charts (basic configuration)
        setupCharts();
    }

    private void setupCharts() {
        // Setup pie chart
        statusPieChart.setUsePercentValues(true);
        statusPieChart.getDescription().setEnabled(false);
        statusPieChart.setDrawHoleEnabled(true);
        statusPieChart.setHoleColor(android.graphics.Color.WHITE);
        statusPieChart.setTransparentCircleColor(android.graphics.Color.WHITE);
        statusPieChart.setHoleRadius(50f);
        statusPieChart.setTransparentCircleRadius(55f);
        
        // Setup bar chart
        monthlyBarChart.getDescription().setEnabled(false);
        monthlyBarChart.setDrawGridBackground(false);
        monthlyBarChart.setDrawBarShadow(false);
        monthlyBarChart.getLegend().setEnabled(false);
    }

    private void setupObservers() {
        // Dashboard stats observer
        viewModel.getDashboardStatsLiveData().observe(getViewLifecycleOwner(), stats -> {
            if (stats != null) {
                updateDashboardStats(stats);
                updateCharts(stats);
                updateInvestorSummary(stats);
            }
        });
        
        // Profit tracking observers - using existing totalAvailableText for available funds
        viewModel.getAvailableFundsLiveData().observe(getViewLifecycleOwner(), availableFunds -> {
            if (availableFunds != null && totalAvailableText != null) {
                totalAvailableText.setText(UiUtils.formatCurrency(availableFunds));
            }
        });
        
        // For now, show profit metrics in existing text views (if layout is updated later, these can be moved)
        viewModel.getMonthlyProfitLiveData().observe(getViewLifecycleOwner(), monthlyProfit -> {
            if (monthlyProfit != null && totalLoanedText != null) {
                totalLoanedText.setText("Monthly Profit: " + UiUtils.formatCurrency(monthlyProfit));
            }
        });
        
        viewModel.getCumulativeProfitLiveData().observe(getViewLifecycleOwner(), cumulativeProfit -> {
            if (cumulativeProfit != null && totalRepaidText != null) {
                totalRepaidText.setText("Cumulative Profit: " + UiUtils.formatCurrency(cumulativeProfit));
            }
        });
        
        viewModel.getInterestEarnedLiveData().observe(getViewLifecycleOwner(), interestEarned -> {
            if (interestEarned != null && totalInvestedText != null) {
                totalInvestedText.setText("Interest Earned: " + UiUtils.formatCurrency(interestEarned));
            }
        });
        
        // Loading state observer
        viewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            if (loadingIndicator != null) {
                loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
        
        // Error message observer
        viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                if (errorMessageText != null) {
                    errorMessageText.setText(errorMessage);
                    errorMessageText.setVisibility(View.VISIBLE);
                }
                UiUtils.showSnackbar(getView(), errorMessage);
            } else {
                if (errorMessageText != null) {
                    errorMessageText.setVisibility(View.GONE);
                }
            }
        });
        
        // Success message observer
        viewModel.getSuccessMessageLiveData().observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null && !successMessage.trim().isEmpty()) {
                UiUtils.showSnackbar(getView(), successMessage);
            }
        });
        
        // Connectivity observer
        viewModel.getIsConnectedLiveData().observe(getViewLifecycleOwner(), isConnected -> {
            updateSyncStatus(isConnected);
        });
        
        // Last sync time observer
        viewModel.getLastSyncTimeLiveData().observe(getViewLifecycleOwner(), lastSyncTime -> {
            updateLastSyncTime(lastSyncTime);
        });
    }

    private void setupClickListeners(View view) {
        // Force sync button
        view.findViewById(R.id.force_sync).setOnClickListener(v -> {
            viewModel.forceSync();
        });
        
        // Refresh on swipe (could be implemented with SwipeRefreshLayout)
    }

    private void updateDashboardStats(DashboardStats stats) {
        // Update finance cards
        totalInvestedText.setText(UiUtils.formatCurrency(stats.getTotalInvested()));
        totalLoanedText.setText(UiUtils.formatCurrency(stats.getTotalLoaned()));
        totalRepaidText.setText(UiUtils.formatCurrency(stats.getTotalRepaid()));
        totalAvailableText.setText(UiUtils.formatCurrency(stats.getTotalAvailable()));
    }

    private void updateCharts(DashboardStats stats) {
        // Update pie chart with client status distribution
        updatePieChart(stats);
        
        // Update bar chart with monthly data
        updateBarChart(stats);
    }

    private void updatePieChart(DashboardStats stats) {
        // Create pie chart data from client status counts
        java.util.List<com.github.mikephil.charting.data.PieEntry> entries = new java.util.ArrayList<>();
        
        entries.add(new com.github.mikephil.charting.data.PieEntry(stats.getPaidClients(), "Paid"));
        entries.add(new com.github.mikephil.charting.data.PieEntry(stats.getPartialClients(), "Partial"));
        entries.add(new com.github.mikephil.charting.data.PieEntry(stats.getOverdueClients(), "Overdue"));
        entries.add(new com.github.mikephil.charting.data.PieEntry(stats.getBlacklistedClients(), "Blacklisted"));
        entries.add(new com.github.mikephil.charting.data.PieEntry(stats.getOwingClients(), "Owing"));
        
        com.github.mikephil.charting.data.PieDataSet dataSet = new com.github.mikephil.charting.data.PieDataSet(entries, "Client Status");
        dataSet.setColors(new int[]{
            com.moithuti.funds.ui.common.StatusColorUtil.getChartColor("PAID"),
            com.moithuti.funds.ui.common.StatusColorUtil.getChartColor("PARTIAL"),
            com.moithuti.funds.ui.common.StatusColorUtil.getChartColor("OVERDUE"),
            com.moithuti.funds.ui.common.StatusColorUtil.getChartColor("BLACKLISTED"),
            com.moithuti.funds.ui.common.StatusColorUtil.getChartColor("OWING")
        });
        
        com.github.mikephil.charting.data.PieData data = new com.github.mikephil.charting.data.PieData(dataSet);
        statusPieChart.setData(data);
        statusPieChart.invalidate();
    }

    private void updateBarChart(DashboardStats stats) {
        // Create bar chart data from monthly data
        java.util.List<com.github.mikephil.charting.data.BarEntry> entries = new java.util.ArrayList<>();
        java.util.List<String> labels = new java.util.ArrayList<>();
        
        java.util.Map<String, Double> monthlyInvestments = stats.getMonthlyInvestments();
        
        if (monthlyInvestments != null) {
            int index = 0;
            for (String month : monthlyInvestments.keySet()) {
                Double value = monthlyInvestments.get(month);
                if (value != null) {
                    entries.add(new com.github.mikephil.charting.data.BarEntry(index, value.floatValue()));
                    labels.add(month);
                }
                index++;
            }
        }
        
        com.github.mikephil.charting.data.BarDataSet dataSet = new com.github.mikephil.charting.data.BarDataSet(entries, "Monthly Investments");
        dataSet.setColor(com.moithuti.funds.ui.common.StatusColorUtil.getChartColor("PAID"));
        
        com.github.mikephil.charting.data.BarData data = new com.github.mikephil.charting.data.BarData(dataSet);
        monthlyBarChart.setData(data);
        
        // Set X-axis labels
        com.github.mikephil.charting.components.XAxis xAxis = monthlyBarChart.getXAxis();
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        
        monthlyBarChart.invalidate();
    }

    private void updateInvestorSummary(DashboardStats stats) {
        if (stats.getInvestorSummaries() != null) {
            investorSummaryAdapter.updateData(stats.getInvestorSummaries());
        }
    }

    private void updateSyncStatus(boolean isConnected) {
        if (isConnected) {
            syncStatusText.setText("🟢 Online");
            syncStatusText.setTextColor(getResources().getColor(R.color.status_paid, null));
        } else {
            syncStatusText.setText("🔴 Offline");
            syncStatusText.setTextColor(getResources().getColor(R.color.status_overdue, null));
        }
    }

    private void updateLastSyncTime(long lastSyncTime) {
        if (lastSyncTime > 0) {
            // Could update a TextView with last sync time if needed
        }
    }

    /**
     * Investor summary adapter for the RecyclerView
     */
    private static class InvestorSummaryAdapter extends RecyclerView.Adapter<InvestorSummaryAdapter.ViewHolder> {
        
        private java.util.List<DashboardStats.InvestorSummary> summaries = new java.util.ArrayList<>();
        
        public void updateData(java.util.List<DashboardStats.InvestorSummary> newSummaries) {
            summaries.clear();
            if (newSummaries != null) {
                summaries.addAll(newSummaries);
            }
            notifyDataSetChanged();
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_investor_card, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DashboardStats.InvestorSummary summary = summaries.get(position);
            holder.bind(summary);
        }
        
        @Override
        public int getItemCount() {
            return summaries.size();
        }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView investorNameText;
            private TextView investedText;
            private TextView loanedOutText;
            private TextView repaidText;
            private TextView availableText;
            
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                investorNameText = itemView.findViewById(R.id.investor_name);
                investedText = itemView.findViewById(R.id.invested_amount);
                loanedOutText = itemView.findViewById(R.id.loaned_out_amount);
                repaidText = itemView.findViewById(R.id.repaid_amount);
                availableText = itemView.findViewById(R.id.available_amount);
            }
            
            public void bind(DashboardStats.InvestorSummary summary) {
                investorNameText.setText(summary.getInvestorName());
                investedText.setText(UiUtils.formatCurrency(summary.getInvested()));
                loanedOutText.setText(UiUtils.formatCurrency(summary.getLoanedOut()));
                repaidText.setText(UiUtils.formatCurrency(summary.getRepaid()));
                availableText.setText(UiUtils.formatCurrency(summary.getAvailable()));
            }
        }
    }
}
