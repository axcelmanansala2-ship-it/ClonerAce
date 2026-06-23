package com.appcloner.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.appcloner.databinding.DialogCloneProgressBinding

class CloneProgressDialog : DialogFragment() {
    private var _binding: DialogCloneProgressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCloneProgressBinding.inflate(layoutInflater)
        isCancelable = false
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    fun updateProgress(step: String, percent: Int) {
        binding.tvStep.text = step
        binding.progressBar.progress = percent
        binding.tvPercent.text = "$percent%"
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
