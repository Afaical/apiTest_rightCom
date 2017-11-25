package com.test.api

import com.test.api.admin.AdminDataBase
import com.test.api.admin.AdminModel
import com.test.api.user.UserDataBase
import com.test.api.user.UserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.Random

@CrossOrigin
@RestController
class Controller {

    @Autowired
    private lateinit var database: UserDataBase
    @Autowired
    private lateinit var databaseAdmin: AdminDataBase

    /**
     * ****************************USER REQUEST***************************************
     **/
    @RequestMapping("/user/getAllUser", method = arrayOf(RequestMethod.GET))
    fun getAllUser() = database.getAllUser()

    @RequestMapping("/user/addUser", method = arrayOf(RequestMethod.POST))
    fun addUser(
            @RequestParam(value = "userFirstname", required = true) userFirstname: String,
            @RequestParam(value = "userSurname", required = true) userSurname: String,
            @RequestParam(value = "userFunction", required = true) userFunction: String): QueryResponse {
        return database.addUser(UserModel(Random().nextInt(25112017), userFirstname, userSurname, userFunction))
    }

    @RequestMapping("/user/updateUser", method = arrayOf(RequestMethod.PUT))
    fun updateUser(
            @RequestParam(value = "userId", required = true) userId: Int,
            @RequestParam(value = "userFirstname", required = true) userFirstname: String,
            @RequestParam(value = "userSurname", required = true) userSurname: String,
            @RequestParam(value = "userFunction", required = true) userFunction: String): QueryResponse {
        return database.updateUser(UserModel(userId, userFirstname, userSurname, userFunction))
    }

    @RequestMapping("/user/deleteUser", method = arrayOf(RequestMethod.DELETE))
    fun deleteUser(@RequestParam(value = "userId", required = true) userId: Int) : QueryResponse {
        return database.deleteUser(userId)
    }

    /**
     * ****************************ADMIN REQUEST***************************************
     **/
    @RequestMapping("/admin/showAllAdmin", method = arrayOf(RequestMethod.GET))
    fun showAllAdmin() = databaseAdmin.getAllAdmin()

    @RequestMapping("/admin/makeConnexion", method = arrayOf(RequestMethod.GET))
    fun makeConnexion(
            @RequestParam(value = "adminMail", required = true) adminMail: String,
            @RequestParam(value = "adminPassword", required = true) adminPassword: String) : QueryResponse {
        return databaseAdmin.checkIfIsAdmin(AdminModel(adminMail, adminPassword))
    }

    @RequestMapping("/admin/addAdmin", method = arrayOf(RequestMethod.POST))
    fun addAmin(
            @RequestParam(value = "adminMail", required = true) adminMail: String,
            @RequestParam(value = "adminPassword", required = true) adminPassword: String) : QueryResponse {
        return databaseAdmin.addAdmin(AdminModel(adminMail, adminPassword))
    }
}