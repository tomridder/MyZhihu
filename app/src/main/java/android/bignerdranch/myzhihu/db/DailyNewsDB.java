package android.bignerdranch.myzhihu.db;

import android.bignerdranch.myzhihu.entity.News;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DailyNewsDB
{
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private static DailyNewsDB mDailynewsDB;
    private String[] allColums={DBHelper.COLUMN_ID,DBHelper.COLUMN_NEWS_ID,DBHelper.COLUMN_NEWS_TITLE,DBHelper.COLUMN_NEWS_IMAGE};

    private DailyNewsDB(Context context)
    {
        dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public synchronized static DailyNewsDB getInstance(Context context)
    {
        if(mDailynewsDB==null)
        {
            mDailynewsDB=new DailyNewsDB(context);
        }
        return  mDailynewsDB;
    }

    public void saveFavourite(News news)
    {
        if(news!=null)
        {
            ContentValues values=new ContentValues();
            values.put(DBHelper.COLUMN_NEWS_ID, news.getId());
            values.put(DBHelper.COLUMN_NEWS_TITLE, news.getTitle());
            values.put(DBHelper.COLUMN_NEWS_IMAGE, news.getImage());
            db.insert(DBHelper.TABLE_NAME,null,values);
        }
    }

    public List<News> loadFavourite()
    {
        List<News> favouriteList=new ArrayList<>();
        Cursor cursor=db.query(DBHelper.TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                News news = new News();
                news.setId(cursor.getInt(1));
                news.setTitle(cursor.getString(2));
                news.setImage(cursor.getString(3));
                favouriteList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favouriteList;
    }

    public boolean isFavourite(News news)
    {
        Cursor cursor=db.query(DBHelper.TABLE_NAME,null,DBHelper.COLUMN_NEWS_ID+" =?",new String[]{news.getId()+""},null,null,null);
        if(cursor.moveToNext())
        {
            cursor.close();
            return true;
        }else
        {
            return false;
        }
    }

    public void deleteFavourite(News news)
    {
        if(news!=null)
        {
            db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_NEWS_ID + " = ?", new String[]{news.getId() + ""});
        }
    }

}
