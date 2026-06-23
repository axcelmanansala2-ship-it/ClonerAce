package com.appcloner.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.appcloner.R
import com.appcloner.databinding.DialogCloneNameBinding

class CloneNameDialog : DialogFragment() {
    private var onClone: ((String) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogCloneNameBinding.inflate(layoutInflater)
        val appName = arguments?.getString("app_name") ?: ""
        binding.etCloneName.setText("$appName Clone")
        return AlertDialog.Builder(requireContext())
            .setTitle("Clone App")
            .setView(binding.root)
            .setPositiveButton("Clone") { _, _ ->
                val name = binding.etCloneName.text.toString()
                onClone?.invoke(name)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    companion object {
        fun newInstance(packageName: String, appName: String, onClone: (String) -> Unit): CloneNameDialog {
            return CloneNameDialog().apply {
                arguments = Bundle().apply {
                    putString("package_name", packageName)
                    putString("app_name", appName)
                }
                this.onClone = onClone
            }
        }
    }
}
