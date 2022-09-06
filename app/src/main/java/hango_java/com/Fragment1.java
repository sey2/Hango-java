package hango_java.com;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment1 extends Fragment {

    protected RecyclerView todayRecycler, hotSpotRecycler, famousRecycler;
    protected TravelAdapter todayAdapter, hotSpotAdapter, famousAdapter;
   // final protected String URL = "https://korean.visitkorea.or.kr/list/ms_list.do?showcase=9월%20SNS%20인기%20여행지%20Top%2011&compid=85ea6b18-a2ca-486c-a21d-2a09e6562451";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        todayAdapter = new TravelAdapter();
        todayRecycler = rootView.findViewById(R.id.todayRecycler);
        setAdapter(todayAdapter, todayRecycler);
        loadData(container,todayAdapter);

        hotSpotAdapter = new TravelAdapter();
        hotSpotRecycler = rootView.findViewById(R.id.hotRecycler);
        setAdapter(hotSpotAdapter, hotSpotRecycler);
        loadData(container, hotSpotAdapter);

        famousAdapter = new TravelAdapter();
        famousRecycler = rootView.findViewById(R.id.famousRecycler);
        setAdapter(famousAdapter, famousRecycler);
        loadData(container, famousAdapter);

        return rootView;
    }

    public ArrayList<Travel> todayTravelLoadData(){
        ArrayList<Travel> data = new ArrayList<>();

       // data.add(new Travel("제주도","세화 해변", " http://tong.visitkorea.or.kr/cms/resource/78/1559578_image2_1.jpg"));
        /*
        data.add(new Travel("제주도", "아르떼 뮤지엄",
                ResourcesCompat.getDrawable(getResources(), R.drawable.back_3, null)));
        data.add(new Travel("제주도", "태웃개",
                ResourcesCompat.getDrawable(getResources(), R.drawable.back_2, null)));
        data.add(new Travel("제주도", "정월드",
                ResourcesCompat.getDrawable(getResources(), R.drawable.back_4, null)));

         */


        return data;
    }

    public ArrayList<Travel> hotTravelloadData(){
        ArrayList<Travel> data = new ArrayList<>();

        /*
        data.add(new Travel("서울","잠실 롯데월드",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_1, null)));
        data.add(new Travel("전주", "한옥 마을",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_2, null)));
        data.add(new Travel("담양", "죽녹원",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_3, null)));
        data.add(new Travel("부산", "롯데월드",
                ResourcesCompat.getDrawable(getResources(), R.drawable.hot_4, null)));

         */
        return data;
    }

    public ArrayList<Travel> famousRestaurantLoadData(){
        ArrayList<Travel> data = new ArrayList<>();

        /*
        data.add(new Travel("광주","솥밥솥밥",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_1, null)));
        data.add(new Travel("목동", "델리커리",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_2, null)));
        data.add(new Travel("파주", "톰바그",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_3, null)));
        data.add(new Travel("서울", "퓨전 선술집",
                ResourcesCompat.getDrawable(getResources(), R.drawable.famous_4, null)));

         */
        return data;
    }

    private void setAdapter(TravelAdapter adapter, RecyclerView recyclerView){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
    }

    private void loadData(ViewGroup view, TravelAdapter adapter) {
        AQuery aq = new AQuery(view);

        HashMap<String, String> params = new HashMap<>();
        params.put("ServiceKey", "nzMrZtg6lBh%2FJHK%2FQ4bjXqIBHVo92ACZWaS7vQfxGW8KGUEqPRGwh2%2BviL8d4TcHqhsQQV1fZRuoUpNXMPmDQg%3D%3D");
        params.put("numOfRows", "10");
        params.put("pageNo", "1");
        params.put("MobileOS", "ETC");
        params.put("MobileApp", "AppTest");
        params.put("arrange", "P");
        params.put("listYN", "Y");
        params.put("areaCode", "5");//1 서울 //39 제주도 //5 광주 // 6 부산
        params.put("_type", "json");

        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList";
        url = addParams(url, params);

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try{
                            Log.d("Json", "execute");
                            JSONObject response = (JSONObject) jsonObject.get("response");
                            JSONObject body = (JSONObject) response.get("body");
                            JSONObject items = (JSONObject) body.get("items");
                            JSONArray itemArray = items.getJSONArray("item");


                            ArrayList<Travel> arItem = new ArrayList<>();
                            for(int i=0; i<itemArray.length(); i++){
                                JSONObject item = itemArray.getJSONObject(i);
                                Travel travel = new Travel();
                                travel.setSpot(parseAddress(item.getString("addr1")));
                                travel.setAddress(parseTitle(item.getString("title")));
                                travel.setImage(item.getString("firstimage"));
                                travel.setMapX(item.getDouble("mapx"));
                                travel.setMapY(item.getDouble("mapy"));
                                arItem.add(travel);
                                Log.d("Json",item.getString("addr1") + " " +
                                        item.getString("title") + " " + item.getString("firstimage"));
                            }

                            if (arItem.size() > 0) {
                                adapter.listData = arItem;
                                adapter.notifyDataSetChanged();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.d("Json", "error");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private String addParams(String url, HashMap<String, String> mapParam) {
        StringBuilder stringBuilder = new StringBuilder(url + "?");

        if (mapParam != null) {
            for (String key : mapParam.keySet()) {
                stringBuilder.append(key + "=");
                stringBuilder.append(mapParam.get(key) + "&");
            }
        }
        return stringBuilder.toString();
    }

    private String parseAddress(String addr){
        String str[] = addr.split(" ");
        return (str.length >= 1) ? str[0] + str[1]: addr;
    }

    private String parseTitle(String title){
        return (title.length() >= 10 ) ? " " : title;
    }

}