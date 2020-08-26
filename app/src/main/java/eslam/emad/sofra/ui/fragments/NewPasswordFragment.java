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
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import eslam.emad.sofra.R;
import eslam.emad.sofra.data.models.new_password.NewPasswordModel;
import eslam.emad.sofra.databinding.FragmentNewPasswordBinding;
import eslam.emad.sofra.ui.activities.HomeActivity;
import eslam.emad.sofra.ui.view_model.LoginFragmentViewModel;
import eslam.emad.sofra.ui.view_model.NewPasswordViewModel;
import eslam.emad.sofra.util.SharedPreferencesManger;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.CLIENT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.RESTAURANT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class NewPasswordFragment extends Fragment {

    private FragmentNewPasswordBinding binding;
    private NavController navController;
    private NewPasswordViewModel viewModel;
    private String code;
    private String password;
    private String passwordConfirmation;
    private boolean handle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_password, container, false);
        View view = binding.getRoot();
        handle = false;
        viewModel = new ViewModelProvider(this).get(NewPasswordViewModel.class);
        viewModel.getNewPassword().observe(getViewLifecycleOwner(), new Observer<NewPasswordModel>() {
            @Override
            public void onChanged(NewPasswordModel newPasswordModel) {
                if (handle) {
                    if (newPasswordModel != null) {
                        Toast.makeText(getContext(), newPasswordModel.getMsg(), Toast.LENGTH_SHORT).show();
                        handle = false;
                        if (newPasswordModel.getStatus().equals(1)) {
                            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true)
                                    .setEnterAnim(R.anim.slide_in_left)
                                    .setExitAnim(R.anim.slide_out_right)
                                    .setPopEnterAnim(R.anim.slide_in_left)
                                    .setPopExitAnim(R.anim.slide_out_right)
                                    .setLaunchSingleTop(true)
                                    .build();

                            navController.navigate(R.id.action_newPasswordFragment_to_loginFragment, null, navOptions);
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
        if (getArguments() != null) {
            NewPasswordFragmentArgs args = NewPasswordFragmentArgs.fromBundle(getArguments());
            code = args.getCode();
        }
        navController = Navigation.findNavController(view);
        binding.fragmentNewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle = true;
                if (code.equals(binding.fragmentNewPasswordCode.getText().toString())) {
                    password = binding.fragmentNewPasswordPassword.getText().toString();
                    passwordConfirmation = binding.fragmentNewPasswordPasswordAgain.getText().toString();
                    if (!password.equals(passwordConfirmation)) {
                        Toast.makeText(getContext(), "Not the Same password", Toast.LENGTH_SHORT).show();
                    } else if (password.length() < 4) {
                        Toast.makeText(getContext(), "Password must be bigger than 4 digits", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.setNewPassword(USER_TYPE.name().toLowerCase(), code, password, passwordConfirmation);
                    }
                } else {
                    Toast.makeText(getContext(), "Code not correct\ncheck your email", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.fragmentNewPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.fragmentNewPasswordPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.fragmentNewPasswordPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    binding.fragmentNewPasswordPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.fragmentNewPasswordPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }
}