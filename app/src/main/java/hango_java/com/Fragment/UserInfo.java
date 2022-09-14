package hango_java.com.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import hango_java.com.R;
import hango_java.com.ViewModel.UserViewModel;

public class UserInfo extends Fragment {

    private UserViewModel userModel;
    private ImageView profile;
    private TextView mbtiTextView;
    private TextView nameTextView;
    private Button changImageBtn;

    private static final int REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_info, container, false);

        userModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        profile = root.findViewById(R.id.home_profile);
        mbtiTextView = root.findViewById(R.id.mbtiTextView);
        nameTextView = root.findViewById(R.id.nameTextView);
        changImageBtn = root.findViewById(R.id.changeImageBtn);

        // 사용자 프로필을 불러온다.
        Glide.with(getContext()).load(userModel.getLiveItems().getValue().getUserProfile()).into(profile);

        // 사용자 Mbti, 이름 설정
        mbtiTextView.setText(userModel.getLiveItems().getValue().getUserMbti());
        nameTextView.setText(userModel.getLiveItems().getValue().getUserName() + "님");

        changImageBtn.setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        });

        setProfileFromCloud();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    Uri imgUri = data.getData();    // 갤러리에서 사진을 가져와
                    cloudUploadImg(imgUri);     // 클라우드에 이미지 저장
                    profile.setImageURI(imgUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // 취소시 호출할 행동
            }
        }
    }

    public void cloudUploadImg(Uri imgUri){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String fileName = userModel.getLiveItems().getValue().getUserID() +"profile.png";
        StorageReference storageRef = storage.getReference("profile/" + fileName);

        UploadTask uploadTask = storageRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("test", "sucess");
            }
        });
    }

    private void setProfileFromCloud(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("profile/" + userModel.getLiveItems().getValue().getUserID() +"profile.png");

        if(imgRef != null){
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext()).load(uri).into(profile);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("test", e.toString());
                }
            });
        }
    }


}