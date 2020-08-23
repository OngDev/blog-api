package com.ongdev.blog.api

import com.ongdev.blog.api.configs.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
