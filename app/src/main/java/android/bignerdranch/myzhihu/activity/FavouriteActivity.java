package android.bignerdranch.myzhihu.activity;

import android.bignerdranch.myzhihu.R;
import android.bignerdranch.myzhihu.adapter.NewsAdapter;
import android.bignerdranch.myzhihu.db.DailyNewsDB;
import android.bignerdranch.myzhihu.entity.News;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private NewsAdapter adapter;
    private List<News> favouriteList;
    private ListView lvFavourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        lvFavourite=(ListView)findViewById(R.id.lv_fav);
        favouriteList= DailyNewsDB.getInstance(this).loadFavourite();
        adapter=new NewsAdapter(this,R.layout.listview_item,favouriteList);
        lvFavourite.setAdapter(adapter);
        lvFavourite.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        NewsDetailActivity.startActivity(this,adapter.getItem(i));
    }
}
