package com.appcloner.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appcloner.data.model.AppInfo
import com.appcloner.databinding.ItemAppBinding
import com.bumptech.glide.Glide
import com.appcloner.utils.PackageUtils

class AppListAdapter(
    private val onClone: (AppInfo) -> Unit
) : ListAdapter<AppInfo, AppListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemAppBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(app: AppInfo) {
            binding.tvAppName.text = app.appName
            binding.tvPackageName.text = app.packageName
            binding.tvAppSize.text = PackageUtils.formatSize(app.sizeBytes)
            binding.ivClonedBadge.visibility = if (app.isCloned) android.view.View.VISIBLE else android.view.View.GONE
            app.icon?.let { binding.ivAppIcon.setImageDrawable(it) }
                ?: binding.ivAppIcon.setImageResource(android.R.drawable.sym_def_app_icon)
            binding.btnClone.setOnClickListener { onClone(app) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<AppInfo>() {
        override fun areItemsTheSame(old: AppInfo, new: AppInfo) = old.packageName == new.packageName
        override fun areContentsTheSame(old: AppInfo, new: AppInfo) = old == new
    }
}
