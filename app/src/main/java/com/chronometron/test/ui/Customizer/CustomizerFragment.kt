package com.chronometron.test.ui.Customizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chronometron.test.databinding.FragmentCustomizerBinding
import com.chronometron.test.R


class CustomizerFragment : Fragment() {

    private var _binding: FragmentCustomizerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val customizerViewModel = ViewModelProvider(this)[CustomizerViewModel::class.java]

        _binding = FragmentCustomizerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupSectionToggle(binding.buttonGoalsToggle, binding.textGoals)
        setupSectionToggle(binding.buttonCategoriesToggle, binding.recyclerCategories)
        setupSectionToggle(binding.buttonDateTimeToggle, binding.textDateTime)

        // Set initial icons
        setInitialIcons(binding.buttonGoalsToggle, binding.textGoals)
        setInitialIcons(binding.buttonCategoriesToggle, binding.recyclerCategories)
        setInitialIcons(binding.buttonDateTimeToggle, binding.textDateTime)

        // Observing the LiveData from ViewModel (if needed)
        customizerViewModel.text.observe(viewLifecycleOwner) {
            // Update your UI here if needed
        }
        return root
    }

    private fun setupSectionToggle(toggleButton: Button, contentView: View) {
        toggleButton.setOnClickListener {
            contentView.visibility = if (contentView.visibility == View.GONE) View.VISIBLE else View.GONE
            updateIcon(toggleButton, contentView.visibility)
        }
    }

    private fun setInitialIcons(button: Button, contentView: View) {
        updateIcon(button, contentView.visibility)
    }

    private fun updateIcon(button: Button, visibility: Int) {
        val iconRes = if (visibility == View.VISIBLE) R.drawable.ic_collapse else R.drawable.ic_expand
        button.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconRes, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
