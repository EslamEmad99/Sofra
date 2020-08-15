package eslam.emad.sofra.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import eslam.emad.sofra.R;
import eslam.emad.sofra.databinding.ActivityHomeBinding;

import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setUserType(String.valueOf(USER_TYPE));
    }
}