package android.bignerdranch.myzhihu.adapter;

import android.bignerdranch.myzhihu.R;
import android.bignerdranch.myzhihu.entity.News;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News>
{
    private LayoutInflater mInflater;
    private int resource;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    private DisplayImageOptions options=new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.no_image)
            .showImageOnLoading(R.drawable.no_image)
            .showImageForEmptyUri(R.drawable.no_image)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    public NewsAdapter(Context context,int resource)
    {
        super(context,resource);
        this.mInflater=LayoutInflater.from(context);
        this.resource=resource;
    }

    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
        this.resource = resource;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
      ViewHolder holder;
      if(convertView==null)
      {
          convertView=mInflater.inflate(resource,null);
          holder=new ViewHolder();
          holder.newsImage=(ImageView)convertView.findViewById(R.id.news_image);
          holder.newsTitle=(TextView)convertView.findViewById(R.id.news_title);
          convertView.setTag(holder);
      }else
      {
          holder=(ViewHolder)convertView.getTag();
      }
      News news=getItem(position);
      holder.newsTitle.setText(news.getTitle());
      imageLoader.displayImage(news.getImage(),holder.newsImage,options);
      return convertView;
    }

    class ViewHolder
    {
        ImageView newsImage;
        TextView newsTitle;
    }

    public void refreshNewslist(List<News> newsList)
    {
        clear();
        addAll(newsList);
        notifyDataSetChanged();
    }
}
