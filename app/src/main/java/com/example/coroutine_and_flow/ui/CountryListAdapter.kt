package com.example.coroutine_and_flow.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutine_and_flow.databinding.CountryListItemBinding
import com.example.coroutine_and_flow.model.CountryModel

class CountryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<CountryModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CountryViewHolder(
            CountryListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CountryViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setCountryList(countryList: List<CountryModel>) {
        items = countryList
        notifyDataSetChanged()
    }

    class CountryViewHolder constructor(
        private val binding: CountryListItemBinding
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind( countryModel: CountryModel) {
            binding.countryName.text = countryModel.name
            binding.countryTimeZone.text = countryModel.timeZone.toString()
        }
    }

}