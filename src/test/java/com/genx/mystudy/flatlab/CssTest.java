package com.genx.mystudy.flatlab;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 22:50
 */
public class CssTest {

    private static Map<String, Integer> map = new HashMap<>(1024);

    public static void main(String[] args) {
        listAllFiles("D:/work/flatlab");

        System.out.println("##########################");
        for (String s : map.keySet()) {
            System.out.println(s);
            String path = "D:/work/flatlab/" + s.substring(32);
            System.out.println(path);
            DownImg.downloadPicture(s, new File(path));
        }
    }


    // 该方法遍历指定目录下的所有文件
    public static void listAllFiles(String mypath) {
        // 实例化File对象
        File file = new File(mypath);
        // 判断该File对象是否是文件夹
        if (file.isDirectory()) {
            // 获取该文件夹下所有的文件及文件夹
            File[] files = file.listFiles();
            // 遍历
            for (File f : files) {
                // 判断该File对象是否是文件夹
                if (f.isDirectory()) {
                    // 递归调用
                    listAllFiles(f.getAbsolutePath());
                } else {
                    // 若是文件，则直接输出该文件的绝对路径
                    if(f.getName().endsWith(".css")){
                        System.out.println(f);
                        readCss(f);
                    }
                }
            }
        } else {
            // 若是文件，则直接输出该文件的绝对路径
//            System.out.println(file.getAbsolutePath());
        }
    }

    private static void readCss(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);

            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                int start = line.indexOf("url(");
                if(start > - 1){
                    String s = line.substring(start + 4);



                    s = s.substring(0, s.indexOf(")")).replace("\"", "").replace("'", "");
                    if(s.startsWith("data")){
                        continue;
                    } else if (s.startsWith("/")){
                        continue;
                    }
                    s =  "http://thevectorlab.net/flatlab/" + file.getParent().substring(16)+"/" + s;
                    s = s.replace("\\", "/");

                    s = s.replace("css/../", "");
                    if(s.contains("?")){
                        s = s.substring(0, s.indexOf("?"));
                    }
                    map.put(s, 1);
                }
            }

            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
