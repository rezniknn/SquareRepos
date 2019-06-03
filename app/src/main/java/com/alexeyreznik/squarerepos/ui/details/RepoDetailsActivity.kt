package com.alexeyreznik.squarerepos.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alexeyreznik.squarerepos.R
import com.alexeyreznik.squarerepos.data.model.Repo
import kotlinx.android.synthetic.main.activity_repo_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RepoDetailsActivity : AppCompatActivity() {

    private lateinit var repo: Repo
    private val viewModel: RepoDetailsViewModel by viewModel {
        parametersOf(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        repo = intent.getSerializableExtra(EXTRA_REPO) as? Repo
            ?: throw IllegalArgumentException("$EXTRA_REPO argument is required")
        setListeners()
        observe()
    }

    private fun setListeners() {
        add_to_bookmarks.setOnClickListener { viewModel.addBookmark() }
        remove_from_bookmarks.setOnClickListener { viewModel.removeBookmark() }
    }

    private fun observe() {
        viewModel.name.observe(this, Observer { nameValue -> name.text = nameValue})
        viewModel.starsCount.observe(this, Observer { starCount -> stars_count.text = starCount})
        viewModel.addBookmarkButtonEnabled.observe(this, Observer { enabled -> add_to_bookmarks.isEnabled = enabled })
        viewModel.removeBookmarkButtonEnabled.observe(this, Observer { enabled -> remove_from_bookmarks.isEnabled = enabled })
    }

    companion object {
        const val EXTRA_REPO = "repo"
    }
}
