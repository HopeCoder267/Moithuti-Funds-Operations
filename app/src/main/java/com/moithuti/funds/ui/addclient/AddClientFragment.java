package com.moithuti.funds.ui.addclient;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.moithuti.funds.R;

/**
 * Add Client Fragment - Form for adding new clients with optional loan
 */
public class AddClientFragment extends Fragment {

    private AddClientViewModel viewModel;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText loanAmountEditText;
    private EditText interestRateEditText;
    private EditText loanTermEditText;
    private Button saveClientButton;
    private Button saveClientWithLoanButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(AddClientViewModel.class);
        
        // Initialize views
        initViews(view);
        setupClickListeners();
        setupObservers();
    }

    private void initViews(View view) {
        nameEditText = view.findViewById(R.id.client_name);
        phoneEditText = view.findViewById(R.id.client_phone);
        emailEditText = view.findViewById(R.id.client_notes); // Using notes field for email temporarily
        addressEditText = null; // Not available in current layout
        loanAmountEditText = null; // Not available in current layout
        interestRateEditText = null; // Not available in current layout
        loanTermEditText = null; // Not available in current layout
        saveClientButton = view.findViewById(R.id.save_client);
        saveClientWithLoanButton = null; // Not available in current layout
    }

    private void setupClickListeners() {
        if (saveClientButton != null) {
            saveClientButton.setOnClickListener(v -> saveClientOnly());
        }
        // saveClientWithLoanButton is not available in current layout
    }

    private void setupObservers() {
        // Observe save operations
        viewModel.getSaveSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Client saved successfully!", Toast.LENGTH_SHORT).show();
                clearForm();
            }
        });

        viewModel.getSaveError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveClientOnly() {
        if (!validateClientForm()) {
            return;
        }

        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText != null ? emailEditText.getText().toString().trim() : "";
        String address = ""; // Not available in current layout

        viewModel.createClient(name, phone, email, address);
    }

    private void saveClientWithLoan() {
        // Not available in current layout - show message
        Toast.makeText(getContext(), "Loan feature not available in current layout", Toast.LENGTH_SHORT).show();
    }

    private boolean validateClientForm() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Client name is required");
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            phoneEditText.setError("Phone number is required");
            return false;
        }

        return true;
    }

    private boolean validateLoanForm() {
        // Not available in current layout
        return false;
    }

    private void clearForm() {
        nameEditText.setText("");
        phoneEditText.setText("");
        if (emailEditText != null) {
            emailEditText.setText("");
        }
        // addressEditText, loan fields not available in current layout
    }
}
