package com.example.mafm.sicaklik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mafm.sicaklik.data.model.Weather;
import com.example.mafm.sicaklik.data.remote.WeatherAPI;
import com.yalantis.phoenix.PullToRefreshView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    TextView sicaklik,sonGuncelleme,sehir,condition;
    Button yenile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id();

        refresh();

      


    }

   private void refresh() {
        final PullToRefreshView mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);

                        get();
                    }
                }, 20);
            }
        });
    }

    private void get() {
        WeatherAPI.Factory.getIstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                sicaklik.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getTemp());
                sehir.setText(response.body().getQuery().getResults().getChannel().getLocation().getCity());
                sonGuncelleme.setText(response.body().getQuery().getResults().getChannel().getLastBuildDate());
                condition.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getText());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("Failed",t.getMessage());
            }
        });

    }


    @Override
    protected void onResume() {

        get();
        super.onResume();
    }

    private void id() {
        sicaklik = (TextView) findViewById(R.id.sicaklik);
        sonGuncelleme = (TextView) findViewById(R.id.tarih);
        sehir = (TextView) findViewById(R.id.sehir);
        condition = (TextView) findViewById(R.id.condition);
        yenile = (Button) findViewById(R.id.yenile);


    }
}
