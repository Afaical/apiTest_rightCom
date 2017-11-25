package com.test.api.user

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.springframework.stereotype.Component
import java.util.HashMap
import com.test.api.QueryResponse

@Component
class UserDataBase{

    private var uri = MongoClientURI("mongodb://user:user@ds119436.mlab.com:19436/api_db_rightcom")
    private var mongoClient = MongoClient(uri)
    private var apiDB = mongoClient.getDB("api_db_rightcom")
    private var userCollection = apiDB.getCollection("Users")

    private fun getMap(user: UserModel) : Map<String, Any>{
        val map = HashMap<String, Any>()
        map.put("userId", user.userId)
        map.put("userFirstname", user.userFirstname)
        map.put("userSurname", user.userSurname)
        map.put("userFunction", user.userFunction)
        return  map
    }

    fun addUser(user: UserModel) : QueryResponse {
        try {
            userCollection.insert(BasicDBObject(getMap(user)))
            return QueryResponse(true)
        } catch (e: Exception){
//            throw Exception(e.message)
            return QueryResponse(false)
        }
    }

    fun getAllUser() : ArrayList<UserModel> {
        val userList = arrayListOf<UserModel>()
        val cursor = userCollection.find()
        try {
            while (cursor.hasNext()) {
                val obj = cursor.next()
                userList.add(UserModel(obj["userId"] as Int,
                        obj["userFirstname"] as String,
                        obj["userSurname"] as String,
                        obj["userFunction"] as String))
            }
        } finally {
            cursor.close()
        }
        return userList
    }

    fun updateUser(user: UserModel) : QueryResponse {
        val newDocument = BasicDBObject(getMap(user))
        val searchQuery = BasicDBObject().append("userId", user.userId)
        try {
            userCollection.update(searchQuery, newDocument)
            return QueryResponse(true)
        } catch (e: Exception){
            return QueryResponse(false)
        }
    }

    fun deleteUser(userId: Int) : QueryResponse {
        val query = BasicDBObject()
        query.append("userId", userId)
        try {
            userCollection.remove(query)
            return QueryResponse(true)
        } catch (e: Exception){
            return QueryResponse(false)
        }
    }
}