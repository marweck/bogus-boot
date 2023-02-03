package com.minone.bogus.model.dto

import com.minone.bogus.model.Tweet
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class CreateTweetCmd {
    var user: @NotBlank String? = null
        private set
    var temperature: @NotNull Double? = null
        private set
    var pressure: @NotNull Double? = null
        private set
    var latitude: @NotNull Double? = null
        private set
    var longitude: @NotNull Double? = null
        private set
    var message: @NotBlank String? = null
        private set

    fun toModel(): Tweet {
        val data = Tweet()
        data.latitude = latitude
        data.longitude = longitude
        data.user = user
        data.temperature = temperature
        data.pressure = pressure
        data.message = message
        return data
    }

    companion object {
        fun build(
            user: String?, temperature: Double?, pressure: Double?, latitude: Double?,
            longitude: Double?, message: String?
        ): CreateTweetCmd {
            val obj = CreateTweetCmd()
            obj.user = user
            obj.temperature = temperature
            obj.pressure = pressure
            obj.latitude = latitude
            obj.longitude = longitude
            obj.message = message
            return obj
        }
    }
}