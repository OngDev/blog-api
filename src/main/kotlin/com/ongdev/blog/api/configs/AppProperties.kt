package com.ongdev.blog.api.configs

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties(
        var authUrl: String = ""
)