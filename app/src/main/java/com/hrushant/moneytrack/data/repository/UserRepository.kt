package com.hrushant.moneytrack.data.repository

import com.hrushant.moneytrack.data.entity.User
import com.hrushant.moneytrack.data.local.dao.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): User?{
        return userDao.getUserByEmail(email)
    }

}