package com.example.budgetapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class ExpenseDetailViewModel() : ViewModel() {
    private val expenseRepository = ExpenseRepository.get()
    private val expenseIdLiveData = MutableLiveData<UUID>()
    var expenseLiveData: LiveData<Expense?> =
        Transformations.switchMap(expenseIdLiveData) { expenseId ->
            expenseRepository.getExpense(expenseId)
        }
    fun loadExpense(expenseId: UUID) {
        expenseIdLiveData.value = expenseId
    }
    fun saveExpense(expense: Expense) {
        expenseRepository.updateExpense(expense)
    }
}