package com.app.springbootsecuritydb.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class ApiLoggingInterceptor : HandlerInterceptor {
  private val logger = LoggerFactory.getLogger(ApiLoggingInterceptor::class.java)

  override fun preHandle(
    request: HttpServletRequest,
    response: HttpServletResponse,
    handler: Any
  ): Boolean {
    logger.info("Incoming Request: Method=${request.method}, URI=${request.requestURI}, QueryParams=${request.queryString}")
    return true
  }

  override fun afterCompletion(
    request: HttpServletRequest,
    response: HttpServletResponse,
    handler: Any,
    ex: Exception?
  ) {
    logger.info("Response: Status=${response.status}, URI=${request.requestURI}")
  }
}
