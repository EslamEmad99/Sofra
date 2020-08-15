package eslam.emad.sofra.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import eslam.emad.sofra.R;
import eslam.emad.sofra.databinding.ActivitySplashBinding;
import eslam.emad.sofra.interfaces.SplashActivityOnClick;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class SplashActivity extends AppCompatActivity implements SplashActivityOnClick {

    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        binding.setSplashActivityOnClick(this);
        Animation buttonAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        Animation logoAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_down);
        binding.orderFood.startAnimation(buttonAnimation);
        binding.saleFood.startAnimation(buttonAnimation);
        binding.sofraLogo.startAnimation(logoAnimation);
    }

    @Override
    public void orderFoodButtonClicked() {
        USER_TYPE = UserType.CLIENT;
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void saleFoodButtonClicked() {
        USER_TYPE = UserType.RESTAURANT;
        startActivity(new Intent(this, AuthActivity.class));
    }
}