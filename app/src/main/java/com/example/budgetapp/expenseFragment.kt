package com.example.budgetapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import java.util.*

private const val TAG = "ExpenseFragment"
private const val ARG_EXPENSE_ID = "expense_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0

class ExpenseFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var expenses: Expense
    private lateinit var titleField: EditText
    private lateinit var categoryField: EditText
    private lateinit var dateButton: Button
    private val expenseDetailViewModel: ExpenseDetailViewModel by lazy {
        ViewModelProviders.of(this).get(ExpenseDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expenses = Expense()
        val expenseId: UUID = arguments?.getSerializable(ARG_EXPENSE_ID) as UUID
        expenseDetailViewModel.loadExpense(expenseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense, container, false)

        titleField = view.findViewById(R.id.expense_title) as EditText
        categoryField = view.findViewById(R.id.expense_category) as EditText
        dateButton = view.findViewById(R.id.expense_date) as Button
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseDetailViewModel.expenseLiveData.observe(
            viewLifecycleOwner,
            Observer { expense ->
                expense?.let {
                    this.expenses = expense
                    updateUI()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                expenses.title = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {}
        }
        titleField.addTextChangedListener(titleWatcher)
        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(expenses.date).apply {
                setTargetFragment(this@ExpenseFragment, REQUEST_DATE)
                show(this@ExpenseFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        expenseDetailViewModel.saveExpense(expenses)
    }

    override fun onDateSelected(date: Date) {
        expenses.date = date
        updateUI()
    }

    private fun updateUI() {
        titleField.setText(expenses.title)
        categoryField.setText(expenses.category)
        dateButton.text = expenses.date.toString()
    }

    companion object {
        fun newInstance(expenseId: UUID): ExpenseFragment {
            val args = Bundle().apply {
                putSerializable(ARG_EXPENSE_ID, expenseId)
            }
            return ExpenseFragment().apply {
                arguments = args
            }
        }
    }
}