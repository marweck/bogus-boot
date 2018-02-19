package com.minone.bogus.api;

import com.minone.bogus.model.Tweet;
import com.minone.bogus.model.dto.CreateTweetCmd;
import com.minone.bogus.service.TweetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class SensorController {

	private TweetService tweetService;

	public SensorController(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@GetMapping(value = "sensor/tweet")
	public HttpEntity<Page<Tweet>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "500") int size) {

		Page<Tweet> tweets = tweetService.findAll(new PageRequest(page, size));

		return new HttpEntity<>(tweets);
	}

	@PostMapping(value = "sensor/tweet")
	public HttpEntity<Tweet> savePosition(@Valid @RequestBody CreateTweetCmd cmd) {

		Tweet tweet = tweetService.createTweet(cmd);

		return new HttpEntity<>(tweet);
	}

	@GetMapping(value = "sensor/tweet/{id}")
	public HttpEntity<Tweet> getById(@PathVariable String id) {

		Optional<Tweet> tweet = tweetService.findById(id);

		return new HttpEntity<>(tweet.get());
	}
}