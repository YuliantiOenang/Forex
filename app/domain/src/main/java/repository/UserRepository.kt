package repository

import com.example.forex.com.yulianti.mynewmodule.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(userId: String): Flow<User>
    fun refreshUser(userId: String): Flow<User>
    fun getUsers(): Flow<List<User>>
}