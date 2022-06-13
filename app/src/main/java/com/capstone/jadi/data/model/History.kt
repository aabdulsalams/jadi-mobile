package com.capstone.jadi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class History(

    @SerializedName("id")
    val id: String,
    @SerializedName("user_id")
    val name: String,
    @SerializedName("disease_id")
    val diseaseId: String,
    @SerializedName("image_url")
    val photoUrl: String,
    @SerializedName("scan_date")
    val createdAt: String,
    @SerializedName("disease_name")
    val disease_name: String

): Parcelable
