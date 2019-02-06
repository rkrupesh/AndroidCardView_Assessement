package rupesh.com.assignment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rupesh.com.assignment.adapter.RecyclerAdapter;
import rupesh.com.assignment.utils.ApiClient;
import rupesh.com.assignment.interfaces.ApiInterface;
import rupesh.com.assignment.models.Dictionary;
import rupesh.com.assignment.models.ResultData;

public class MainActivity extends AppCompatActivity {

    private TextView tittle, description;
    ImageView image_pic;
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private List<Dictionary> mResultData = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private Boolean getNetworkStatus;
    private SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        tittle = (TextView) findViewById(R.id.tv_tittle);
        description = (TextView) findViewById(R.id.description);
        image_pic = (ImageView) findViewById(R.id.image_pic);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        getNetworkStatus = networkStatus();
      //  Log.d(TAG,"network status is" + getNetworkStatus);
        updatedData();
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updatedData();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    //To fetch the values and set it to the adapter.
    private void updatedData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        Call<ResultData> call = apiService.getData();
        call.enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                if (response != null && response.body() != null && response.body().getRows() != null) {
                  //  Log.d(TAG,"all ok");
                    try {
                        mResultData = response.body().getRows();
                        getSupportActionBar().setTitle(response.body().getTitle().toString());
                        recyclerAdapter = new RecyclerAdapter(mResultData);
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, getString(R.string.data_not_found));
                }
            }
            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), getString(R.string.network_status), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(getApplicationContext(),R.string.portrait,Toast.LENGTH_LONG).show();
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(getApplicationContext(),R.string.landscape,Toast.LENGTH_LONG).show();
        }
    }

    // Used to check the network status.
    private boolean networkStatus(){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager connection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = connection.getAllNetworkInfo();
        for (NetworkInfo data : netInfo) {
            if (data.getTypeName().equalsIgnoreCase("WIFI"))
                if (data.isConnected())
                    haveConnectedWifi = true;
            if (data.getTypeName().equalsIgnoreCase("MOBILE"))
                if (data.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}