package eslam.emad.sofra.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eslam.emad.sofra.R;
import eslam.emad.sofra.data.models.auth.AuthModel;
import eslam.emad.sofra.data.models.city.City;
import eslam.emad.sofra.data.models.region.Region;
import eslam.emad.sofra.databinding.FragmentClientRegisterBinding;
import eslam.emad.sofra.ui.view_model.ApplicationViewModel;
import eslam.emad.sofra.ui.view_model.ClientRegisterViewModel;
import eslam.emad.sofra.util.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class ClientRegisterFragment extends Fragment {

    private FragmentClientRegisterBinding binding;
    private NavController navController;
    private ClientRegisterViewModel viewModel;
    private ApplicationViewModel applicationViewModel;
    private ArrayList<Region> allRegions;
    private ArrayList<City> allCities;
    private ArrayAdapter<Region> regionArrayAdapter;
    private ArrayAdapter<City> cityArrayAdapter;
    private boolean handle;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mPasswordConfirmation;
    private String mPhone;
    private String mRegionId;
    private static final int RESULT_GALLERY = 0;
    private Uri selectedImage;
    public static final String TAG = "belal";
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_client_register, container, false);
        View view = binding.getRoot();
        handle = false;
        viewModel = new ViewModelProvider(this).get(ClientRegisterViewModel.class);
        applicationViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        viewModel.getClientRegister().observe(getViewLifecycleOwner(), new Observer<AuthModel>() {
            @Override
            public void onChanged(AuthModel authModel) {
                if (handle) {
                    if (authModel != null) {
                        Toast.makeText(getContext(), authModel.getMsg(), Toast.LENGTH_LONG).show();
                        handle = false;
                        if (authModel.getStatus().equals(1)) {
                            navController.navigateUp();
                        }
                    }
                }
            }
        });

        allRegions = new ArrayList<>();
        allCities = new ArrayList<>();
        allRegions.add(new Region(0, "المنطقة"));
        allCities.add(new City(0, "المدينة"));

        applicationViewModel.getAllCities().observe(getViewLifecycleOwner(), new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                if (cities != null) {
                    allCities.clear();
                    allCities.add(new City(0, "المدينة"));
                    allCities.addAll(cities);
                    cityArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        applicationViewModel.getAllRegions().observe(getViewLifecycleOwner(), new Observer<List<Region>>() {
            @Override
            public void onChanged(List<Region> regions) {
                if (regions != null) {
                    allRegions.clear();
                    allRegions.add(new Region(0, "المنطقة"));
                    allRegions.addAll(regions);
                    regionArrayAdapter.notifyDataSetChanged();
                }
            }
        });
        cityArrayAdapter = new ArrayAdapter<City>(getContext(), android.R.layout.simple_spinner_item, allCities);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fragmentClientRegisterCitySpinner.setAdapter(cityArrayAdapter);

        regionArrayAdapter = new ArrayAdapter<Region>(getContext(), android.R.layout.simple_spinner_item, allRegions);
        regionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fragmentRestaurantRegisterRegionSpinner.setAdapter(regionArrayAdapter);

        binding.fragmentClientRegisterCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    int x = ((City) adapterView.getSelectedItem()).getId();
                    applicationViewModel.setAllRegions(x);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.fragmentClientRegisterCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    mRegionId = String.valueOf(((Region) adapterView.getSelectedItem()).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.fragmentClientRegisterShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.fragmentClientRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.fragmentClientRegisterPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    binding.fragmentClientRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.fragmentClientRegisterPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        binding.fragmentClientRegisterUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PermissionChecker.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
                    } else {
                        openGallery();
                    }
                } else {
                    openGallery();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.fragmentClientRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle = true;
                mName = binding.fragmentClientRegisterUserNameEt.getText().toString();
                mEmail = binding.fragmentClientRegisterUserEmailEt.getText().toString();
                mPhone = binding.fragmentClientRegisterUserPhoneEt.getText().toString();
                mPassword = binding.fragmentClientRegisterPassword.getText().toString();
                mPasswordConfirmation = binding.fragmentClientRegisterPasswordAgain.getText().toString();
                if (!(mName.trim().length() >= 1)) {
                    Toast.makeText(getContext(), "Enter a valid name", Toast.LENGTH_SHORT).show();
                } else if (mName.length() >= 50) {
                    Toast.makeText(getContext(), "Enter a short name", Toast.LENGTH_SHORT).show();
                } else if (!(mEmail.contains("@")) || !(mEmail.contains(".com"))) {
                    Toast.makeText(getContext(), "invalid email", Toast.LENGTH_SHORT).show();
                } else if (mPhone.length() != 11 || !mPhone.startsWith("01")) {
                    Toast.makeText(getContext(), "Wrong phone number", Toast.LENGTH_SHORT).show();
                } else if (mRegionId == null) {
                    Toast.makeText(getContext(), "Choose your region", Toast.LENGTH_SHORT).show();
                } else if (!mPassword.equals(mPasswordConfirmation)) {
                    Toast.makeText(getContext(), "Not the Same password", Toast.LENGTH_SHORT).show();
                } else if (mPassword.length() < 4) {
                    Toast.makeText(getContext(), "Password must be bigger than 4 digits", Toast.LENGTH_SHORT).show();
                } else if (selectedImage == null) {
                    Toast.makeText(getContext(), "please choose Image", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody name = RequestBody.create(mName, okhttp3.MultipartBody.FORM);
                    RequestBody email = RequestBody.create(mEmail, okhttp3.MultipartBody.FORM);
                    RequestBody phone = RequestBody.create(mPhone, okhttp3.MultipartBody.FORM);
                    RequestBody regionId = RequestBody.create(mRegionId, okhttp3.MultipartBody.FORM);
                    RequestBody password = RequestBody.create(mPassword, okhttp3.MultipartBody.FORM);
                    RequestBody passwordConfirmation = RequestBody.create(mPasswordConfirmation, okhttp3.MultipartBody.FORM);

                    String path = RealPathUtil.getRealPath(getContext(), selectedImage);
                    File file = new File(path);
                    RequestBody reqFileselect = RequestBody.create(file, MediaType.parse("image/*"));
                    MultipartBody.Part imagePart = MultipartBody.Part.createFormData("profile_image", file.getName(), reqFileselect);

                    viewModel.setClientRegister(name, email, password, passwordConfirmation, phone, regionId, imagePart);
                }
            }
        });
    }

    void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_GALLERY) {
            if (resultCode == RESULT_OK && data != null) {
                selectedImage = data.getData();
                binding.fragmentClientRegisterUserImage.setImageURI(selectedImage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}