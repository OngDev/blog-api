package com.ongdev.blog.api.configs

import com.ongdev.blog.api.services.interfaces.UserService
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfiguration(
        val userService: UserService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun tokenAuthenticationFilter(): TokenAuthenticationFilter {
        return TokenAuthenticationFilter(userService)
    }

    override fun configure(http: HttpSecurity?) {
        http
                ?.cors()
                ?.and()
                ?.csrf()?.disable()
                ?.formLogin()?.disable()
                ?.httpBasic()?.disable()
                ?.exceptionHandling()?.authenticationEntryPoint(RestAuthenticationEntryPoint())
                ?.and()
                ?.authorizeRequests()
                ?.antMatchers("/actuator/**")?.permitAll()
                ?.antMatchers("/swagger-ui.html")?.permitAll()
                ?.antMatchers("/swagger-resources/**")?.permitAll()
                ?.antMatchers("/v2/api-docs")?.permitAll()
                ?.anyRequest()?.authenticated()
        http?.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }
}