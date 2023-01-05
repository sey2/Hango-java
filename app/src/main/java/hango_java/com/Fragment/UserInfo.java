package hango_java.com.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.InputStream;

import hango_java.com.R;
import hango_java.com.ViewModel.TravelViewModel;

public class UserInfo extends Fragment {

    private TravelViewModel userModel;
    private ImageView profile;
    private TextView mbtiTextView;
    private TextView nameTextView;
    private Button changImageBtn;
    private ProgressBar expBar;

    private static final int REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_info, container, false);

        userModel = new ViewModelProvider(getActivity()).get(TravelViewModel.class);
        profile = root.findViewById(R.id.home_profile);
        mbtiTextView = root.findViewById(R.id.mbtiTextView);
        nameTextView = root.findViewById(R.id.nameTextView);
        changImageBtn = root.findViewById(R.id.changeImageBtn);
        expBar = root.findViewById(R.id.expBar);

        // 사용자 프로필을 불러온다.
        Glide.with(getContext()).load(userModel.getUserinfo().getValue().getUserProfile()).into(profile);

        // 사용자 Mbti, 이름 설정
        mbtiTextView.setText(userModel.getUserinfo().getValue().getUserMbti());
        nameTextView.setText(userModel.getUserinfo().getValue().getUserName() + "님");

        changImageBtn.setOnClickListener((v) -> {
            /*
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);

             */

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra("crop",true);
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(intent, 104);
        });

        expBar.setProgress(75);

        setProfileFromCloud();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        /*
        if (requestCode == REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    Uri imgUri = data.getData();    // 갤러리에서 사진을 가져와
                    cloudUploadImg(imgUri);     // 클라우드에 이미지 저장
                    profile.setImageURI(imgUri);
                    userModel.getUserinfo().getValue().setProfileUri(imgUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // 취소시 호출할 행동
            }
        }

         */

        switch (requestCode) {
            case 104:  // 사진을 앨범에서 선택하는 경우
                if(resultCode == getActivity().RESULT_OK) {
                    Uri imgUri = intent.getData();
                    cloudUploadImg(imgUri);     // 클라우드에 이미지 저장
                    userModel.getUserinfo().getValue().setProfileUri(imgUri);
                    CropImage.activity(imgUri).setGuidelines(CropImageView.Guidelines.ON).start(getContext(), this);
                }

                break;

            /* 자른 사진을 pictureImageView에 적용 */
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                if (result != null) {
                    Uri resultUri = result.getUri();

                    ContentResolver resolver = getContext().getContentResolver();

                    try {
                        InputStream instream = resolver.openInputStream(resultUri);
                        Bitmap resultPhotoBitmap = BitmapFactory.decodeStream(instream);

                        //resultPhotoBitmap = getRoundedCornerBitmap(resultPhotoBitmap, 20);
                        profile.setImageBitmap(resultPhotoBitmap);

                        instream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                }

        }
    }

    public void cloudUploadImg(Uri imgUri){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String fileName = userModel.getUserinfo().getValue().getUserID() +"profile.png";
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
        StorageReference imgRef = storageRef.child("profile/" + userModel.getUserinfo().getValue().getUserID() +"profile.png");

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