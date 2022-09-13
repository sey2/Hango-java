package hango_java.com.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import hango_java.com.R;
import hango_java.com.ViewModel.UserViewModel;

public class UserInfo extends Fragment {

    private UserViewModel userModel;
    private ImageView profile;
    private TextView mbtiTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_info, container, false);

        userModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        profile = root.findViewById(R.id.profile);
        mbtiTextView = root.findViewById(R.id.mbtiTextView);

        // 사용자 프로필을 불러온다.
        Glide.with(getContext()).load(userModel.getLiveItems().getValue().getUserProfile()).into(profile);

        // 사용자 Mbti Text를 불러온다.
        mbtiTextView.setText(userModel.getLiveItems().getValue().getUserMbti());



        return root;
    }
}