package com.minone.bogus.model

import org.springframework.data.annotation.Id
import java.util.*

class Tweet {
    @Id
    var id: String? = null
    var user: String? = null
    var creationDate: Date? = null
    var temperature: Double? = null
    var pressure: Double? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var message: String? = null
}
