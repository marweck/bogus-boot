package com.minone.bogus.service;

import com.minone.bogus.model.Tweet;
import com.minone.bogus.model.dto.CreateTweetCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Tweet createTweet(CreateTweetCmd cmd) {

        Assert.notNull(cmd);

        Tweet tweet = cmd.toModel();

        tweet.setCreationDate(new Date());

        return tweetRepository.save(tweet);
    }

    public Tweet findById(String id) {

        Assert.notNull(id);

        return tweetRepository.findOne(id);
    }

    public Page<Tweet> findAll(Pageable page) {

        Assert.notNull(page);

        return tweetRepository.findAll(page);
    }
}