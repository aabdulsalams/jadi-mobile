package com.capstone.jadi.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.room.Room
import com.capstone.jadi.R
import com.capstone.jadi.data.local.HistoryDatabase
import com.capstone.jadi.data.local.entity.HistoryEntity
import com.capstone.jadi.utils.ConstVal.DB_NAME
import com.capstone.jadi.utils.urlToBitmap

internal class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var history: MutableList<HistoryEntity> = mutableListOf()

    override fun onCreate() {
    }

    override fun onDataSetChanged() {
        val database = Room.databaseBuilder(
            context.applicationContext, HistoryDatabase::class.java,
            DB_NAME
        ).build()
        database.getHistoryDao().getAllHistories().forEach {
            history.add(
                HistoryEntity(
                    it.id,
                    it.photoUrl
                )
            )
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = history.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.history_widget_item)
        rv.setImageViewBitmap(R.id.imgHistory, urlToBitmap(history[position].photoUrl))

        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}