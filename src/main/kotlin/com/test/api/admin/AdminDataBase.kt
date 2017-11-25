package com.test.api.admin

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.test.api.QueryResponse
import org.springframework.stereotype.Component
import java.util.HashMap

@Component
class AdminDataBase {

    private var uri = MongoClientURI("mongodb://user:user@ds119436.mlab.com:19436/api_db_rightcom")
    private var mongoClient = MongoClient(uri)
    private var apiDB = mongoClient.getDB("api_db_rightcom")
    private var adminCollection = apiDB.getCollection("Admins")

    private fun getMap(admin: AdminModel) : Map<String, Any>{
        val map = HashMap<String, Any>()
        map.put("adminMail", admin.adminMail)
        map.put("adminPassword", admin.adminPassword)
        return  map
    }

    fun getAllAdmin() : ArrayList<AdminModel>{
        val adminList = arrayListOf<AdminModel>()
        val cursor = adminCollection.find()
        try {
            while (cursor.hasNext()) {
                val obj = cursor.next()
                adminList.add(AdminModel(obj["adminMail"] as String, obj["adminPassword"] as String))
            }
        } finally {
            cursor.close()
        }
        return adminList
    }

    fun checkIfIsAdmin(adminModel: AdminModel) : QueryResponse {
        for(i in getAllAdmin()){
            if (i.adminMail.equals(adminModel.adminMail) && i.adminPassword.equals(adminModel.adminPassword)){
                return QueryResponse(true)
            }
        }
        return QueryResponse(success = false)
    }

    fun addAdmin(admin: AdminModel) : QueryResponse {
        try {
            adminCollection.insert(BasicDBObject(getMap(admin)))
            return QueryResponse(true)
        } catch (e: Exception){
            return QueryResponse(false)
        }
    }
}