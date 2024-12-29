package com.app.springbootsecuritydb.utils


import com.app.springbootsecuritydb.model.LoginRequest
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    private val secretKeyString =
        "YourSecretKjhkldasfhjkladsfljkdasfjklsadfhjkdsahfljksdahfljkasdhfjlkhsfjklsadhlfjkasdfasdfasdey" // Use a strong secret in production
    private val jwtExpirationMs = 3600000 // Token validity: 1 hour

    // Create a SecretKey for HMAC-SHA256
    private val secretKey = Keys.hmacShaKeyFor(secretKeyString.toByteArray())
    fun generateToken(loginRequest: LoginRequest): String {
        val claims: Map<String, Any> = mapOf("username" to loginRequest.username)

        return Jwts.builder().claims(claims).issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + jwtExpirationMs)).signWith(secretKey).compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).build().parse(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun extractUsername(token: String): String? {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).body["username"] as String?
    }
}
