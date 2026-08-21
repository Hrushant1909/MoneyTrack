package com.hrushant.moneytrack.data.model

import com.hrushant.moneytrack.data.entity.Category
import com.hrushant.moneytrack.data.entity.TransactionType

object DefaultCategories {

    val categories = listOf(

        Category(name = "Food", type = TransactionType.EXPENSE),
        Category(name = "Rent", type = TransactionType.EXPENSE),
        Category(name = "Travel", type = TransactionType.EXPENSE),
        Category(name = "Shopping", type = TransactionType.EXPENSE),
        Category(name = "Bills", type = TransactionType.EXPENSE),
        Category(name = "Entertainment", type = TransactionType.EXPENSE),
        Category(name = "Health", type = TransactionType.EXPENSE),
        Category(name = "Education", type = TransactionType.EXPENSE),
        Category(name = "Other", type = TransactionType.EXPENSE),

        Category(name = "Salary", type = TransactionType.INCOME),
        Category(name = "Freelance", type = TransactionType.INCOME),
        Category(name = "Investment", type = TransactionType.INCOME),
        Category(name = "Other Income", type = TransactionType.INCOME)
    )
}