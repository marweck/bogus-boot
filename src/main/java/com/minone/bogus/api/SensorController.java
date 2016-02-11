package com.minone.bogus.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minone.bogus.model.Tweet;
import com.minone.bogus.model.dto.CreateTweetCmd;
import com.minone.bogus.service.TweetService;

@RestController
public class SensorController {

	@Autowired
	private TweetService tweetService;

	@RequestMapping(value = "sensor/tweet", method = RequestMethod.GET)
	public HttpEntity<Page<Tweet>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "500") int size) {

		Page<Tweet> tweets = tweetService.findAll(new PageRequest(page, size));

		return new HttpEntity<Page<Tweet>>(tweets);
	}

	@RequestMapping(value = "sensor/tweet", method = RequestMethod.POST)
	public HttpEntity<Tweet> savePosition(@Valid @RequestBody CreateTweetCmd cmd) {

		Tweet tweet = tweetService.createTweet(cmd);

		return new HttpEntity<Tweet>(tweet);
	}

	@RequestMapping(value = "sensor/tweet/{id}", method = RequestMethod.GET)
	public HttpEntity<Tweet> getById(@PathVariable String id) {

		Tweet tweet = tweetService.findById(id);

		return new HttpEntity<Tweet>(tweet);
	}
}