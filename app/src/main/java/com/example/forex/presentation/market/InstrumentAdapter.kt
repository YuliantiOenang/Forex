package com.example.forex.presentation.market

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.forex.R
import com.example.forex.domain.repository.model.Instrument

class InstrumentAdapter : RecyclerView.Adapter<InstrumentAdapter.InstrumentViewHolder>() {
    var data: MutableList<Instrument> = mutableListOf()
    lateinit var context: Context

    class InstrumentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val symbol: TextView
        val change: TextView
        val sell: TextView
        val buy: TextView

        init {
            symbol = view.findViewById(R.id.txt_symbol)
            change = view.findViewById(R.id.txt_change)
            sell = view.findViewById(R.id.txt_sell)
            buy = view.findViewById(R.id.txt_buy)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstrumentViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_instrument, parent, false)
        return InstrumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstrumentViewHolder, position: Int) {
        holder.symbol.text = data[position].symbol
        holder.change.text = context.getString(R.string.change_format, data[position].change)
        holder.change.setTextColor(
            if (data[position].change >= 0) {
                ContextCompat.getColor(context, R.color.green)
            } else {
                ContextCompat.getColor(context, R.color.red)
            }
        )
        holder.sell.text = data[position].sell.toString()
        holder.buy.text = data[position].buy.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @JvmName("setNewData")
    fun setData(newData: List<Instrument>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}