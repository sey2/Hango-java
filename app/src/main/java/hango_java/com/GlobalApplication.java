package hango_java.com;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        KakaoSdk.init(this, "fc840073cb80861950802cae8d4b0237");
    }
}