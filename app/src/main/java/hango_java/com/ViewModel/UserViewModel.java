package hango_java.com.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import hango_java.com.Data.UserInfoData;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserInfoData> liveItems;


    public LiveData<UserInfoData> getLiveItems(){
        if(liveItems == null)
            liveItems = new MutableLiveData<UserInfoData>();

        return liveItems;
    }

    public void setLiveItems(UserInfoData item){

        if(liveItems == null) getLiveItems();

        liveItems.setValue(item);

    }

}
