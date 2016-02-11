package com.minone.bogus.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.minone.bogus.model.Tweet;

public interface TweetRepository extends MongoRepository<Tweet, String> {

}