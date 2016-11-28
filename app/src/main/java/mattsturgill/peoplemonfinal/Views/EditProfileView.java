package mattsturgill.peoplemonfinal.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mattsturgill.peoplemonfinal.MainActivity;
import mattsturgill.peoplemonfinal.Model.Account;
import mattsturgill.peoplemonfinal.Model.ImageLoadedEvent;
import mattsturgill.peoplemonfinal.Network.RestClient;
import mattsturgill.peoplemonfinal.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matthewsturgill on 11/27/16.
 */

public class EditProfileView extends LinearLayout {
    private Context context;
    private String selectedImage;
    public Bitmap image;
    public Bitmap originalImage;

    @Bind(R.id.edit_user_name)
    EditText editUserName;

    @Bind(R.id.edit_profile_imageView)
    ImageView editProfileImageView;

    @Bind(R.id.edit_picture_button)
    Button editPictureButton;

    @Bind(R.id.save_button)
    Button saveButton;

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        retrieveAccountInfo();
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.edit_picture_button)
    public void uploadAvatar() {
        ((MainActivity) context).getImage();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelectedImage(ImageLoadedEvent event) {
        String selectedImage = event.selectedImage;
        image = BitmapFactory.decodeFile(selectedImage);
        editProfileImageView.setImageBitmap(image);
    }

    @OnClick(R.id.save_button)
    public void saveProfileChanges() {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editUserName.getWindowToken(), 0);
        final String name = editUserName.getText().toString();
        Account account = new Account(name, selectedImage);
        RestClient restClient = new RestClient();
        restClient.getApiService().postUserInfo(account).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    retrieveAccountInfo();
                    Toast.makeText(context, "Changed Profile Name Successfully", Toast.LENGTH_LONG).show();

                } else {
                    resetView();
                    Toast.makeText(context, "Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resetView();
                Toast.makeText(context, "Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void resetView() {
        saveButton.setEnabled(true);
    }

    private void retrieveAccountInfo() {
        RestClient restClient = new RestClient();
        restClient.getApiService().getUserInfo().enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {

                if (response.isSuccessful()) {
                    Account authUser = response.body();
                    Log.d(authUser.getFullName(), "****RESPONSE NAME****");
                    Log.d(authUser.getEmail(), "****RESPONSE EMAIL****");
                    Log.d(authUser.getAvatarBase64(), "****RESPONSE AVATAR****");

                    //Converting to bitmap
                    String encodedImage = authUser.getAvatarBase64();
                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    editUserName.setText(authUser.getFullName());
                    editProfileImageView.setImageBitmap(decodedByte);

                } else {
                    resetView();
                    Toast.makeText(context, "Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                resetView();
                Toast.makeText(context, "Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }
}
