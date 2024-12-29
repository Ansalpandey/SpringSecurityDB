package com.app.springbootsecuritydb.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.Nonnull
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "users")
@TypeAlias("user")
data class User(
    @Id
    val id: ObjectId? = null,
    @Nonnull
    val username: String,
    @Nonnull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String,
    @Nonnull
    val name: String,
    @Nonnull
    val email: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Nonnull
    val dob: Date,
)
