package mattsturgill.peoplemonfinal.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import mattsturgill.peoplemonfinal.Adapter.NearbyAdapter;
import mattsturgill.peoplemonfinal.Constants.Constants;
import mattsturgill.peoplemonfinal.Model.User;
import mattsturgill.peoplemonfinal.Network.RestClient;
import mattsturgill.peoplemonfinal.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class NearView extends LinearLayout {
    private Context context;
    private NearbyAdapter nearbyAdapter;

    @Bind(R.id.nearby_recycler)
    RecyclerView nearbyRecyclerView;

    public NearView(Context context, AttributeSet attrs, NearbyAdapter nearbyAdapter) {
        super(context, attrs);
        this.context = context;
        this.nearbyAdapter = nearbyAdapter;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        nearbyAdapter = new NearbyAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        nearbyRecyclerView.setLayoutManager(linearLayoutManager);
        nearbyRecyclerView.setAdapter(nearbyAdapter);
        listNearbyPeople();
    }

    private void listNearbyPeople() {
        RestClient restClient = new RestClient();
        restClient.getApiService().nearBy(Constants.radiusInMeters).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()) {
                    nearbyAdapter.nearbyUsers = new ArrayList<>(Arrays.asList(response.body()));
                    for (User user : nearbyAdapter.nearbyUsers) {

                        nearbyAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(context, R.string.profile_info_error + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, R.string.profile_info_error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
