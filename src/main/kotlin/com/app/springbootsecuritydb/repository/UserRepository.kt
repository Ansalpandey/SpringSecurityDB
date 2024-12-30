package com.app.springbootsecuritydb.repository

import com.app.springbootsecuritydb.model.User
import org.bson.types.ObjectId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, ObjectId>{
    fun findByUsername(username: String): User?
}