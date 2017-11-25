package com.test.api.admin

class AdminModel {

    var adminMail: String = ""
    var adminPassword: String = ""

    constructor(adminMail: String, adminPassword: String) {
        this.adminMail = adminMail
        this.adminPassword = adminPassword
    }
}