package com.jandas.codechallenge.model.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.jandas.codechallenge.db.entity.CartEntity

@Entity(
    tableName = "cart_info",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = CartEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id"),
            onDelete = CASCADE
        )
    )
)
data class CartQuantityEntity(
    @field:PrimaryKey val id: Int,
    @ColumnInfo(name = "quantity") val quantity: Int
)