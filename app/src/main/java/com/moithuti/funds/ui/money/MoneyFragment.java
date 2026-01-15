package com.moithuti.funds.ui.money;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moithuti.funds.R;

/**
 * Money Fragment - Investor management and loan funding
 * Add/edit investors, set monthly investments, issue loans with funding
 */
public class MoneyFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_money, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // TODO: Setup investor list
        // TODO: Setup loan funding interface
        // TODO: Setup investor detail navigation
    }
}
