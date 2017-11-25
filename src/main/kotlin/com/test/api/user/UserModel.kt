package com.test.api.user

class UserModel{
    var userId: Int = 0
    var userFirstname: String = ""
    var userSurname: Any = ""
    var userFunction: String = ""

    constructor(userId: Int, userFirstname: String, userSurname: Any, userFunction: String) {
        this.userId = userId
        this.userFirstname = userFirstname
        this.userSurname = userSurname
        this.userFunction = userFunction
    }
}