package com.zookeeper.module.util;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InitZk {

    @Bean
    public ZooKeeper init() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("", 2181, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                if (state == Event.KeeperState.SyncConnected) {
                    System.out.println("服务器已连接");
                }else{
                    System.out.println("服务器当前作态为："+state.getIntValue());
                }
            }
        });
        return zooKeeper;
    }
}
