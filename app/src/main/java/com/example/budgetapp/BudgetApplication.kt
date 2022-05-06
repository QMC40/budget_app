package com.example.budgetapp

import android.app.Application

class BudgetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ExpenseRepository.initialize(this)
    }
}