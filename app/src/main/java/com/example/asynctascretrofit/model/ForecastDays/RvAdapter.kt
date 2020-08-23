package com.example.asynctascretrofit.model.ForecastDays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asynctascretrofit.R
import kotlinx.android.synthetic.main.item_forcast.view.*

class RvAdapter : RecyclerView.Adapter<RvAdapter.RvVholder>() {
    private val list = arrayListOf<ForCastItemNumbeZeroFourth>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvVholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forcast,  parent,false)
        return RvVholder(view)
    }

    fun update(list: List<ForCastItemNumbeZeroFourth>?) {
        if (list!= null){
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RvVholder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    class RvVholder(v : View) : RecyclerView.ViewHolder(v){
        fun bind(CastItemNumbe: ForCastItemNumbeZeroFourth) {
            itemView.textTV.text = CastItemNumbe.main.temp.toString()
        }
    }
}