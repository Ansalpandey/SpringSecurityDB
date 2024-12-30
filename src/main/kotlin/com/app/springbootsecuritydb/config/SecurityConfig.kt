package com.app.springbootsecuritydb.config

import com.app.springbootsecuritydb.filter.JwtFilter
import com.app.springbootsecuritydb.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val userRepository: UserRepository
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(13)
    }


    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers("/api/user/register", "/api/user/login").permitAll().anyRequest().authenticated()
            }
            .oauth2Login(
                Customizer.withDefaults()
            )
            .httpBasic(
                Customizer.withDefaults()
            )
            .formLogin(Customizer.withDefaults())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        return AuthenticationManager {
            val username = it.name
            val user = userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User not found: $username")
            UsernamePasswordAuthenticationToken(user, null, emptyList())
        }
    }
}
