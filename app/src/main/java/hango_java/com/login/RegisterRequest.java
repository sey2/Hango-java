package hango_java.com.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://casio2978.dothome.co.kr/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String UserEmail, String UserPwd, String UserName, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", UserEmail);
        map.put("userPassword", UserPwd);
        map.put("userName", UserName);
        map.put("userProfile", "https://user-images.githubusercontent.com/54762273/189822446-e8b21800-6184-456c-8421-24268988b1e5.png");
        map.put("userMbti", "INFJ");
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}