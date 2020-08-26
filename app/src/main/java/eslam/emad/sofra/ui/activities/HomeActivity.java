package eslam.emad.sofra.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import eslam.emad.sofra.R;
import eslam.emad.sofra.databinding.ActivityHomeBinding;
import eslam.emad.sofra.interfaces.ActivityHomeOnClick;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class HomeActivity extends AppCompatActivity implements ActivityHomeOnClick {

    ActivityHomeBinding binding;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.home_bottom_nav);
        navController = Navigation.findNavController(this, R.id.home_nav_host);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        if(USER_TYPE == UserType.CLIENT){
            binding.activityHomeCalculater.setVisibility(View.GONE);
            binding.activityHomeCart.setVisibility(View.VISIBLE);
        } else if (USER_TYPE == UserType.RESTAURANT){
            binding.activityHomeCalculater.setVisibility(View.VISIBLE);
            binding.activityHomeCart.setVisibility(View.GONE);
        }
        binding.setOnClick(this);
    }

    @Override
    public void onNotificationClick() {
        NavOptions navOptions = new NavOptions.Builder().setLaunchSingleTop(true).build();
        navController.navigate(R.id.notificationsFragment, null, navOptions);
    }

    @Override
    public void onCartClick() {
        Toast.makeText(this, "onCartClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalculatorClick() {
        Toast.makeText(this, "onCalculatorClick", Toast.LENGTH_SHORT).show();
    }
}