package net.petcontrol.PetControlApi22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterEventsPetcontrol extends
        RecyclerView.Adapter<AdapterEventsPetcontrol.ViewHolder> {
    private final List<EventsPetControl> eventsList;

    // Constructor del Adapter
    public AdapterEventsPetcontrol(List<EventsPetControl> eventsList) {
        this.eventsList = eventsList;
    }
    // Crea nuevas vistas inflando el diseño para cada elemento del RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla el layout para el elemento del RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_events_petcontrol, parent, false);
        return new ViewHolder(view);
    }

    // Toma el elemento correspondiente de la lista y configura las vistas con los datos
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EventsPetControl notification = eventsList.get(position);
        //holder.date.setText(notification.getDate());
        holder.content.setText(notification.getContent());
    }
    // Devuelve el número total de elementos en la lista
    @Override
    public int getItemCount() {
        return eventsList.size();
    }


    // Clase ViewHolder para definir la estructura de cada elemento
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView title;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            // Vincular las vistas por sus IDs
            date = itemView.findViewById(R.id.txtDateEvents);
            title = itemView.findViewById(R.id.txtTitleEvents);
            content = itemView.findViewById(R.id.txtContentEvents);
        }
    }
}