package hango_java.com.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

import hango_java.com.Data.Travel;


public class TravelViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Travel>> liveItems;
    private ArrayList<Travel> items = new ArrayList<>();

    public LiveData<ArrayList<Travel>> getLiveItems(){
        if(liveItems == null)
            liveItems = new MutableLiveData<ArrayList<Travel>>();

        return liveItems;
    }

    public ArrayList<Travel> getList (){
        return items;
    }

    public void add(Travel item){
        items.add(item);

        if(liveItems == null) getLiveItems();

        liveItems.setValue(items);
    }

}
