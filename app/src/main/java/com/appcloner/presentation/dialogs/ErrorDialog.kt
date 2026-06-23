package com.appcloner.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.appcloner.databinding.DialogErrorBinding

class ErrorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogErrorBinding.inflate(layoutInflater)
        val message = arguments?.getString("message") ?: "An error occurred"
        binding.tvErrorMessage.text = message
        return AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setView(binding.root)
            .setPositiveButton("OK", null)
            .create()
    }

    companion object {
        fun newInstance(message: String) = ErrorDialog().apply {
            arguments = Bundle().apply { putString("message", message) }
        }
    }
}
