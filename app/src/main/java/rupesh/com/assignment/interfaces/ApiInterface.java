package rupesh.com.assignment.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import rupesh.com.assignment.models.ResultData;

public interface ApiInterface {

    //created API named as getData()
    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<ResultData> getData();
}
