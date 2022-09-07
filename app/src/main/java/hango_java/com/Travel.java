package hango_java.com;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.lifecycle.ViewModel;

public class Travel {
    String city;
    String spot;
    String img;
    Double mapX;
    Double mapY;

    public Travel(String city, String spot, String img){
        this.city = city;
        this.spot = spot;
        this.img = img;
    }

    public Travel(){}

    public void setAddress(String addr){ this.city = addr; }
    public void setSpot(String spot){this.spot = spot;}
    public void setMapX(Double maxX) {this.mapX = maxX;}
    public void setMapY(Double mapY) {this.mapY = mapY;}
    public void setImage(String img){this.img = img;}

}
