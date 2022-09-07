package hango_java.com;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Fragment1 extends Fragment {

    protected RecyclerView todayRecycler,hotelRecycler, famousRecycler;
    protected TravelAdapter todayAdapter, hotelAdapter, famousAdapter;
    private TravelViewModel model;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        model = new ViewModelProvider(this.getActivity()).get(TravelViewModel.class);

        todayAdapter = new TravelAdapter();
        todayRecycler = rootView.findViewById(R.id.todayRecycler);
        setAdapter(todayAdapter, todayRecycler);
        loadData(container,todayAdapter, "areaBasedList","");

        hotelAdapter = new TravelAdapter();
        hotelRecycler = rootView.findViewById(R.id.hotRecycler);
        setAdapter(hotelAdapter, hotelRecycler);
        loadData(container, hotelAdapter, "searchStay","");

        LocalDate current_date = LocalDate.now();
        String date[] = current_date.toString().split("-");

        famousAdapter = new TravelAdapter();
        famousRecycler = rootView.findViewById(R.id.famousRecycler);
        setAdapter(famousAdapter, famousRecycler);
        loadData(container, famousAdapter, "searchFestival", date[0]+date[1]+date[2]);

        return rootView;
    }


    private void setAdapter(TravelAdapter adapter, RecyclerView recyclerView){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
    }

    private void loadData(ViewGroup view, TravelAdapter adapter, String search, String eventDate) {
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

        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + search;

        url = addParams(url, params);

        if(!eventDate.equals(""))
            url += "&eventStartDate=" + eventDate;

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
                                model.add(travel);
                                Log.d("Json",item.getString("addr1") + " " +
                                        item.getString("title") + " " + item.getString("firstimage") + " " +
                                        item.getDouble("mapx") + " " + item.getDouble("mapy"));
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
        return (title.length() >= 12 ) ? title.substring(0, 12) + ".." : title;
    }



}