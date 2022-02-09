package com.example.coroutine_and_flow.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutine_and_flow.databinding.CountryListItemBinding
import com.example.coroutine_and_flow.data.network.responses.Country

class CountryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<Country> = ArrayList()

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

    fun setCountryList(countryList: List<Country>) {
        items = countryList
        notifyDataSetChanged()
    }

    class CountryViewHolder constructor(
        private val binding: CountryListItemBinding
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.countryName.text = country.name
            binding.countryTimeZone.text = country.timeZone.toString()
        }
    }

}