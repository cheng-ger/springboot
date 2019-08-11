package com.cyl.it.practice.controller;

import com.alibaba.fastjson.JSON;
import com.cyl.it.practice.dao.ItemModelDao;
import com.cyl.it.practice.dao.UserInfoModelDao;
import com.cyl.it.practice.model.ItemModel;
import com.cyl.it.practice.model.UserInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-08-10
 */
@RestController
@Slf4j
@RequestMapping("elasticService")
public class ElasticSearchTestController {

    @Autowired
    private ItemModelDao itemDao;

    @Autowired
    private UserInfoModelDao userInfoDao;

    @GetMapping("itemAddOne")
    public String itemAddOne() {
        log.info("start : itemAddOne ========>>>>");
        //String uuid = UUID.randomUUID().toString();
        ItemModel itemModel = new ItemModel("1", "小米手机7", " 手机",
                "小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemDao.save(itemModel);
        log.info("end : itemAddOne ========>>>> model:{}", itemModel);
        return "success";
    }

    /*elasticsearch中本没有修改，它的修改原理是该是先删除在新增
    修改和新增是同一个接口，区分的依据就是id。*/
    @GetMapping("itemUpdateOne")
    public String itemAddOne(@RequestParam String id) {
        log.info("start : itemUpdateOne ========>>>>");

        ItemModel itemModel = new ItemModel(id, "小米手机update", " 手机",
                "小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemDao.save(itemModel);
        log.info("end : itemUpdateOne ========>>>> model:{}", itemModel);
        return "success";
    }


    @GetMapping("getItemOne")
    public String getItemOne(@RequestParam String id) {
        String result = "";
        log.info("start : getItemOne ========>>>>");
        Optional<ItemModel> optionalItemModel = itemDao.findById(id);
        if(optionalItemModel.isPresent() ) result = JSON.toJSONString(optionalItemModel.get());
        log.info("end : getItemOne ======>>");
        return result;
    }



    @GetMapping("userInfoAddOne")
    public  String userInfoAddOne() {
        log.info("start : userInfoAddOne ========>>>>");
        String uuid = UUID.randomUUID().toString();
        UserInfoModel userInfoModel = new UserInfoModel(uuid, "cyl" + (int) Math.random()*1000 , 18,
                "1", "go fish");
        userInfoDao.save(userInfoModel);
        log.info("end : userInfoAddOne ========>>>> model:{}" , userInfoModel);
        return "success";
    }


    @GetMapping("itemAddList")
    public String itemAddList(){
        log.info("start ===>>> itemAddList");

        List<ItemModel> itemModelList = getItemModelList();
        itemDao.saveAll(itemModelList);

        log.info("end ===>>> itemAddList");
        return "success";
    }

    private List<ItemModel> getItemModelList() {
        List<ItemModel> itemModelList =new ArrayList<>();
        ItemModel itemModel ;
        String uuid;
        for (int i = 0; i < 5; i++) {
             uuid = UUID.randomUUID().toString();
             if(i % 2 == 0)
            itemModel = new ItemModel(uuid, "小米手机" + (int) Math.random()*20 , " 手机",
                    "小米",  Math.random()*5000 , "http://image.baidu.com/13123.jpg");
            else
            itemModel = new ItemModel(uuid, "华为手机Mate" + (int) Math.random()*20 , " 手机",
                    "华为",   Math.random()*5000 , "http://image.baidu.com/13123.jpg");

            itemModelList.add(itemModel);
        }
        return itemModelList;
    }


    @GetMapping("findItemTitleList")
    public List userInfoAddList(@RequestParam String title){

        log.info("start ===>>> findItemTitleList");

       // itemDao.findByTitleLike(title);


        log.info("end ===>>> findItemTitleList");
        return itemDao.findByTitleLike(title);
    }


    @GetMapping("findByTitleLikeAndCategoryAndBrand")
    public List findByTitleLikeAndCategoryAndBrand(@RequestParam(required = false) String title , @RequestParam(required = false) String category,@RequestParam(required = false) String brand){

        log.info("start ===>>> findByTitleLikeAndCategoryAndBrand");

        //itemDao.findByTitleLikeAndCategoryAndBrand(title ,category , brand );


        log.info("end ===>>> findByTitleLikeAndCategoryAndBrand");
        return itemDao.findByTitleLikeAndCategoryAndBrand(title ,category , brand );
    }

    @GetMapping("userInfoAddList")
    public String userInfoAddList(){

        log.info("start ===>>> userInfoAddList");

        List<UserInfoModel>  userInfoModelList =getUserInfoModelList();
        userInfoDao.saveAll(userInfoModelList);

        log.info("end ===>>> userInfoAddList");
        return "success";
    }

    private List<UserInfoModel> getUserInfoModelList() {
        List<UserInfoModel> userInfoModelList =new ArrayList<>();
        UserInfoModel userInfoModel ;
        for (int i = 0; i < 5; i++) {
            String uuid = UUID.randomUUID().toString();
            userInfoModel = new UserInfoModel(uuid, "cyl" + (int) Math.random()*1000 , 18,
                    "1", "go fish" + System.currentTimeMillis());
            userInfoModelList.add(userInfoModel);
        }
        return userInfoModelList;
    }

    @GetMapping("findItemAll")
    public List findItemAll() {
        log.info("start ===>> findItemAll");
        Iterable<ItemModel> itemModelIterable = itemDao.findAll();
        List<ItemModel> itemModelList =new ArrayList<>();
        itemModelIterable.forEach(  single ->  itemModelList.add(single)  );
        log.info("end ===>> findItemAll");
        //String result = JSON.toJSONString(itemModelIterable);
        return  itemModelList;
    }



    @GetMapping("findUserInfoAll")
    public String findUserInfoAll() {
        log.info("start ===>> findUserInfoAll");
        Iterable<UserInfoModel> userInfoModelIterable = userInfoDao.findAll();

        log.info("end ===>> findUserInfoAll");

        return JSON.toJSONString(userInfoModelIterable);
    }


    //自定义查询
    @GetMapping("m1")
    public String  m1(@RequestParam(required = false)  String titleContext , @RequestParam(required = false)  String brand) {
        //NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        // 构建查询条件
        NativeSearchQueryBuilder  queryBuilder =new NativeSearchQueryBuilder();
        // 添加基本分词查询
        //matchQuery:底层就是使用的termQuery
        //termQuery:功能更强大，除了匹配字符串以外，还可以匹配
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", titleContext));
        queryBuilder.withQuery(QueryBuilders.matchQuery("brand", brand));

        Page<ItemModel> itemModelPage = itemDao.search(queryBuilder.build());

        List<ItemModel> itemModelList = new ArrayList<>();

        itemModelPage.forEach(e -> itemModelList.add(e));
        log.info("m1 >>> NativeSearchQueryBuilder : {}" , itemModelList);
        return JSON.toJSONString(itemModelPage);
    }


    //自定义查询
    /**
        * @Description:
        * termQuery:功能更强大，除了匹配字符串以外，还可以匹配
        * int/long/double/float/....
    * */
    @GetMapping("m2")
    public String  m2(@RequestParam(required = false)  String titleContext ) {
        //NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        // 构建查询条件
        NativeSearchQueryBuilder  queryBuilder =new NativeSearchQueryBuilder();
        // 添加基本分词查询
        //matchQuery:底层就是使用的termQuery
        //termQuery:功能更强大，除了匹配字符串以外，还可以匹配
        queryBuilder.withQuery(QueryBuilders.termQuery("title", titleContext));


        // 查找
        Page<ItemModel> page = itemDao.search(queryBuilder.build());

        for(ItemModel item:page){
            log.info("m1 testFuzzyQuery >>> NativeSearchQueryBuilder : {}" , item.toString());
        }

        return JSON.toJSONString(page);
    }



    /**
     * @Description:布尔查询
     *
     */
    @GetMapping("m3")
    public String testBooleanQuery(@RequestParam(required = false)  String titleContext ){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        builder.withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title","华为"))
                        .must(QueryBuilders.matchQuery("brand","华为"))
        );

        // 查找
        Page<ItemModel> page = itemDao.search(builder.build());
        for(ItemModel item:page){
            System.out.println(item.toString());
        }

        return JSON.toJSONString(page);
    }


    /**
     * @Description:模糊查询
     *
     */
    @GetMapping("m4")
    public String testFuzzyQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.fuzzyQuery("title", "手机"));
        Page<ItemModel> pages = itemDao.search(builder.build());
        for (ItemModel item : pages) {
            log.info("m3 testFuzzyQuery >>> NativeSearchQueryBuilder : {}" , item);
        }

        return JSON.toJSONString(pages);
    }



    /**
     * @Description:分页查询
     *
     */
    @GetMapping("m5")
    public String searchByPage(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page,size));

        // 搜索，获取结果
        Page<ItemModel> items = itemDao.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + items.getTotalPages());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());

        for (ItemModel item : items) {
            System.out.println(item.toString());
        }

        return JSON.toJSONString(items);
    }
    /**
     * @Description:排序查询
     *
     */
    @GetMapping("m6")
    public String searchAndSort(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));

        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));

        // 搜索，获取结果
        Page<ItemModel> items = itemDao.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);

        for (ItemModel item : items) {
            System.out.println(item);
        }

        return JSON.toJSONString(items);
    }

    /*2.5 聚合（牛逼！！solr无此功能）
    聚合可以让我们极其方便的实现对数据的统计、分析。例如：

    什么品牌的手机最受欢迎？
    这些手机的平均价格、最高价格、最低价格？
    这些手机每月的销售情况如何？
    实现这些统计功能的比数据库的sql要方便的多，而且查询速度非常快，可以实现近实时搜索效果。
    */

    //Elasticsearch中的聚合，包含多种类型，最常用的两种，一个叫桶，一个叫度量：
    //是按照某种方式对数据进行分组，每一组数据在ES中称为一个桶，例如我们根据国籍对人划分，可以得到中国桶、英国桶，日本桶
    //分组完成以后，我们一般会对组中的数据进行聚合运算，例如求平均值、最大、最小、求和等，这些在ES中称为度量


    /**
     * @Description:按照品牌brand进行分组
     *
     */
    @GetMapping("t1")
    public void testAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<ItemModel> aggPage = (AggregatedPage<ItemModel>) itemDao.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }

    }


   /* （1）统计某个字段的数量
    ValueCountBuilder vcb=  AggregationBuilders.count("count_uid").field("uid");
（2）去重统计某个字段的数量（有少量误差）
    CardinalityBuilder cb= AggregationBuilders.cardinality("distinct_count_uid").field("uid");
（3）聚合过滤
    FilterAggregationBuilder fab= AggregationBuilders.filter("uid_filter").filter(QueryBuilders.queryStringQuery("uid:001"));
（4）按某个字段分组
    TermsBuilder tb=  AggregationBuilders.terms("group_name").field("name");
（5）求和
    SumBuilder  sumBuilder=	AggregationBuilders.sum("sum_price").field("price");
（6）求平均
    AvgBuilder ab= AggregationBuilders.avg("avg_price").field("price");
（7）求最大值
    MaxBuilder mb= AggregationBuilders.max("max_price").field("price");
（8）求最小值
    MinBuilder min=	AggregationBuilders.min("min_price").field("price");
（9）按日期间隔分组
    DateHistogramBuilder dhb= AggregationBuilders.dateHistogram("dh").field("date");
（10）获取聚合里面的结果
    TopHitsBuilder thb=  AggregationBuilders.topHits("top_result");
（11）嵌套的聚合
    NestedBuilder nb= AggregationBuilders.nested("negsted_path").path("quests");
（12）反转嵌套
    AggregationBuilders.reverseNested("res_negsted").path("kps ");*/

    /**
     * @Description:嵌套聚合，求平均值
     *
     */
    @RequestMapping("t2")
    public void testSubAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<ItemModel> aggPage = (AggregatedPage<ItemModel>) itemDao.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }

}
