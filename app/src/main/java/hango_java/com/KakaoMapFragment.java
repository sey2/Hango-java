package hango_java.com;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;


public class KakaoMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kakao_map, container, false);

        MapView mapView = new MapView(root.getContext());
        ViewGroup mapViewContainer = root.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        return root;
    }
}