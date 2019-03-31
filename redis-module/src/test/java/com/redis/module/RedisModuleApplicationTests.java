package com.redis.module;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisModuleApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ListOperations listOperations;
    @Autowired
    private ValueOperations valueOperations;
    @Autowired
    private HashOperations hashOperations;
    @Autowired
    private SetOperations setOperations;
    @Autowired
    private ZSetOperations zSetOperations;


    @Test
    public void valueOperations() {
        //设置值 参数1 key  参数2  值
        valueOperations.set("key-1", "1");
        //设置值 参数1 key  参数2  值  参数3 偏移量；注意：如果当前key存在已经存在则根据偏移量覆盖值，一个字符连个偏移量
        valueOperations.set("key-2", "值-2", 4);
        //设置值 参数1 key  参数2  值  参数3 过期时间 参数4 过期时间单位；
        valueOperations.set("key-3", "值-3", 2, TimeUnit.SECONDS);
        //设置值 参数1 key  参数2  偏移量 参数3  boolean类型 true代表1 false 代表 0；
        //redis中数据是以二进制存储，该方法是在二进制的数据中进行偏移  相关资料：https://blog.csdn.net/hgd613/article/details/54095729
        valueOperations.setBit("key-4", 3, false);
        //设置值 参数1 key  参数2 值   （如果当前key不存在）
        valueOperations.setIfAbsent("key-5", "值-5");
        //设置值 参数1 key  参数2 值   这是新值返回旧值,如果不存在返回null
        Object andSet = valueOperations.getAndSet("key-6", "值-6");
        Map<String, String> map = new HashMap<>();
        map.put("key-7", "key-7");
        map.put("key-8", "key-8");
        map.put("key-9", "key-2");
        //设置多个key和值
        valueOperations.multiSet(map);
        //设置多个key和值（如果当前key不存在）
        valueOperations.multiSetIfAbsent(map);
        //截取当前key值的一部分，参数2 开始位置，参数3 结束位置
        String s = valueOperations.get("key-1", 0, 2);
        List list = new ArrayList();
        list.add("key-8");
        list.add("key-1");
        list.add("key-2");
        list.add("key-5");
        //根据集合中的key获取多个值
        List list1 = valueOperations.multiGet(list);
        //在当前key中的值后边追加新值
        valueOperations.append("key-1", "1444");
        //在当前key以增量的形式改变值，例如 当前key的值为1 则实际存储的数据为4(增量数类型可以为double)
        valueOperations.increment("key-1", 3);
        //获取储存当前key的值长度
        Long size = valueOperations.size("key-8");
        //根据当前key获取值
        Object o = valueOperations.get("key-1");
        System.out.println(size);
    }

    @Test
    public void listOperations() {

        /**
         * 获取当前key集合长度
         * key 集合key
         */
        //  Long size = listOperations.size("list-1");

        /**
         * 删除原key集合中的最后一个元素，并将它添加到目标key集合中，添加位置是在目标key集合中的第一个位置
         * sourceKey 原集合key
         * destinationKey 目标集合key
         */
        // Object o = listOperations.rightPopAndLeftPush("list-1", "list-2");
        /**
         *  向指定集合中添加多一个值
         *  key 集合key
         *  V... values  多个值（值的类型可以是Collection）
         */
        //listOperations.leftPushAll("list-1","va0","va1","va2");

        /**
         * 仅当前集合存在时，才向集合中添加数据,从右边开始添加（也就是尾部）
         * key 集合key
         * value 值
         */
        //listOperations.rightPushIfPresent("list-1", "vaad");


        /**
         * 仅当前集合存在时，才向集合中添加数据,从左边开始添加（也就是头部）
         * key 集合key
         * value 值
         */
        // listOperations.leftPushIfPresent("list-1", "vaad");

        /**
         * 获取集合中指定下标的的值
         * key  集合key
         * index 下标
         */
        // Object index = listOperations.index("list-1", 0);

        /**
         * 向当前key集合中添加值，并覆盖当前集合中指定位置的值
         * key 集合key
         * index 指定的下标位置
         * value 新值
         */
        //listOperations.set("list-1",7,"value-2");
        /**
         *  向当前key集合中添加元素（尾部添加）
         */
        //listOperations.rightPush("list-1", "value 40");

        /**
         * 向当前key集合中添加元素
         * key 集合key
         * pivot 集合中存在的某个值（在其后边添加）,如果存在多个相同值，从左到右第一个目标值后添加
         * value 要添加入集合中的新的值
         */
        // listOperations.rightPush("list-1", "value-1","ww");

        /**
         * 删除并返回当前key的最后一个元素
         */
        // Object o = listOperations.rightPop("list-1",10,TimeUnit.SECONDS);
        /**
         * 删除并返回当前key的第一个元素
         */
        //listOperations.leftPop("list-1",10,TimeUnit.SECONDS);

        /**
         * 删除当前key集合中值为参数3的元素；
         * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
         * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
         * count = 0 : 移除表中所有与 value 相等的值。
         */
        // listOperations.remove("list-1", 1, "value-1");
        /**
         *  修剪当前key集合，这里的修剪的意思就是把start到end之间的集合数据保留，其余的全部删除
         *  start 修剪起始位置
         *  end 修剪结束位置
         */
        // listOperations.trim("list-1",0,1);
        listOperations.leftPush("list-1","value -40","45553");
        /**
         * 获取集合中的元素
         * key 集合key
         * start 开始下标
         * end 结束下标
         */
        List range = listOperations.range("list-1", 0, listOperations.size("list-1"));
        System.out.println(range);
    }

    @Test
    public void hashOperations() {
        /**
         *  向指定的key和hashkey中设置value，如果不存在则新建
         */
        //hashOperations.put("map-1","hashkey-2","value-2");
        /**
         * 通过指定的key和hashkey删除元素
         */
        // hashOperations.delete("map-1","hashkey-1");
        /**
         *  通过指定的key和hashkey获取value
         */
        // Object o = hashOperations.get("map-1", "hashkey-1");
        /**
         *  判断指定的key和hashkey是否存在
         */
        Boolean aBoolean = hashOperations.hasKey("map-1", "hashkey-2");
        if (aBoolean) {
            System.out.println("已存在");
        }
        ScanOptions scanOptions = ScanOptions.scanOptions().count(2).match("*-2").build();
        /**
         * 待学习
         */
        //Cursor scan = hashOperations.scan("map-1",scanOptions);
        /**
         * 根据key获取所有的hashkey
         */
        // Set keys = hashOperations.keys("map-1");
        /**
         *  如果指定的key中不存在指定的hashkey则添加新的元素
         */
        hashOperations.putIfAbsent("map-1", "hashkey-4", "value-5");
        /**
         * 根据指定的key获取当前key中所有的hashkey
         */
        // List values = hashOperations.values("map-1");
        /**
         *  以增量的形式改变当前key中的hashkey的值
         */
        // hashOperations.increment("map-1","hashkey-1",1);

        List list = new ArrayList();
        list.add("hashkey-1");
        list.add("hashkey-2");
        list.add("hashkey-3");
        /**
         *  根据hashkey获取指定的key中的value，hashkey是已Collection的方式传递
         */
        List values = hashOperations.multiGet("map-1", list);
        /**
         * 获取指定key中的元素个数
         */
        Long size = hashOperations.size("map-1");
        Map map = new HashMap();
        map.put("hashkey-6", "value-6");
        map.put("hashkey-7", "value-7");
        map.put("hashkey-8", "value-8");
        hashOperations.putAll("map-1", map);
        RedisOperations operations = hashOperations.getOperations();
        Map entries = hashOperations.entries("map-1");
        System.out.println(JSON.toJSONString(entries));
    }

    @Test
    public void setOperations() {
        /**
         * 添加值到指定的key中
         *  key set集合key
         *  values 值（可以多个）
         *  return 返回添加的个数
         */
        setOperations.add("set-1", "aa1", "bb1", "cc1", "dd1","aa2", "bb2", "cc2");
        setOperations.add("set-2", "aa2", "bb2", "cc2", "dd2","ee2");
        /**
         *  获取两个集合的差集
         */
        Set difference = setOperations.difference("set-2", "set-1");
        /**
         * 获取两个集合的差集，并存在destkey中
         */
       // Long aLong = setOperations.differenceAndStore("set-2", "set-1", "set-3");
        /**
         * 随机获取当前集合中的元素
         * key 集合key
         * count 元素个数
         */
        Set set = setOperations.distinctRandomMembers("set-3", 1);

        /**
         * 根据指定的集合key获取多个集合中的相交集
         */
        Set intersect = setOperations.intersect("set-1", "set-2");
        /**
         * 根据指定的集合key获取多个集合中的相交集,并保存
         * 参数3 表示新set的key
         */
        //setOperations.intersectAndStore("set-1", "set-2","set-4");
        /**
         * 检查集合是否包含某个值
         *
         * return  boolean类型
         */
        //setOperations.isMember("set-1","value-1");
        /**
         * 移动原集合中的元素到目标集合
         * key 原集合key
         * value 要移动的值
         * destKey 目标集合key
         */
        setOperations.move("set-1","aa1","set-3");
        /**
         * 随机删除元素，并返回
         */
        setOperations.pop("set-2",setOperations.size("set-2"));
        /**
         * 根据条件匹配指定set中的元素,可以模糊匹配
         */
        Cursor scan = setOperations.scan("set-1", ScanOptions.scanOptions().match("c*").build());
        while (scan.hasNext()){
            Object next = scan.next();
            System.out.println("遍历："+next);
        }
        /**
         * 合并集合
         */
        Set union = setOperations.union("set-1", "set-2");
        /**
         * 合并集合并保存
         */
        setOperations.unionAndStore("set-1", "set-2", "set-6");
        Set members2 = setOperations.members("set-2");
        Set members1 = setOperations.members("set-1");
        Set members3 = setOperations.members("set-3");
        System.out.println("union："+union);
        System.out.println(JSON.toJSONString(members1 + "    " + members2 + "      " + members3));

    }

    @Test
    public void zSetOperations(){
        //zSetOperations.add("zset-1","value-1",1.0);
        Set<ZSetOperations.TypedTuple<String>> typedTuples = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            ZSetOperations.TypedTuple typedTuple =  new DefaultTypedTuple("value-"+i,(double)i%3);
            typedTuples.add(typedTuple);
        }
        zSetOperations.add("zset-1",typedTuples);

        /**
         * 获取给定集合的大小
         */
       // System.out.println(zSetOperations.size("zset-1"));
       // System.out.println(zSetOperations.zCard("zset-1"));
        /**
         * 通过提供的分数范围，获取给定集合的元素
         *
         */
   //     System.out.println(zSetOperations.count("zset-1", 0, 1.1));
        /**
         * 修改分数
         */
        zSetOperations.incrementScore("zset-1","value-1",1.9);

        /**
         *  取集合中的交集，并保存为一个新的集合
         *  zset-1和zset-2中的交集保存为zset-2
         */
       // zSetOperations.intersectAndStore("zset-1","zset-2","zset-3");
        /**
         * 根据指定范围获取有序集合中的元素
         */
       // System.out.println(zSetOperations.range("zset-1", 0, 100));
//        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
//        range.gt("-");range.lt("+");
//        System.out.println(zSetOperations.rangeByLex("zset-1", range));
        /**
         * 根据分数范围获取有序集合中的元素
         */
        System.out.println(zSetOperations.rangeByScore("zset-1", 0, 1));
        /**
         * 根据分数范围获取有序集合中的元素,并可以在该范围中设置偏移量和返回个数
         *
         */
        System.out.println(zSetOperations.rangeByScore("zset-1", 0, 1, 2, 100));

        /**
         * 获取有序集合中指定分数范围的元素
         */
        Set<ZSetOperations.TypedTuple<String>> set = zSetOperations.rangeByScoreWithScores("zset-1", 0, 1);
        Set<ZSetOperations.TypedTuple<String>> set1 = zSetOperations.rangeByScoreWithScores("zset-1", 0, 1,1,3);


        zSetOperations.rangeWithScores("zset-q",0,1);

        /**
         * 获取指定有序集合中某个值的索引
         */
        System.out.println(zSetOperations.rank("zset-1", "value-2"));

        /**
         * 删除有序集合中的某些值
         */
        zSetOperations.remove("zset-1","a","b");

        /**
         * 删除有序集合中指定范围的一个或多个值
         */
        zSetOperations.removeRange("zset-1",0,1);

        /**
         * 删除有序集合中指定分数范围的一个或多个值
         */
        zSetOperations.removeRangeByScore("zset-1",0,1);

        /**
         * 获取有序集合中指定范围的元素（从高到低）
         */
        zSetOperations.reverseRange("zset-1",0,1);
        /**
         * 获取有序集合中指定分数范围的元素（从高到低）
         */
        zSetOperations.reverseRangeByScore("zset-1",0,1.1);

        zSetOperations.reverseRangeByScoreWithScores("zset-1",0,1.1);

        /**
         *   获取有序集合中指定范围的元素值和分数（从高到低）
         */
        zSetOperations.reverseRangeWithScores("zset-1",0,1);

        /**
         * 获取指定有序集合中的索引（从高到低）
         */
        zSetOperations.reverseRank("zset-1","va");

        /**
         * 通过遍历有序集合获取符合匹配条件的元素
         */
        zSetOperations.scan("zset-1",ScanOptions.NONE);

        /**
         * 获取指定集合中某个元素的分数
         */
        zSetOperations.score("zset-1","va");

        /**
         * 合并有序集合并保存
         */
        zSetOperations.unionAndStore("zset-1","zset-2","zset-3");

        // Set ranges = zSetOperations.range("zset-1", 0, zSetOperations.size("zset-1"));
        //System.out.println(JSON.toJSONString(ranges));
    }
}
