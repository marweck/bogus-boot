package com.minone.bogus.api

import com.minone.bogus.model.Tweet
import com.minone.bogus.model.dto.CreateTweetCmd
import com.minone.bogus.service.TweetService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpEntity
import org.springframework.web.bind.annotation.*

@RestController
class SensorController(private val tweetService: TweetService) {
    @GetMapping(value = ["sensor/tweet"])
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): HttpEntity<Page<Tweet>> {
        val tweets = tweetService.findAll(PageRequest.of(page, size))
        return HttpEntity(tweets)
    }

    @PostMapping(value = ["sensor/tweet"])
    fun savePosition(@RequestBody cmd: @Valid CreateTweetCmd): HttpEntity<Tweet> {
        val tweet = tweetService.createTweet(cmd)
        return HttpEntity(tweet)
    }

    @GetMapping(value = ["sensor/tweet/{id}"])
    fun getById(@PathVariable id: String): HttpEntity<Tweet> {
        val tweet = tweetService.findById(id)
        return HttpEntity(tweet.get())
    }
}