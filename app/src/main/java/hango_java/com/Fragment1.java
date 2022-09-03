package hango_java.com;

import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

    protected RecyclerView todayRecycler;
    protected RecyclerView hotSpotRecycler;
    protected RecyclerView famousRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        TravelAdapter todayAdapter = new TravelAdapter();
        todayAdapter.listData = todayTravelLoadData();
        todayRecycler = rootView.findViewById(R.id.todayRecycler);
        todayRecycler.setAdapter(todayAdapter);
        todayRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));

        TravelAdapter hotSpotAdapter = new TravelAdapter();
        hotSpotAdapter.listData = hotTravelloadData();
        hotSpotRecycler = rootView.findViewById(R.id.hotRecycler);
        hotSpotRecycler.setAdapter(hotSpotAdapter);
        hotSpotRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));

        TravelAdapter famousAdapter = new TravelAdapter();
        famousAdapter.listData = hotTravelloadData();
        famousRecycler = rootView.findViewById(R.id.famousRecycler);
        famousRecycler.setAdapter(famousAdapter);
        famousRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));

        return rootView;
    }

    public ArrayList<Travel> todayTravelLoadData(){
        ArrayList<Travel> data = new ArrayList<>();

        data.add(new Travel("제주도","세화 해변",
                ResourcesCompat.getDrawable(getResources(), R.drawable.back_1, null)));
        data.add(new Travel("제주도", "아르떼 뮤지엄",
                ResourcesCompat.getDrawable(getResources(), R.drawable.back_3, null)));
        data.add(new Travel("제주도", "태웃개",
                ResourcesCompat.getDrawable(getResources(), R.drawable.back_2, null)));
        data.add(new Travel("제주도", "정월드",
                ResourcesCompat.getDrawable(getResources(), R.drawable.back_4, null)));

        return data;
    }

    public ArrayList<Travel> hotTravelloadData(){
        ArrayList<Travel> data = new ArrayList<>();

        data.add(new Travel("서울","잠실 롯데월드",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_1, null)));
        data.add(new Travel("전주", "한옥 마을",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_2, null)));
        data.add(new Travel("담양", "죽녹원",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_3, null)));
        data.add(new Travel("부산", "롯데월드",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_4, null)));

        return data;
    }

    public ArrayList<Travel> famousRestaurantLoadData(){
        ArrayList<Travel> data = new ArrayList<>();

        data.add(new Travel("광주","솥밥솥밥",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_1, null)));
        data.add(new Travel("목동", "델리커리",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_2, null)));
        data.add(new Travel("파주", "톰바그",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_3, null)));
        data.add(new Travel("서울", "퓨전 선술집",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_4, null)));

        return data;
    }

}