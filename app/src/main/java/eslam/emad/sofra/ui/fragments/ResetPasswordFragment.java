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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import eslam.emad.sofra.R;
import eslam.emad.sofra.data.models.reset_password.ResetPasswordModel;
import eslam.emad.sofra.databinding.FragmentForgetPasswordBinding;
import eslam.emad.sofra.ui.view_model.ResetPasswordViewModel;
import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class ResetPasswordFragment extends Fragment {

    private FragmentForgetPasswordBinding binding;
    private NavController navController;
    private ResetPasswordViewModel viewModel;
    private boolean handle;
    private String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false);
        View view = binding.getRoot();
        handle = false;
        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        viewModel.getResetPassword().observe(getViewLifecycleOwner(), new Observer<ResetPasswordModel>() {
            @Override
            public void onChanged(ResetPasswordModel resetPasswordModel) {
                if (handle) {
                    if (resetPasswordModel != null) {
                        Toast.makeText(getContext(), resetPasswordModel.getMsg(), Toast.LENGTH_SHORT).show();
                        handle = false;
                        if (resetPasswordModel.getStatus().equals(1)){
                            String code = resetPasswordModel.getResetPasswordData().getCode();
                            ResetPasswordFragmentDirections.ActionForgetPasswordFragmentToNewPasswordFragment action =
                                    ResetPasswordFragmentDirections.actionForgetPasswordFragmentToNewPasswordFragment(code);
                            Toast.makeText(getContext(), code, Toast.LENGTH_LONG).show();
                            navController.navigate(action);
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
        binding.fragmentForgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle = true;
                email = binding.fragmentForgetPasswordEmailEdittext.getText().toString();
                if (!(email.contains("@")) || !(email.contains(".com"))) {
                    Toast.makeText(getContext(), "invalid email", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.setResetPassword(USER_TYPE.name().toLowerCase(), email);
                }
            }
        });
    }
}