package hango_java.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import hango_java.com.Data.UserInfoData;
import hango_java.com.ViewModel.TravelViewModel;


public class MainActivity extends AppCompatActivity {

    private NavController nav_host;
    private BottomNavigationView nav_bar;
    private TravelViewModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Activity2", "MainActivity");

        userModel = new ViewModelProvider(this).get(TravelViewModel.class);
        Intent intent = getIntent();
        userModel.setLiveItems(new UserInfoData(intent.getExtras().getString("userID"),
                intent.getExtras().getString("userName"), intent.getExtras().getString("userProfile"),
                intent.getExtras().getString("userMbti")));

        nav_host = Navigation.findNavController(this, R.id.nav_host);
        nav_bar = findViewById(R.id.nav_bar);
        NavigationUI.setupWithNavController(nav_bar, nav_host);
        setProfileFromCloud();

    }

    private void setProfileFromCloud(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("profile/" + userModel.getUserinfo().getValue().getUserID() +"profile.png");

        if(imgRef != null){
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d("callUrl", "Main call");
                    userModel.getUserinfo().getValue().setProfileUri(uri);
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