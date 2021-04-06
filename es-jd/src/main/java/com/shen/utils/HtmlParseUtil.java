package com.shen.utils;

import com.shen.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName HtmlParseUtil.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年04月06日 15:53:00
 */
@Component
public class HtmlParseUtil {

//    public static void main(String[] args) throws IOException {
//        new HtmlParseUtil().parseJD("java").forEach(System.out::println);
//    }

    public List<Content> parseJD(String keyword) throws IOException {
        // 获得请求 https://search.jd.com/Search?keyword=java
        // 1、联网  2、无法获取 ajax  需模拟浏览器
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        // 解析网页 ( Jsoup 返回对象就是浏览器 Document 对象) 跳过登录
        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN) AppleWebKit/535.12 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/535.12").timeout(30000).get();
//        Document document = Jsoup.parse(new URL(url), 30000);

        // js中使用的方法，都可使用
        Element element = document.getElementById("J_goodsList");
        // 获取所有的li标签
        Elements lis = element.getElementsByTag("li");
//        System.out.println(element.html());
        List<Content> goodsList = new ArrayList<>();
        // 获取元素中的内容
        for (Element li : lis) {
            if (li.attr("class").equalsIgnoreCase("gl-item")) {
                String img = li.getElementsByTag("img").eq(0).attr("data-lazy-img");
                String price = li.getElementsByClass("p-price").eq(0).text();
                String title = li.getElementsByClass("p-name").eq(0).text();
                Content content = new Content();
                content.setImg(img);
                content.setTitle(title);
                content.setPrice(price);
                goodsList.add(content);
            }
//            System.out.println("========================================");
//            System.out.println(img);
//            System.out.println(price);
//            System.out.println(title);
        }
        return goodsList;
    }
}
