package com.hrushant.moneytrack.data.repository

import com.hrushant.moneytrack.data.entity.User
import com.hrushant.moneytrack.data.local.dao.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User): Boolean{
        val existingUser = userDao.getUserByEmail(user.email)
        if(existingUser != null)
            return false
        userDao.insertUser(user)
        return true
    }

    suspend fun getUserByEmail(email: String): User?{
        return userDao.getUserByEmail(email)
    }

}