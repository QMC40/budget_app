package com.example.budgetapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Expense(@PrimaryKey val id: UUID = UUID.randomUUID(),
                   var title: String = "",
                   var category: String = "",
                   var date: Date = Date())
