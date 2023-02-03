package com.minone.bogus.service

import com.minone.bogus.model.Tweet
import com.minone.bogus.model.dto.CreateTweetCmd
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class TweetService(private val tweetRepository: TweetRepository) {
    fun createTweet(cmd: CreateTweetCmd): Tweet {
        val tweet = cmd.toModel()
        tweet.creationDate = Date()
        return tweetRepository.save(tweet)
    }

    fun findById(id: String): Optional<Tweet> {
        return tweetRepository.findById(id)
    }

    fun findAll(page: Pageable): Page<Tweet> {
        return tweetRepository.findAll(page)
    }
}
