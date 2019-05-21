package android.bignerdranch.myzhihu.task;

import android.bignerdranch.myzhihu.entity.NewsDetail;
import android.bignerdranch.myzhihu.http.Http;
import android.bignerdranch.myzhihu.http.JsonHelper;
import android.os.AsyncTask;
import android.webkit.WebView;

import java.io.IOException;

public class LoadnewsDetailTask extends AsyncTask<Integer,Void, NewsDetail>
{
    private WebView mWebView;
    public LoadnewsDetailTask(WebView mWebView) {
        this.mWebView = mWebView;
    }

    @Override
    protected NewsDetail doInBackground(Integer... params) {
        NewsDetail mNewsDetail=null;
        try
        {
            mNewsDetail= JsonHelper.parseJsonToDetail(Http.getNewsDetail(params[0]));
        }catch (IOException e)
        {

        }finally {
            return mNewsDetail;
        }
    }

    @Override
    protected void onPostExecute(NewsDetail newsDetail) {
        String headerImage;
        if(newsDetail.getImage()==null ||newsDetail.getImage()=="")
        {
            headerImage="file:///android_asset/news_detail_header_image.jpg";
        }else
        {
            headerImage=newsDetail.getImage();
        }
        StringBuilder sb=new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(newsDetail.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(newsDetail.getImage_source()).append("</span>")
                .append("<img src=\"").append(headerImage)
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                + newsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        mWebView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
    }
}
