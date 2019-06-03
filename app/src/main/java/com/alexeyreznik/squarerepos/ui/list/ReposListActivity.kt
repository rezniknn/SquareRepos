package com.alexeyreznik.squarerepos.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexeyreznik.squarerepos.R
import com.alexeyreznik.squarerepos.data.model.Repo
import com.alexeyreznik.squarerepos.ui.details.RepoDetailsActivity
import kotlinx.android.synthetic.main.activity_repos_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class ReposListActivity : AppCompatActivity() {

    private val viewModel: ReposListViewModel by viewModel()
    private lateinit var adapter: ReposListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos_list)
        initRecyclerView()
        setListeners()
        observe()
        lifecycle.addObserver(viewModel)
    }

    private fun initRecyclerView() {
        adapter = ReposListAdapter { repo -> navigateToRepoDetails(repo) }
        repos_list.adapter = adapter
        repos_list.layoutManager = LinearLayoutManager(this)
    }

    private fun setListeners() {
        srl.setOnRefreshListener { viewModel.reload() }
    }

    private fun observe() {
        viewModel.loading.observe(this, Observer { isLoading ->
            srl.isRefreshing = isLoading
        })
        viewModel.reposList.observe(this, Observer { repos ->
            adapter.items.clear()
            adapter.items.addAll(repos)
            adapter.notifyDataSetChanged()
        })
    }

    private fun navigateToRepoDetails(repo: Repo) {
        val intent = Intent(this, RepoDetailsActivity::class.java).apply {
            putExtra(RepoDetailsActivity.EXTRA_REPO, repo)
        }
        startActivity(intent)
    }
}
