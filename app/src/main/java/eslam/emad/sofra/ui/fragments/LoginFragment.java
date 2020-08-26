package eslam.emad.sofra.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import eslam.emad.sofra.R;
import eslam.emad.sofra.data.models.auth.AuthModel;
import eslam.emad.sofra.databinding.FragmentLoginBinding;
import eslam.emad.sofra.ui.activities.HomeActivity;
import eslam.emad.sofra.ui.view_model.LoginFragmentViewModel;
import eslam.emad.sofra.util.SharedPreferencesManger;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.CLIENT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.RESTAURANT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private NavController navController;
    private LoginFragmentViewModel viewModel;
    private boolean handle;
    private String email;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        View view = binding.getRoot();
        handle = false;
        viewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);
        viewModel.getLoginModel().observe(getViewLifecycleOwner(), new Observer<AuthModel>() {
            @Override
            public void onChanged(AuthModel authModel) {
                if (handle) {
                    if (authModel != null) {
                        Toast.makeText(getContext(), authModel.getMsg(), Toast.LENGTH_SHORT).show();
                        handle = false;
                        if (authModel.getStatus().equals(1)){
                            if (USER_TYPE == UserType.CLIENT) {
                                //go to Cart Fragment
                                SharedPreferencesManger.getINSTANCE(getContext()).setBooleanValue(CLIENT_LOGGED_IN, true);
                            } else if (USER_TYPE == UserType.RESTAURANT) {
                                SharedPreferencesManger.getINSTANCE(getContext()).setBooleanValue(RESTAURANT_LOGGED_IN, true);
                            }
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        binding.fragmentLoginHaveYouForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginFragment_to_forgetPasswordFragment);
            }
        });

        binding.fragmentLoginEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle = true;
                email = binding.fragmentLoginEmailTv.getText().toString();
                password = binding.fragmentLoginPasswordTv.getText().toString();
                if (!(email.contains("@")) || !(email.contains(".com"))) {
                    Toast.makeText(getContext(), "invalid email", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 4) {
                    Toast.makeText(getContext(), "Password must be bigger than 4 digits", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.setLoginModel(USER_TYPE.name().toLowerCase(), email, password);
                }
            }
        });

        binding.fragmentLoginDoNotHaveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USER_TYPE == UserType.CLIENT) {
                    navController.navigate(R.id.action_loginFragment_to_clientRegisterFragment);
                } else if (USER_TYPE == UserType.RESTAURANT) {
                    navController.navigate(R.id.action_loginFragment_to_restaurantRegisterFragment);
                }
            }
        });

        binding.fragmentLoginShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.fragmentLoginPasswordTv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    binding.fragmentLoginPasswordTv.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }
}