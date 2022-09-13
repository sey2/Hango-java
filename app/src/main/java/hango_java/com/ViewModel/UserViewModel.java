package hango_java.com.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import hango_java.com.Data.UserInfo;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserInfo> liveItems;


    public LiveData<UserInfo> getLiveItems(){
        if(liveItems == null)
            liveItems = new MutableLiveData<UserInfo>();

        return liveItems;
    }

    public void setLiveItems(UserInfo item){

        if(liveItems == null) getLiveItems();

        liveItems.setValue(item);

    }

}
