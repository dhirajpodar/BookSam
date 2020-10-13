package com.example.booksam.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.example.booksam.R
import kotlinx.android.synthetic.main.custom_edittext.view.*

class CustomEditText : LinearLayout {


    private var hasError: Boolean = false

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.custom_edittext, this)

        if (attrs != null) {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
            val maxLines = typeArray.getInt(R.styleable.CustomEditText_maxLines, 10)
            val hint = typeArray.getString(R.styleable.CustomEditText_hint)
            val text = typeArray.getString(R.styleable.CustomEditText_text)
            val label = typeArray.getString(R.styleable.CustomEditText_label)
            val mandatory = typeArray.getBoolean(R.styleable.CustomEditText_mandatory, false)
            val allCaps = typeArray.getBoolean(R.styleable.CustomEditText_allCaps, false)
            val focusable = typeArray.getBoolean(R.styleable.CustomEditText_focusable, true)
            val clickable = typeArray.getBoolean(R.styleable.CustomEditText_clickable, true)

            et_input_text.setText(text)
            et_input_text.maxLines = maxLines
            tv_label.text = label
            et_input_text.setText(text)
            et_input_text.hint = hint
            if (mandatory) {
                tv_label_required.visibility = View.VISIBLE
            } else {
                tv_label_required.visibility = View.GONE
            }
            et_input_text.isFocusable = focusable
            et_input_text.isClickable = clickable
            et_input_text.isAllCaps = allCaps
        }

        et_input_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //Do nothing
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (hasError) {
                    setError(null)
                }
            }
        })
    }

    fun setError(message: String?) {
        if (message != null) {
            hasError = true
            tv_error_text.visibility = View.VISIBLE
            tv_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorRed, null))
            tv_error_text.setText(message)
        } else {
            hasError = false
            tv_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorBlack, null))
            tv_error_text.visibility = View.GONE
        }

    }

    fun text(): String? {
        return et_input_text.text.toString()
    }

    fun setText(text: String?) {
        et_input_text.setText(text)
    }

    fun getEditText(): EditText {
        return et_input_text
    }

}