package com.example.mynotes.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import com.example.mynotes.Models.Notes;
import com.example.mynotes.NotesClickListener;
import com.example.mynotes.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter <NotesViewHolden> {
    Context context;
    List<Notes> list;

    NotesClickListener listener;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolden onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolden(LayoutInflater.from(context).inflate(R.layout.notes_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolden holder, int position) {

        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        holder.textView_notes.setText(list.get(position).getNotes());

        if (list.get(position).isPinned()) {
            holder.imageView_pin.setImageResource(R.drawable.pin_icon);
        } else {
            holder.imageView_pin.setImageResource(0);
        }
        int color_code = getRandomColor();
        holder.notes_conteiner.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));
        holder.notes_conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));

            }
        });
        holder.notes_conteiner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_conteiner);
                return true;
            }
        });


    }
    private  int getRandomColor() {
        List <Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.Aquamarine);
        colorCode.add(R.color.MediumSlateBlue);
        colorCode.add(R.color.DarkTurquoise);
        colorCode.add(R.color.Thistle);
        colorCode.add(R.color.SlateBlue);
        colorCode.add(R.color.Salmon);
        colorCode.add(R.color.DarkCyan);
        colorCode.add(R.color.LightSeaGreen);
        colorCode.add(R.color.PaleGreen);
        colorCode.add(R.color.Plum);
        colorCode.add(R.color.Cyan);
        colorCode.add(R.color.SpringGreen);
        colorCode.add(R.color.HotPink);
        colorCode.add(R.color.Violet);
        colorCode.add(R.color.LightCoral);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {return list.size();}

    public void filterList(List<Notes> filterList){
        list = filterList;
        notifyDataSetChanged();
    }
}

class NotesViewHolden extends RecyclerView.ViewHolder {

    CardView notes_conteiner;
    TextView textView_title;
    TextView textView_notes;
    TextView textView_date;
    ImageView imageView_pin;

    public  NotesViewHolden (@NonNull View itemView){
        super(itemView);
        notes_conteiner = itemView.findViewById(R.id.notes_conteiner);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textView_date = itemView.findViewById(R.id.textView_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}
