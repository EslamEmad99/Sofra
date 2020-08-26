package eslam.emad.sofra.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import eslam.emad.sofra.R;
import eslam.emad.sofra.util.SharedPreferencesManger;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.CLIENT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.RESTAURANT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "belal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (USER_TYPE == UserType.CLIENT) {

        } else if (USER_TYPE == UserType.RESTAURANT) {
            if (SharedPreferencesManger.getINSTANCE(this).getBooleanValue(RESTAURANT_LOGGED_IN)){
                goToHomeActivity();
            }
        }

        setContentView(R.layout.activity_auth);
    }

    public void goToHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }
}