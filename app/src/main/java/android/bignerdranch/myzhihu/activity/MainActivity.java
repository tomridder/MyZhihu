package android.bignerdranch.myzhihu.activity;

import android.bignerdranch.myzhihu.R;
import android.bignerdranch.myzhihu.adapter.NewsAdapter;
import android.bignerdranch.myzhihu.task.LoadNewsTask;
import android.bignerdranch.myzhihu.task.LoadnewsDetailTask;
import android.bignerdranch.myzhihu.utility.Utility;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener {

    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private NewsAdapter adapter;
    private boolean isConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isConnected= Utility.checkNetworkConnection(this);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        lv=(ListView)findViewById(R.id.lv);
        setTitle(getTime());
        adapter=new NewsAdapter(this,R.layout.listview_item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        if(isConnected)
        {
            new LoadNewsTask(adapter).execute();
        }else
        {
            Utility.noNetworkAlert(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings)
        {
            return true;
        }
        if(id==R.id.action_favourite)
        {
            Intent i=new Intent(this,FavouriteActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if(isConnected)
        {
            new LoadNewsTask(adapter, new LoadNewsTask.onFinishListener() {
                @Override
                public void aftertakFinish() {
                    refreshLayout.setRefreshing(false);
                }
            }).execute();
        }else
        {
            Utility.noNetworkAlert(MainActivity.this);
            refreshLayout.setRefreshing(false);
        }
    }

    public String getTime()
    {
        Calendar c=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat(getString(R.string.date_format));
        return format.format(c.getTime());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        NewsDetailActivity.startActivity(this,adapter.getItem(i));
    }
}
