package hango_java.com;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<Holder> {

    public ArrayList<Travel> listData = new ArrayList<>();
    private View context;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent;
        View itemView = inflater.inflate(R.layout.recommend_item, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Travel travel = listData.get(position);
        holder.setTravel(travel, context);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public ArrayList<Travel> getItemList(){return listData;}
}

class Holder extends RecyclerView.ViewHolder{

    private TextView cityTextView;
    private TextView spotTextView;
    private RelativeLayout background;
    private ImageView imageView;

    public Holder(View itemView){
        super(itemView);

        cityTextView = itemView.findViewById(R.id.cityTextView);
        spotTextView = itemView.findViewById(R.id.spotTextView);
        background = itemView.findViewById(R.id.relativeLayout);
        imageView = itemView.findViewById(R.id.backgroundImg);
        itemView.setOnClickListener((v)->{
            Toast.makeText(itemView.getContext(),"클릭됨", Toast.LENGTH_SHORT).show();
        });

    }

    public void setTravel(Travel travel, View context){
        cityTextView.setText(travel.city);
        spotTextView.setText(travel.spot);
        Glide.with(context).load(travel.img).into(imageView);
    }
}