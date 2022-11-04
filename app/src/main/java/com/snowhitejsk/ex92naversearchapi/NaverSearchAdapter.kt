package com.snowhitejsk.ex92naversearchapi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.snowhitejsk.ex92naversearchapi.databinding.RecyclerItemBinding

class NaverSearchAdapter constructor(var context: Context, var items:MutableList<NaverSearchItem>) : RecyclerView.Adapter<NaverSearchAdapter.VH>() {

    inner class VH constructor(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding: RecyclerItemBinding = RecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        //html문자열의 태그문을 제거.
        var title:Spanned = HtmlCompat.fromHtml(items[position].title, HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binding.tvTitle.text = title

        holder.binding.tvPrice.text = items[position].lprice +"원"
        holder.binding.tvMall.text = items[position].mallName

        Glide.with(context).load(items[position].image ).error(R.drawable.dog).into(holder.binding.iv)

        holder.binding.root.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(items[position].link)))
        }
    }

    override fun getItemCount(): Int = items.size
}