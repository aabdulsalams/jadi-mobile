package com.capstone.jadi.presentation.history

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.capstone.jadi.data.model.DetailDisease
import com.capstone.jadi.data.model.History
import com.capstone.jadi.databinding.ItemHistoryRowBinding
import com.capstone.jadi.presentation.detail.DetailActivity
import com.capstone.jadi.utils.ConstVal
import com.capstone.jadi.utils.ext.setImageUrl
import com.capstone.jadi.utils.ext.timeStamptoString

class HistoryAdapter(
    private val historyList: List<History>
): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.HistoryViewHolder {
        val binding = ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        historyList[position].let { history ->
            holder.bind(history)
        }
    }

    override fun getItemCount(): Int = historyList.size

    inner class HistoryViewHolder(private val binding: ItemHistoryRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(history: History){
            with(binding){
                rvName.text = history.disease_name
                rvDate.text = history.createdAt.timeStamptoString()
                rvDiseaseId.text = history.diseaseId


                rvImg.setImageUrl(history.photoUrl, true)
            }
            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(ConstVal.BUNDLE_KEY_HISTORY, history)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.rvImg, "thumbnail"),
                    Pair(binding.rvName, "title"),
                    Pair(binding.rvDiseaseId, "diseaseId")
                )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }


}