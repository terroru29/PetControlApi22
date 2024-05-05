package net.petcontrol.PetControlApi22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterNotificationsPetcontrol extends
        RecyclerView.Adapter<AdapterNotificationsPetcontrol.ViewHolder> {
    private List<NotificationsPetControl> notificationsList;

    // Constructor del Adapter
    public AdapterNotificationsPetcontrol(List<NotificationsPetControl> notificationsList) {
        this.notificationsList = notificationsList;
    }
    // Crea nuevas vistas inflando el diseño para cada elemento del RecyclerView
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla el layout para el elemento del RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notifications_petcontrol, parent, false);
        return new ViewHolder(view);
    }

    // Toma el elemento correspondiente de la lista y configura las vistas con los datos
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationsPetControl notification = notificationsList.get(position);
        holder.date.setText(notification.getDate());
        holder.content.setText(notification.getContent());
    }
    // Devuelve el número total de elementos en la lista
    @Override
    public int getItemCount() {
        return notificationsList.size();
    }


    // Clase ViewHolder para definir la estructura de cada elemento
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            // Vincular las vistas por sus IDs
            date = itemView.findViewById(R.id.txtDateNotifications);
            content = itemView.findViewById(R.id.txtContentNotifications);
        }
    }
}