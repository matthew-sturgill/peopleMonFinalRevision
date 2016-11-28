package mattsturgill.peoplemonfinal.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Base64;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import mattsturgill.peoplemonfinal.Constants.Constants;
import mattsturgill.peoplemonfinal.MainActivity;
import mattsturgill.peoplemonfinal.Model.User;
import mattsturgill.peoplemonfinal.Network.RestClient;
import mattsturgill.peoplemonfinal.PeoplemonApplication;
import mattsturgill.peoplemonfinal.R;
import mattsturgill.peoplemonfinal.Stages.CaughtListStage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class MapPageView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener  {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Handler handler = new Handler();
    private LatLng latLng;
    private Context context;
    private ArrayList<User> peopleMon;

    @Bind(R.id.googleMapView)
    MapView map;

    @Bind(R.id.checkInButton)
    FloatingActionButton radarFAB;

    @Bind(R.id.caught_fab)
    FloatingActionButton caughtFAB;

    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        map.onCreate(((MainActivity) getContext()).savedInstanceState);
        map.onResume();
        map.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {handler.post(checkForLocationChange);}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public boolean onMarkerClick(final Marker marker) {
        RestClient restClient = new RestClient();
        restClient.getApiService().catchUser(marker.getTitle(), Constants.radiusInMeters).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, R.string.got_em, Toast.LENGTH_SHORT).show();
                    marker.remove();
//                    caughtUsers();
                } else {
                    Toast.makeText(context, R.string.cant_catch, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, R.string.ran_away, Toast.LENGTH_SHORT).show();

            }
        });

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setPadding(10, 10, 10, 10);

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Toast.makeText(context, R.string.location_failed, Toast.LENGTH_SHORT).show();
        }

        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
    }

    public void checkForNearby() {
        final RestClient restClient = new RestClient();
        restClient.getApiService().nearBy(Constants.radiusInMeters).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, R.string.checkinNearBy, Toast.LENGTH_SHORT).show();

                    peopleMon = new ArrayList<>(Arrays.asList(response.body()));

                    for (final User user : peopleMon) {

                        if ((user.getAvatarBase64() == null) || (user.getAvatarBase64().length() <= 50)) {

                            LatLng latLng = new LatLng(user.getLatitude(), user.getLongitude());
                            mMap.addMarker(new MarkerOptions().title(user.getUserId()).position(latLng)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokemon52)));

                        } else {

                            String encodedImage = user.getAvatarBase64();
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            try {
                                decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);

                                LatLng loc = new LatLng(user.getLatitude(), user.getLongitude());

                                mMap.addMarker(new MarkerOptions().title(user.getUserId())
                                        .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                        .snippet(user.getUserId())
                                        .position(loc));
                            } catch (Exception e) {}
                        }
                    }

                } else {
                    Toast.makeText(context, R.string.no_peoplemon_near_you, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, R.string.find_user_failed, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @OnClick(R.id.caught_fab)
    public void caughtView() {
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new CaughtListStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    Runnable checkForLocationChange = new Runnable() {
        @Override
        public void run() {
                try {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    if (mLastLocation != null) {
                        checkInLocation();
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
    };

    @OnClick(R.id.checkInButton)
    public void checkInLocation() {
        latLng = new LatLng(mLastLocation.getLatitude(),
                mLastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(getContext().getString(R.string.user))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.wolf)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));

        final RestClient restClient = new RestClient();
        restClient.getApiService().checkIn(latLng).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    checkForNearby();

                } else {
                    Toast.makeText(context, R.string.check_in_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, R.string.check_in_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
