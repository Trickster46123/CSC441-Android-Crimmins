

package edu.lemoyne.campusapp

import com.google.gson.annotations.SerializedName

data class ShowcaseItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("created_at")
    val createdAt: String
)
