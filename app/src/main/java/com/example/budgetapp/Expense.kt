package com.example.budgetapp

import java.util.*

data class Expense(
    val id: UUID = UUID.randomUUID(),
    var amount: Double = 0.0,
    var title: String = "",
    var type: String = "",
    var date: Date = Date()
)