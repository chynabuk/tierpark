package com.example.tierpark;

import com.example.tierpark.entities.Feed;
import com.example.tierpark.services.impl.FeedService;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        FeedService feedService = new FeedService();
//        feedService.insert(
//                Feed.builder()
//                        .name("Banana")
//                        .measure("ST")
//                        .pricePerUnit(4)
//                        .build());

        List<Feed> feeds = feedService.readAll();
        for(Feed f : feeds){
            System.out.println(f);
        }

        Feed feed = feeds.get(0);
        feed.setName("Et");
        feedService.update(feed);

        feeds = feedService.readAll();
        for(Feed f : feeds){
            System.out.println(f);
        }

        feedService.delete(feed.getId());
        feeds = feedService.readAll();
        for(Feed f : feeds){
            System.out.println(f);
        }
    }
}
