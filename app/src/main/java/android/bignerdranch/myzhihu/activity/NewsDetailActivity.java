package android.bignerdranch.myzhihu.activity;

import android.bignerdranch.myzhihu.R;
import android.bignerdranch.myzhihu.db.DailyNewsDB;
import android.bignerdranch.myzhihu.entity.News;
import android.bignerdranch.myzhihu.task.LoadnewsDetailTask;
import android.bignerdranch.myzhihu.utility.Utility;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class NewsDetailActivity extends AppCompatActivity {

    private WebView mWebView;
    private boolean isFavourite=false;
    private News news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mWebView=(WebView)findViewById(R.id.webview);
        setWebView(mWebView);
        news=(News)getIntent().getSerializableExtra("news");
        new LoadnewsDetailTask(mWebView).execute(news.getId());
        isFavourite= DailyNewsDB.getInstance(this).isFavourite(news);
    }

    private void setWebView(WebView mWebView)
    {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        if(isFavourite) menu.findItem(R.id.action_favourite).setIcon(R.drawable.fav_active);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.action_settings)
        {
          return   true;
        }
        if(id==R.id.action_favourite)
        {
            if(!isFavourite)
            {
                DailyNewsDB.getInstance(this).saveFavourite(news);
                item.setIcon(R.drawable.fav_active);
                isFavourite=true;
            }else
            {
                DailyNewsDB.getInstance(this).deleteFavourite(news);
                item.setIcon(R.drawable.fav_normal);
                isFavourite=false;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void startActivity(Context context, News news) {
        if (Utility.checkNetworkConnection(context)) {
            Intent i = new Intent(context, NewsDetailActivity.class);
            i.putExtra("news", news);
            context.startActivity(i);
        } else {
            Utility.noNetworkAlert(context);
        }
    }
}
