package hango_java.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hango_java.com.Data.UserInfoData;
import hango_java.com.ViewModel.UserViewModel;


public class MainActivity extends AppCompatActivity {

    private NavController nav_host;
    private BottomNavigationView nav_bar;
    private UserViewModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_host = Navigation.findNavController(this, R.id.nav_host);
        nav_bar = findViewById(R.id.nav_bar);
        NavigationUI.setupWithNavController(nav_bar, nav_host);

        userModel = new ViewModelProvider(this).get(UserViewModel.class);
        Intent intent = getIntent();
        userModel.setLiveItems(new UserInfoData(intent.getExtras().getString("userID"),
                intent.getExtras().getString("userName"), intent.getExtras().getString("userProfile"),
                intent.getExtras().getString("userMbti")));
    }

}