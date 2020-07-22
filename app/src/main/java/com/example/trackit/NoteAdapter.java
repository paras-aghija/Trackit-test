package com.example.trackit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;
    int day;
    String dayName;


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
            day=currentNote.getDay_no();
            switch (day){
                case 1: dayName = "Sunday";
                break;
                case 2: dayName = "Monday";
                    break;
                case 3: dayName = "Tuesday";
                    break;
                case 4: dayName = "Wednesday";
                    break;
                case 5: dayName = "Thursday";
                    break;
                case 6: dayName = "Friday";
                    break;
                case 7: dayName = "Saturday";
                    break;
            }
            holder.textViewTitle.setText(currentNote.getTitle());
            holder.textViewDescription.setText(currentNote.getDescription());
            holder.textViewDay.setText(dayName);
            holder.textViewTime.setText(currentNote.getTime());
            holder.textViewalarmType.setText(currentNote.getAlarmType());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDay;
        private TextView textViewTime;
        private TextView textViewalarmType;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewDay = itemView.findViewById(R.id.day);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewalarmType = itemView.findViewById(R.id.alarmType);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
