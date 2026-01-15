package com.moithuti.funds.ui.clients;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moithuti.funds.R;
import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.ui.common.StatusColorUtil;
import com.moithuti.funds.ui.common.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Adapter - RecyclerView adapter for client list
 * Displays client cards with status indicators
 */
public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private List<ClientEntity> clients = new ArrayList<>();
    private OnClientClickListener onClientClickListener;
    private OnClientLongClickListener onClientLongClickListener;

    public interface OnClientClickListener {
        void onClientClick(ClientEntity client);
    }

    public interface OnClientLongClickListener {
        void onClientLongClick(ClientEntity client);
    }

    public void setOnClientClickListener(OnClientClickListener listener) {
        this.onClientClickListener = listener;
    }

    public void setOnClientLongClickListener(OnClientLongClickListener listener) {
        this.onClientLongClickListener = listener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client_card, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        ClientEntity client = clients.get(position);
        holder.bind(client);
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void updateClients(List<ClientEntity> newClients) {
        clients.clear();
        if (newClients != null) {
            clients.addAll(newClients);
        }
        notifyDataSetChanged();
    }

    public ClientEntity getClientAt(int position) {
        return clients.get(position);
    }

    class ClientViewHolder extends RecyclerView.ViewHolder {
        
        private final android.view.View statusIndicator;
        private final android.widget.TextView clientNameText;
        private final android.widget.TextView clientPhoneText;
        private final android.widget.TextView clientStatusText;
        private final View cardView;

        public ClientViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            
            cardView = itemView;
            statusIndicator = itemView.findViewById(R.id.status_indicator);
            clientNameText = itemView.findViewById(R.id.client_name);
            clientPhoneText = itemView.findViewById(R.id.client_phone);
            clientStatusText = itemView.findViewById(R.id.client_status);
        }

        public void bind(ClientEntity client) {
            // Set client name
            clientNameText.setText(client.getName() != null ? client.getName() : "Unknown");
            
            // Set phone (optional)
            String phone = client.getPhone();
            if (phone != null && !phone.trim().isEmpty()) {
                clientPhoneText.setText(phone);
                clientPhoneText.setVisibility(android.view.View.VISIBLE);
            } else {
                clientPhoneText.setVisibility(android.view.View.GONE);
            }
            
            // Set status
            String status = client.getStatus();
            if (status != null) {
                clientStatusText.setText(StatusColorUtil.getStatusDisplayName(status));
                clientStatusText.setBackgroundColor(StatusColorUtil.getClientStatusColorInt(cardView.getContext(), status));
                clientStatusText.setTextColor(android.graphics.Color.WHITE);
                
                // Set status indicator color
                statusIndicator.setBackgroundColor(StatusColorUtil.getClientStatusColorInt(cardView.getContext(), status));
            } else {
                clientStatusText.setText("Unknown");
                clientStatusText.setBackgroundColor(StatusColorUtil.getClientStatusColorInt(cardView.getContext(), "OWING"));
                statusIndicator.setBackgroundColor(StatusColorUtil.getClientStatusColorInt(cardView.getContext(), "OWING"));
            }
            
            // Set click listener
            cardView.setOnClickListener(v -> {
                if (onClientClickListener != null) {
                    onClientClickListener.onClientClick(client);
                }
            });
            
            // Set long click listener
            cardView.setOnLongClickListener(v -> {
                if (onClientLongClickListener != null) {
                    onClientLongClickListener.onClientLongClick(client);
                    return true;
                }
                return false;
            });
        }
    }
}
