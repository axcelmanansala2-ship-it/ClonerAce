package com.appcloner.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appcloner.data.model.ClonedApp
import com.appcloner.databinding.ItemClonedAppBinding
import java.text.SimpleDateFormat
import java.util.*

class ClonedAppAdapter(
    private val onLaunch: (ClonedApp) -> Unit,
    private val onDelete: (ClonedApp) -> Unit
) : ListAdapter<ClonedApp, ClonedAppAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemClonedAppBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(app: ClonedApp) {
            binding.tvCloneName.text = app.appName
            binding.tvClonePackage.text = app.clonedPackage
            binding.tvInstallDate.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                .format(Date(app.installDate))
            binding.chipGgPatched.visibility = if (app.isGameGuardianPatched)
                android.view.View.VISIBLE else android.view.View.GONE
            binding.btnLaunch.setOnClickListener { onLaunch(app) }
            binding.btnDelete.setOnClickListener { onDelete(app) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClonedAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<ClonedApp>() {
        override fun areItemsTheSame(old: ClonedApp, new: ClonedApp) = old.cloneId == new.cloneId
        override fun areContentsTheSame(old: ClonedApp, new: ClonedApp) = old == new
    }
}
