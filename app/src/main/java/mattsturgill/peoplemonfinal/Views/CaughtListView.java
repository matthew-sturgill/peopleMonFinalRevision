package mattsturgill.peoplemonfinal.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import mattsturgill.peoplemonfinal.Adapter.CaughtListAdapter;
import mattsturgill.peoplemonfinal.Model.User;
import mattsturgill.peoplemonfinal.Network.RestClient;
import mattsturgill.peoplemonfinal.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matthewsturgill on 11/27/16.
 */

public class CaughtListView extends RelativeLayout {
    private Context context;
    private CaughtListAdapter peopleCaughtAdapter;

    @Bind(R.id.caught_recycler)
    RecyclerView recyclerView;

    public CaughtListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        peopleCaughtAdapter = new CaughtListAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(peopleCaughtAdapter);
        listCaughtPeople();
    }

    private void listCaughtPeople() {
        RestClient restClient = new RestClient();
        restClient.getApiService().caught().enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()) {
                    peopleCaughtAdapter.caughtUsers = new ArrayList<>(Arrays.asList(response.body()));
                    for (User user : peopleCaughtAdapter.caughtUsers) {

                        peopleCaughtAdapter.notifyDataSetChanged();
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
