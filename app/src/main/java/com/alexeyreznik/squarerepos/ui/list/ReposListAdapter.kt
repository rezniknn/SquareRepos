package com.alexeyreznik.squarerepos.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyreznik.squarerepos.R
import com.alexeyreznik.squarerepos.data.model.Repo
import kotlinx.android.synthetic.main.layout_repo_item.view.*

class ReposListAdapter(private val onItemClick: ((Repo) -> Unit)? = null) :
    RecyclerView.Adapter<ReposListAdapter.ViewHolder>() {

    val items = mutableListOf<RepoListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_repo_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: RepoListItem) {
            itemView.name.text = item.repo.name
            itemView.stars_count.text = item.repo.stars.toString()
            itemView.favorite.visibility = if (item.bookmarked) View.VISIBLE else View.GONE
            itemView.setOnClickListener { onItemClick?.invoke(item.repo) }
        }
    }
}