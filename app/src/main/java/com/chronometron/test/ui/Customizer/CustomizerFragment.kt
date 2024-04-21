package com.chronometron.test.ui.Customizer
// Import necessary Android and Kotlin libraries for UI and application functionality
import android.app.Dialog  // Used to create new dialog windows
import android.os.Bundle  // Allows passing data between activities as key-value pairs
import android.content.Context  // Provides access to application-specific resources
import android.view.LayoutInflater  // Instantiates layout XML file into its corresponding View objects
import android.view.View  // Basic building block for user interface components
import android.view.ViewGroup  // Special view that can contain other views (called children)
import android.widget.*  // Widgets that are used within layouts, such as Button, TextView, etc.
import androidx.core.content.ContextCompat  // Helper for accessing features in Context
import androidx.fragment.app.Fragment  // A piece of an application's user interface or behavior that can be placed in an Activity
import androidx.lifecycle.ViewModelProvider  // Provides ViewModels to a scope, such as a fragment or an activity
import android.view.Gravity  // Constants used to specify the placement of a view within a ViewGroup
import android.util.TypedValue  // Container for a dynamically typed data value
import com.chronometron.test.databinding.FragmentCustomizerBinding  // Generated binding class from the layout file for easier component handling
import com.chronometron.test.R  // Accessor to the resources

// Define a Fragment for handling the customization part of the application
class CustomizerFragment : Fragment() {

    private var _binding: FragmentCustomizerBinding? = null  // Nullable binding variable for accessing the views
    private val binding get() = _binding!!  // Non-nullable getter for the binding

    private val categories = mutableListOf<Category>()  // List to hold the categories dynamically

    // Called to have the fragment instantiate its user interface view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val customizerViewModel = ViewModelProvider(this)[CustomizerViewModel::class.java]

        _binding = FragmentCustomizerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupSectionToggle()
        observeViewModel(customizerViewModel)

        return root
    }


    // Display categories in the user interface
    private fun displayCategories() {
        val categoryItemsLayout = binding.categoryItemsContainer
        categoryItemsLayout.removeAllViews()  // Remove all views before adding new ones

        val inflater = LayoutInflater.from(requireContext())  // Get the LayoutInflater from the context
        categories.forEach { category ->
            val categoryView = inflater.inflate(R.layout.category_item, categoryItemsLayout, false) as LinearLayout
            configureCategoryView(categoryView, category)  // Configure the category view

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL  // Center the category views horizontally
            layoutParams.bottomMargin = TypedValue.applyDimension(  // Apply bottom margin in dp
                TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics
            ).toInt()
            categoryView.layoutParams = layoutParams

            categoryItemsLayout.addView(categoryView)  // Add the category view to the layout
        }
    }

    // Configure individual category views
    private fun configureCategoryView(view: View, category: Category) {
        val iconView = view.findViewById<ImageView>(R.id.category_icon)  // Find the ImageView
        val textView = view.findViewById<TextView>(R.id.category_name)  // Find the TextView
        iconView.setImageResource(category.iconResId)  // Set the icon from the category
        textView.text = category.name  // Set the text from the category
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))  // Set the text color
    }

    // Setup toggle buttons and their actions
    private fun setupSectionToggle() {
        listOf(
            Pair(binding.buttonGoalsToggle, binding.textGoals),
            Pair(binding.buttonCategoriesToggle, binding.layoutCategories),
            Pair(binding.buttonDateTimeToggle, binding.textDateTime)
        ).forEach { (button, view) ->
            setupSectionToggle(button, view)  // Setup toggle for each pair
            setInitialIcons(button, view)  // Set initial icons based on state
        }

        binding.buttonAddCategory.setOnClickListener {
            showAddCategoryDialog()  // Show dialog to add a new category
        }
    }

    // Observe changes from the ViewModel
    private fun observeViewModel(customizerViewModel: CustomizerViewModel) {
        customizerViewModel.text.observe(viewLifecycleOwner) { }
    }

    // Toggle visibility of views and update icons accordingly
    private fun setupSectionToggle(toggleButton: Button, contentView: View) {
        toggleButton.setOnClickListener {
            contentView.visibility = if (contentView.visibility == View.GONE) View.VISIBLE else View.GONE
            updateIcon(toggleButton, contentView.visibility)  // Update the icon based on visibility
        }
    }

    // Set initial icons based on visibility
    private fun setInitialIcons(button: Button, contentView: View) {
        updateIcon(button, contentView.visibility)
    }

    // Update icons for buttons based on the visibility of the associated view
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

    // Show a dialog to add a new category
    private fun showAddCategoryDialog() {
        val dialog = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_add_category)  // Set the content view for the dialog
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)  // Set layout parameters
            window?.setBackgroundDrawableResource(android.R.color.transparent)  // Set the background to transparent
        }

        val nameEditText = dialog.findViewById<EditText>(R.id.edit_category_name)  // Get the EditText for name input
        val iconSpinner = dialog.findViewById<Spinner>(R.id.spinnerCategoryIcon)  // Get the Spinner for selecting an icon
        val icons = listOf(R.drawable.ic_fun, R.drawable.ic_sport, R.drawable.ic_work, R.drawable.ic_coding, R.drawable.ic_varsity, R.drawable.ic_star, R.drawable.ic_arrow)  // List of icons
        val texts = listOf("Fun", "Sport", "Work", "Coding", "Varsity", "Star", "Arrow")  // Corresponding descriptions
        val adapter = IconTextAdapter(requireContext(), icons, texts)  // Adapter for the Spinner
        iconSpinner.adapter = adapter  // Set the adapter to the Spinner

        dialog.findViewById<Button>(R.id.button_save_category).setOnClickListener {
            val name = nameEditText.text.toString()  // Get the entered name
            if (name.isEmpty()) {
                nameEditText.error = "Name cannot be empty"  // Show error if name is empty
            } else {
                val iconResId = icons[iconSpinner.selectedItemPosition]  // Get the selected icon
                categories.add(Category(name, iconResId))  // Add new category
                dialog.dismiss()  // Dismiss the dialog
                displayCategories()  // Refresh the category display
            }
        }

        dialog.findViewById<TextView>(R.id.text_cancel).setOnClickListener {
            dialog.dismiss()  // Dismiss the dialog on cancel
        }

        dialog.show()  // Show the dialog
    }

    // Called when the fragment view is being destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clear the binding
    }
}

// Adapter class for displaying icons with text in a Spinner
class IconTextAdapter(context: Context, private val icons: List<Int>, private val texts: List<String>) : ArrayAdapter<String>(context, R.layout.spinner_item, texts) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)  // Obtain the LayoutInflater from context

    // Return the view for each item in the Spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item, parent, false)  // Inflate the spinner item layout
        val iconView = view.findViewById<ImageView>(R.id.icon)  // Get the icon view
        val textView = view.findViewById<TextView>(R.id.text)  // Get the text view
        iconView.setImageResource(icons[position])  // Set the icon for the item
        textView.text = texts[position]  // Set the text for the item

        return view  // Return the composed view
    }

    // Return the dropdown view for each item in the Spinner
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View = getView(position, convertView, parent)
}

// Data class for holding category information
data class Category(val name: String, val iconResId: Int)
