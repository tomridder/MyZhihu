package android.bignerdranch.myzhihu.task;

import android.bignerdranch.myzhihu.adapter.NewsAdapter;
import android.bignerdranch.myzhihu.entity.News;
import android.bignerdranch.myzhihu.http.Http;
import android.bignerdranch.myzhihu.http.JsonHelper;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

public class LoadNewsTask extends AsyncTask<Void,Void, List<News>>
{
    private NewsAdapter adapter;
    private onFinishListener listener;
    public LoadNewsTask(NewsAdapter adapter)
    {
        super();
        this.adapter=adapter;
    }

    public LoadNewsTask(NewsAdapter adapter, onFinishListener listener) {
        super();
        this.adapter = adapter;
        this.listener = listener;
    }

    @Override
    protected List<News> doInBackground(Void... voids) {
        List<News> newsList=null;
        try
        {
            newsList= JsonHelper.parseJsonToList(Http.getLastNewsList());
        }catch (IOException e )
        {

        }finally {
            return newsList;
        }
    }

    @Override
    protected void onPostExecute(List<News> news) {
        adapter.refreshNewslist(news);
        if(listener!=null)
        {
            listener.aftertakFinish();
        }
    }

    public interface  onFinishListener
    {
        public void aftertakFinish();
    }
}

