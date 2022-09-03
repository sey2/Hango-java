package hango_java.com;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class Travel {
    String city;
    String spot;
    Drawable img;

    public Travel(String city, String spot, Drawable img){
        this.city = city;
        this.spot = spot;
        this.img = img;
    }
}
