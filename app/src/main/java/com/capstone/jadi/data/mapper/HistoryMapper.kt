package com.capstone.jadi.data.mapper

import com.capstone.jadi.data.local.entity.HistoryEntity
import com.capstone.jadi.data.model.History

fun historyToHistoryEntity(history: History): HistoryEntity {
    return HistoryEntity(
        id = history.id,
        photoUrl = history.photoUrl
    )
}
