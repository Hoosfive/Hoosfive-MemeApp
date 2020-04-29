package ru.surf.memeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_popular_memes.*
import ru.surf.memeapp.R
import ru.surf.memeapp.model.NetworkService
import ru.surf.memeapp.model.response.MemesResponseBody
import ru.surf.memeapp.ui.adapter.MemeListAdapter

class PopularMemesFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return PopularMemesFragment()
        }

    }

    private val adapter = MemeListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(
            R.layout.fragment_popular_memes, container,
            false
        )
        initRecyclerView(v)
        getMemes()
        return v
    }

    private fun getMemes() {
        NetworkService.getMemes({
            showMemes(it)
            removeLoading()
            removeStub()
        }, {
            showError()
            removeLoading()
        })
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.memes_rv)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
    }


    private fun showMemes(memes: List<MemesResponseBody>) {
        adapter.setData(memes)
    }

    private fun showError() {
        val snack = Snackbar.make(
            popular_meme_layout,
            R.string.popularMemesSnackBarErrorText,
            Snackbar.LENGTH_LONG
        )
        snack.view.setBackgroundColor(resources.getColor(R.color.colorError))
        snack.show()
        textStub.visibility = View.VISIBLE
    }

    private fun removeLoading() {
        progressBar.visibility = View.GONE
    }

    private fun removeStub() {
        stub.visibility = View.GONE
    }
}