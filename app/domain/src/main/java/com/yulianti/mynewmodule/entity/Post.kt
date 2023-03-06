package  com.example.forex.com.yulianti.mynewmodule.entity

import com.example.forex.usecase.usecase

data class Post (
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String
): usecase.Output