package com.minone.bogus.service

import com.minone.bogus.model.Tweet
import org.springframework.data.mongodb.repository.MongoRepository

interface TweetRepository : MongoRepository<Tweet, String>
