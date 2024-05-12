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

    override fun updateUser(username: String,
                            name: String,
                            email: String,
                            password: String) : Boolean {

        val findUser = mList.find { it.username == username } == null

        if(findUser != null) {
            val index = mList.indexOfFirst { it.username == username }
            mList[index] = User(username, name, email, password)
            return true
        }
        return false
    }

    override fun getUser(username: String) {

    }
}