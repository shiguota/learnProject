package com.zookeeper.module;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperModuleApplicationTests {

    @Autowired
    private ZooKeeper zooKeeper;

    @Test
    public void contextLoads() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/root/aa", false);
        if(exists == null){
            String s = zooKeeper.create("/root/aa", "数据aa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        List<String> children = zooKeeper.getChildren("/root",false);
        for (String child : children) {
            System.out.println(child);
        }
        /**
         * 获取节点数据
         */
        byte[] data = zooKeeper.getData("/root", false, null);
        System.out.println("======"+new String(data));
    }
}
