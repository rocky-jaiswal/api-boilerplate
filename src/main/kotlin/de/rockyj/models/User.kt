package de.rockyj.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name="users")
data class User(
        @Id
        val id: Int,
        val name: String
)