package android.bignerdranch;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ZhihuDailyApplication extends Application
{
    public static void initImageLoader(Context context)
    {
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY-2)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
    }
}
