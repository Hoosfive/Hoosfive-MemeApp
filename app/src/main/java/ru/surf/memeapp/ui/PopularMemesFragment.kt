package ru.surf.memeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_popular_memes.*
import ru.surf.memeapp.R
import ru.surf.memeapp.model.NetworkService
import ru.surf.memeapp.model.response.MemesResponseBody

class PopularMemesFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return PopularMemesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getMemes()
        return inflater.inflate(
            R.layout.fragment_popular_memes, container,
            false
        )
    }

    private fun getMemes() {
        setLoading(true)
        NetworkService.getMemes({ showMeme(it) }, { showError() })
    }

    private fun showMeme(memes: List<MemesResponseBody>) {
        setLoading(false)
    }

    private fun showError() {
        setLoading(false)
        val snack = Snackbar.make(
            popular_meme_layout,
            R.string.popularMemesSnackBarErrorText,
            Snackbar.LENGTH_LONG
        )
        snack.view.setBackgroundColor(resources.getColor(R.color.colorError))
        snack.show()
        textStub.visibility = View.VISIBLE
    }

    private fun setLoading(isLoading: Boolean) {

        if (isLoading)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }
}