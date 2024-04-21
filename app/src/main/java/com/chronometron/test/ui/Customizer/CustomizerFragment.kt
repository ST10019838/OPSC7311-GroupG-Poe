package com.chronometron.test.ui.Customizer

import android.app.Dialog
import android.os.Bundle
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chronometron.test.databinding.FragmentCustomizerBinding
import com.chronometron.test.R

class CustomizerFragment : Fragment() {

    // Property to hold the binding for the views, ensuring safe access with non-null assertion.
    private var _binding: FragmentCustomizerBinding? = null
    private val binding get() = _binding!!

    // List to store categories throughout the application lifecycle.
    private val categories = mutableListOf<Category>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val customizerViewModel = ViewModelProvider(this)[CustomizerViewModel::class.java]

        // Inflate the layout for this fragment using binding to ensure direct access to views.
        _binding = FragmentCustomizerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupSectionToggle()
        observeViewModel(customizerViewModel)

        return root
    }

    private fun setupSectionToggle() {
        // Configures each toggle section with corresponding listeners and initial icons.
        listOf(
            Pair(binding.buttonGoalsToggle, binding.textGoals),
            Pair(binding.buttonCategoriesToggle, binding.layoutCategories),
            Pair(binding.buttonDateTimeToggle, binding.textDateTime)
        ).forEach { (button, view) ->
            setupSectionToggle(button, view)
            setInitialIcons(button, view)
        }

        // Set up listener for the add category button to open the dialog.
        binding.buttonAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun observeViewModel(customizerViewModel: CustomizerViewModel) {
        // Observes changes in LiveData from the ViewModel.
        customizerViewModel.text.observe(viewLifecycleOwner) {
            // React to data changes, if necessary.
        }
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
        val startIconRes = when (button.id) {
            R.id.button_goals_toggle -> R.drawable.ic_goals
            R.id.button_categories_toggle -> R.drawable.ic_categories
            R.id.button_date_time_toggle -> R.drawable.ic_date_time
            else -> 0
        }
        val endIconRes = if (visibility == View.VISIBLE) R.drawable.ic_collapse else R.drawable.ic_expand
        button.setCompoundDrawablesWithIntrinsicBounds(startIconRes, 0, endIconRes, 0)
    }

    // Shows a dialog to add a new category with a Spinner that displays both icons and text.
    private fun showAddCategoryDialog() {
        val dialog = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_add_category)
            window?.let {
                it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                it.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }

        val nameEditText = dialog.findViewById<EditText>(R.id.edit_category_name)
        val iconSpinner = dialog.findViewById<Spinner>(R.id.spinnerCategoryIcon)

        // Setting up the spinner with icons and names
        val icons = listOf(
            R.drawable.ic_fun, R.drawable.ic_sport, R.drawable.ic_work,
            R.drawable.ic_coding, R.drawable.ic_varsity, R.drawable.ic_star,
            R.drawable.ic_arrow
        )
        val iconNames = listOf("Fun", "Sport", "Work", "Coding", "Varsity", "Star", "Arrow")
        val adapter = IconTextAdapter(requireContext(), icons, iconNames)
        iconSpinner.adapter = adapter

        dialog.findViewById<Button>(R.id.button_save_category).setOnClickListener {
            val name = nameEditText.text.toString()
            val iconResId = icons[iconSpinner.selectedItemPosition]
            categories.add(Category(name, iconResId))
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.text_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Custom adapter for handling icons and text in the spinner.
class IconTextAdapter(
    context: Context,
    private val icons: List<Int>,
    private val texts: List<String>
) : ArrayAdapter<String>(context, R.layout.spinner_item, texts) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item, parent, false)
        val iconView = view.findViewById<ImageView>(R.id.icon)
        val textView = view.findViewById<TextView>(R.id.text)

        iconView.setImageResource(icons[position])
        textView.text = texts[position]

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}

// Data class to hold category information.
data class Category(val name: String, val iconResId: Int)
