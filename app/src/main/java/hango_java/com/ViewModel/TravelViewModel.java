package hango_java.com.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

import hango_java.com.Data.Travel;
import hango_java.com.Data.UserInfoData;


public class TravelViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Travel>> travelItems;
    private MutableLiveData<UserInfoData> userInfoItem;
    private ArrayList<Travel> items = new ArrayList<>();

    public LiveData<ArrayList<Travel>> getTravelLiveItems(){
        if(travelItems == null)
            travelItems = new MutableLiveData<ArrayList<Travel>>();

        return travelItems;
    }

    public ArrayList<Travel> getList (){
        return items;
    }

    public void add(Travel item){
        items.add(item);

        if(travelItems == null) getTravelLiveItems();

        travelItems.setValue(items);
    }

    public void deleteList(){items = new ArrayList<>();}


    public MutableLiveData<UserInfoData> getUserinfo(){
        if(userInfoItem == null)
            userInfoItem = new MutableLiveData<UserInfoData>();

        return userInfoItem;
    }

    public void setLiveItems(UserInfoData item){

        if(userInfoItem == null) getUserinfo();

        userInfoItem.setValue(item);

    }

}
