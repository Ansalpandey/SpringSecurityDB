package com.app.springbootsecuritydb.service

import com.app.springbootsecuritydb.model.User
import com.app.springbootsecuritydb.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*

@Service
class OAuthUserService(
  private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

  private val logger = LoggerFactory.getLogger(OAuthUserService::class.java)

  override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
    logger.info("Starting to load user from OAuth2UserRequest.")

    val oAuth2User = super.loadUser(userRequest)
    logger.info("OAuth2 user loaded successfully. Attributes: ${oAuth2User.attributes}")

    val registrationId = userRequest.clientRegistration.registrationId
    logger.info("OAuth2 provider: $registrationId")

    val attributes = oAuth2User.attributes
    logger.info("Extracted attributes: $attributes")

    val email = attributes["email"] as String?
      ?: attributes["user_email"] as String?
      ?: throw IllegalArgumentException("Email not found in OAuth2 attributes")
    val name = attributes["name"] as String? ?: attributes["given_name"] as String? ?: "Unknown"

    logger.info("Final extracted details - Email: $email, Name: $name")

    try {
      logger.info("Checking if user exists in the database.")
      val existingUser = userRepository.findByUsername(email)

      if (existingUser == null) {
        logger.info("User not found in database. Creating a new user.")
        val newUser = User(
          username = email,
          password = "",
          name = name,
          email = email,
          dob = Date()
        )
        val savedUser = userRepository.save(newUser)
        logger.info("New user saved successfully: $savedUser")
      } else {
        logger.info("User already exists in database: $existingUser")
      }
    } catch (ex: Exception) {
      logger.error("An error occurred while processing the OAuth2 user: ${ex.message}", ex)
      throw ex
    }

    logger.info("OAuth2 user processing completed successfully.")
    return oAuth2User
  }
}
