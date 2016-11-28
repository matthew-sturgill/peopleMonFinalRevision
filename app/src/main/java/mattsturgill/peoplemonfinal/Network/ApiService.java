package mattsturgill.peoplemonfinal.Network;

import com.google.android.gms.maps.model.LatLng;

import mattsturgill.peoplemonfinal.Model.Account;
import mattsturgill.peoplemonfinal.Model.Authorization;
import mattsturgill.peoplemonfinal.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public interface ApiService {

    @POST("/api/Account/Register")
    Call<Void> register(@Body Account account);

    @FormUrlEncoded
    @POST("token")
    Call<Authorization> login(@Field("grant_type") String grantType,
                              @Field("username") String email,
                              @Field("password") String password);

    @POST("/api/Account/UserInfo")
    Call<Void> postUserInfo(@Body Account account);

    @POST("/v1/User/CheckIn")
    Call<Void> checkIn(@Body LatLng latLng);

    @GET("/v1/User/Caught")
    Call<User[]> caught();

    @GET("/v1/User/Nearby")
    Call<User[]> nearBy(@Query("radiusInMeters") Integer radiusInMeters);

    @GET("/api/Account/UserInfo")
    Call<Account> getUserInfo();

    @FormUrlEncoded
    @POST("v1/User/Catch")
    Call<Void> catchUser(@Field("CaughtUserId") String userId,
                         @Field("RadiusInMeters") Integer radius);

//    @POST("/api/Account/Logout")
//    Call<Void> logOut;
}
