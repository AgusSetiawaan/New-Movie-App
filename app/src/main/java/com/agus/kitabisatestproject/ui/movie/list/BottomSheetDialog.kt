package com.agus.kitabisatestproject.ui.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agus.kitabisatestproject.R
import com.agus.kitabisatestproject.model.Category
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottomsheet.*

class BottomSheetDialog constructor(private val listener: CategoryListener) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindButton()
    }

    private fun bindButton(){
        btn_popular.setOnClickListener {
            listener.onCategorySelected(Category.POPULAR)
            dismiss()
        }
        btn_upcoming.setOnClickListener {
            listener.onCategorySelected(Category.UPCOMING)
            dismiss()
        }
        btn_top_rated.setOnClickListener {
            listener.onCategorySelected(Category.TOP_RATED)
            dismiss()
        }
        btn_now_playing.setOnClickListener {
            listener.onCategorySelected(Category.NOW_PLAYING)
            dismiss()
        }
        btn_favourite.setOnClickListener {
            listener.onCategorySelected(Category.FAVOURITE)
            dismiss()
        }
    }

    interface CategoryListener {
        fun onCategorySelected(category: Category)
    }
}
