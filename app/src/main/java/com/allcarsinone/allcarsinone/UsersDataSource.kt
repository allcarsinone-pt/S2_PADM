package com.allcarsinone.allcarsinone

import android.provider.ContactsContract.CommonDataKinds.Email

interface UsersDataSource {

    fun createUser(username: String,
                   name: String,
                   email: String,
                   password: String) : Boolean

    fun updateUser()

    fun getUser(username: String)

}