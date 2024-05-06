package com.allcarsinone.allcarsinone

class ListUsersDataSource(private val mList : MutableList<User>): UsersDataSource{

    override fun createUser(username: String,
                            name: String,
                            email: String,
                            password: String) : Boolean {
        if(mList.find { it.username == username } == null ||
            mList.find { it.email == email} == null )
            return false

        mList.add(User(username, name, email, password))
        return true;
    }

    override fun updateUser() {

    }

    override fun getUser(username: String) {

    }
}