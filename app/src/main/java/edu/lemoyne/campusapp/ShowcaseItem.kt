
package edu.lemoyne.campusapp

import com.google.gson.annotations.SerializedName

data class ShowcaseItem(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("category")
    val category: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)
