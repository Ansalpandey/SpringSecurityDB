package com.app.springbootsecuritydb.controller

import com.app.springbootsecuritydb.model.LoginRequest
import com.app.springbootsecuritydb.model.User
import com.app.springbootsecuritydb.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

  @PostMapping("/register")
  fun registerUser(@RequestBody user: User): ResponseEntity<Map<String, String>> {
    return try {
      userService.registerUser(user)
      val response = mapOf("message" to "User successfully registered")
      ResponseEntity(response, HttpStatus.CREATED)
    } catch (e: IllegalArgumentException) {
      val errorResponse = mapOf("error" to e.message!!)
      ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    } catch (e: Exception) {
      val errorResponse = mapOf("error" to "Internal Server Error")
      ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @PostMapping("/login")
  fun loginUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
    return try {
      val response = userService.loginUser(loginRequest)
      ResponseEntity(response, HttpStatus.OK)
    } catch (e: IllegalArgumentException) {
      val errorResponse = mapOf("error" to e.message!!)
      ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    } catch (e: Exception) {
      val errorResponse = mapOf("error" to "Internal Server Error")
      ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @GetMapping("/profile")
  fun getUserProfile(authentication: Authentication): ResponseEntity<Any> {
    val user = authentication.principal as User
    val userInfo = mapOf(
      "id" to user.id,
      "username" to user.username,
      "name" to user.name,
      "email" to user.email
    )
    return ResponseEntity(userInfo, HttpStatus.OK)
  }
}
