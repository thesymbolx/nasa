package uk.co.nasa.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ApodFavorite (@PrimaryKey val imageUrl: String)