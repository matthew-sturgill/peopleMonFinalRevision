package mattsturgill.peoplemonfinal.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import mattsturgill.peoplemonfinal.Constants.Constants;
import mattsturgill.peoplemonfinal.Model.Authorization;
import mattsturgill.peoplemonfinal.Network.RestClient;
import mattsturgill.peoplemonfinal.Network.UserStore;
import mattsturgill.peoplemonfinal.R;
import mattsturgill.peoplemonfinal.Stages.MapStage;
import mattsturgill.peoplemonfinal.Stages.RegisterStage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static mattsturgill.peoplemonfinal.PeoplemonApplication.getMainFlow;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class LoginView extends LinearLayout {
    private Context context;

    @Bind(R.id.login_username)
    EditText userNameField;

    @Bind(R.id.login_password)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.login_register_button)
    Button loginRegisterButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_register_button)
    public void showRegisterView() {
        Flow flow = getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }

    @OnClick(R.id.login_button)
    public void login() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userNameField.getWindowToken(), 0);//used to hide keyboard after input
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);//terrible.

        String username = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        String grantType = "password";

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, R.string.empty_username_or_password, Toast.LENGTH_LONG).show();
        } else {
            loginButton.setEnabled(false);
            loginRegisterButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            RestClient restClient = new RestClient();
            restClient.getApiService().login(Constants.grant_type, username,
                    password).enqueue(new Callback<Authorization>() {

                @Override
                public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                    if (response.isSuccessful()) {
                        Authorization authUser = response.body();
                        UserStore.getInstance().setToken(authUser.getToken());
                        UserStore.getInstance().setTokenExpiration(authUser.getExpiration());

                        Flow flow = getMainFlow();
                        History newHistory = History.single(new MapStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                    } else {
                        resetView();
                        Toast.makeText(context, context.getResources().getString(R.string.login_failed) + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Authorization> call, Throwable t) {
                    resetView();
                    Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void resetView() {
        loginButton.setEnabled(true);
        loginRegisterButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}
