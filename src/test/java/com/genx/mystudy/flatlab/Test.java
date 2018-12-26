package com.genx.mystudy.flatlab;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 21:45
 */
public class Test {


    private static String baseUrl = "http://thevectorlab.net/flatlab/";

    private static String dir = "D:/work/flatlab/";

    private static Map<String, Integer> map = new HashMap(1024);

    public static void main(String[] args) throws IOException {

        get("index.html");


    }

    private static void get(String url) {
        try {
            if (url.startsWith("http")){
                return;
            }
            if(url.contains("#")){
                url = url.substring(0, url.indexOf("#"));
                System.out.println("url="+url);
            }

            if(map.containsKey(url)){
                return;
            }
            System.out.println("开始下载：" + baseUrl + url);
            map.put(url, 1);

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(baseUrl + url)
                    .build();
            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            String s = response.body().string();


            save(dir + url, s);


            if(url.endsWith(".html")){
                parseHtml(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void parseHtml(String html){
        Document document = Jsoup.parse(html);

        //CSS
//        Elements links = document.getElementsByTag("link");
//        for (Element link : links) {
//            String href = link.attr("href");
//            get(href);
//        }
//
//        //JS
//        Elements scripts = document.getElementsByTag("script");
//        for (Element script : scripts) {
//            String src = script.attr("src");
//            if(StringUtils.isNoneEmpty(src)){
//                get(src);
//            }
//        }
//
//        //页面
        Elements as = document.getElementsByTag("a");
        for (Element a : as) {
            String href = a.attr("href");
            if(href.endsWith(".html")){
                get(href);
            }
        }
        
        //img
        Elements imgs = document.getElementsByTag("img");
        for (Element img : imgs) {
            String src = img.attr("src");
            if(StringUtils.isNoneEmpty(src)){


                File file = new File(dir + src);
                if(!file.exists()) {
                    System.out.println("开始下载：" + baseUrl + src);
                    DownImg.downloadPicture(baseUrl + src, file);
                }
            }
        }

    }

    private static void save(String path, String s) throws IOException {
        FileUtils.write(new File(path), s);
    }
}
