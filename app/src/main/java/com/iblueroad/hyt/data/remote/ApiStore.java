package com.iblueroad.hyt.data.remote;

import com.iblueroad.hyt.data.remote.model.GirlResp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by SkyXiao on 2017/9/7.
 */
public interface ApiStore {

    @GET("api/data/福利/10/{index}")
    Call<GirlResp> getGirlsData(@Path("index") int index);

}
