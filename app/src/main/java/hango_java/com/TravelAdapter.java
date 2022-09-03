package hango_java.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<Holder> {

    public ArrayList<Travel> listData = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recommend_item, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Travel travel = listData.get(position);
        holder.setTravel(travel);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}

class Holder extends RecyclerView.ViewHolder{

    private TextView cityTextView;
    private TextView spotTextView;
    private RelativeLayout background;

    public Holder(View itemView){
        super(itemView);

        cityTextView = itemView.findViewById(R.id.cityTextView);
        spotTextView = itemView.findViewById(R.id.spotTextView);
        background = itemView.findViewById(R.id.relativeLayout);

        itemView.setOnClickListener((v)->{
            Toast.makeText(itemView.getContext(),"클릭됨", Toast.LENGTH_SHORT).show();
        });
    }

    public void setTravel(Travel travel){
        cityTextView.setText(travel.city);
        spotTextView.setText(travel.spot);
        background.setBackground(travel.img);
    }
}