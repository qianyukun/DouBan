package com.qyk.douban.common;

/**
 * Created by Administrator on 2015/12/17.
 */
public class HttpRequestStrings {
    /**搜索图书
     q	查询关键字	q和tag必传其一
     tag	查询的tag	q和tag必传其一
     start	取结果的offset	默认为0
     count	取结果的条数	默认为20，最大为100
     */
    public static String _book_search="https://api.douban.com/v2/book/search";

}
