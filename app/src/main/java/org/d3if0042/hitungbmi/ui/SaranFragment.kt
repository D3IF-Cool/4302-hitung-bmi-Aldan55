package org.d3if0042.hitungbmi.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if0042.hitungbmi.R
import org.d3if0042.hitungbmi.databinding.FragmentSaranBinding
import org.d3if0042.hitungbmi.data.KategoriBmi

class SaranFragment : Fragment() {
    private val args: SaranFragmentArgs by navArgs()
    private lateinit var binding: FragmentSaranBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)
        updateUI(args.kategori)
        binding.shareButton.setOnClickListener { shareData() }
        return binding.root
    }

    private fun updateUI(kategori: KategoriBmi) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriBmi.KURUS -> {
                actionBar?.title = getString(R.string.judul_kurus)
                binding.imageView.setImageResource(R.drawable.kurus)
                binding.textView.text = getString(R.string.saran_kurus)
            }
            KategoriBmi.IDEAL -> {
                actionBar?.title = getString(R.string.judul_ideal)
                binding.imageView.setImageResource(R.drawable.ideal)
                binding.textView.text = getString(R.string.saran_ideal)
            }
            KategoriBmi.GEMUK -> {
                actionBar?.title = getString(R.string.judul_gemuk)
                binding.imageView.setImageResource(R.drawable.gemuk)
                binding.textView.text = getString(R.string.saran_gemuk)
            }
        }
        val bmi= args.bmi
        binding.beratTf.text= getString(R.string.berat_x, args.berat)
        binding.tinggiTf.text= getString(R.string.tinggi_x, args.tinggi)
        binding.kelaminTf.text= getString(R.string.jenis_kelamin_x, args.sex)
        binding.bmiTextView.text= bmi
        binding.kategoriTextView.text= getString(R.string.kategori_x, args.kategori)
    }
    private fun shareData() {
        val message = getString(R.string.bagikan_template_x,
                binding.beratTf.text,
                binding.tinggiTf.text,
                binding.kelaminTf.text,
                binding.bmiTextView.text,
                binding.kategoriTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                        requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

}