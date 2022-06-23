package cl.ione.globallogic.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import cl.ione.globallogic.Adapter.NewsAdapter;
import cl.ione.globallogic.Data.NewsData;
import cl.ione.globallogic.R;
import cl.ione.globallogic.Utilities.ApiConnection;
import cl.ione.globallogic.Utilities.CloudConnect;
import cl.ione.globallogic.Utilities.Messages;
import cl.ione.globallogic.Utilities.Util;

public class MainActivity extends AppCompatActivity {

    //Controls
    private ProgressBar mProgress;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mListView;

    //Variables
    private Context mContext;
    private List<NewsData> mNewsData = new ArrayList<>();
    private NewsAdapter mAdapter;
    private final ArrayList<NewsData> mResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mProgress = findViewById(R.id.list_progress);
        mSwipeRefresh = findViewById(R.id.list_swipe);
        mSwipeRefresh.setOnRefreshListener(() -> {
            mSwipeRefresh.setRefreshing(true);
            mResults.clear();
            getApiData();
        });

        //Lista config
        mListView = findViewById(R.id.list_data);
        mListView.setNestedScrollingEnabled(false);
        mListView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mListView.setLayoutManager(mLayoutManager);
        mAdapter = new NewsAdapter(mContext, getDataSet());

        getApiData();
    }

    //Get results
    private List<NewsData> getDataSet() {
        return mResults;
    }

    //----------------------------------------------------------------------------------------------
    //Get Api data
    private void getApiData() {
        showProgress(true);

        //Check internet connection
        if (!Util.checkInternetAccess(this)) {
            Messages.showAlert(this, getString(R.string.alert_description));
            showProgress(false);
            mSwipeRefresh.setRefreshing(false);
            return;
        }

        //Url request
        new CloudConnect.JsonParser(ApiConnection.NEWS_API, null, json -> {
            try {
                mNewsData = CloudConnect.extractData(json.toString());

                assert mNewsData != null;
                if (mNewsData.size() > 1) {
                    mResults.addAll(mNewsData);
                    mListView.setAdapter(mAdapter);
                }
            } catch (Exception ex) {
                Messages.showAlert(this, getString(R.string.alert_fail));
            }
            showProgress(false);
            mSwipeRefresh.setRefreshing(false);
        }).execute();
    }

    //----------------------------------------------------------------------------------------------
    //Progressbar action
    private void showProgress(final boolean show) {
        Util.showProgress(this, mProgress, mListView, show);
    }
}