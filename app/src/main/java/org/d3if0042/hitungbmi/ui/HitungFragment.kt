package org.d3if0042.hitungbmi.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if0042.hitungbmi.R
import org.d3if0042.hitungbmi.data.KategoriBmi
import org.d3if0042.hitungbmi.databinding.FragmentHitungBinding

class HitungFragment : Fragment(){
    private lateinit var binding: FragmentHitungBinding
    private lateinit var kategoriBmi: KategoriBmi
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener{hitung()}
        binding.button2.setOnClickListener{reset()}
        binding.shareButton.setOnClickListener { shareData() }
        binding.saranButton.setOnClickListener{
            val berat= binding.beratTf.text.toString()
            val tinggi= binding.tinggiTf.text.toString()
            val selectedId= binding.radioGroup.checkedRadioButtonId
            val sex= if (selectedId == R.id.priaRB)
                getString(R.string.pria)
            else
                getString(R.string.wanita)
            val bmi= binding.bmiTextView.text.toString()

            val action= HitungFragmentDirections.actionHitungFragmentToSaranFragment(kategoriBmi, berat, tinggi, sex, bmi)
            findNavController().navigate(action)

        }
        setHasOptionsMenu(true)
        return binding.root
    }
    private fun hitung(){
        val berat= binding.beratTf.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi= binding.tinggiTf.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggiCm= tinggi.toFloat() / 100
        val selectedId= binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale= selectedId== R.id.priaRB
        val bmi= berat.toFloat() / (tinggiCm * tinggiCm)
        val kategori= getKategori(bmi,isMale)
        binding.bmiTextView.text = getString(R.string.bmi_x, bmi)
        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
        binding.buttonGroup.visibility = View.VISIBLE

    }
    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.priaRB)
            getString(R.string.pria)
        else
            getString(R.string.wanita)
        val message = getString(R.string.bagikan_template,
                binding.beratTf.text,
                binding.tinggiTf.text,
                gender,
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
    private fun getKategori(bmi: Float, isMale: Boolean): String {
        kategoriBmi = if (isMale) {
            when{
                bmi < 20.5 -> KategoriBmi.KURUS
                bmi >= 27.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        } else {
            when{
                bmi < 18.5 -> KategoriBmi.KURUS
                bmi >= 25.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }
        val stringRes = when (kategoriBmi) {
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }

    private fun reset(){
        val berat= binding.beratTf.text.toString()
        val tinggi= binding.tinggiTf.text.toString()
        val selectedId= binding.radioGroup.checkedRadioButtonId
        if (TextUtils.isEmpty(berat) && TextUtils.isEmpty(tinggi) && selectedId== -1) {
            Toast.makeText(context, R.string.already_empty, Toast.LENGTH_LONG).show()
            return
        }
        binding.tinggiTf.setText("")
        binding.beratTf.setText("")
        binding.priaRB.setChecked(false)
        binding.wanitaRB.setChecked(false)
        binding.bmiTextView.setText("")
        binding.kategoriTextView.setText("")
        binding.saranButton.visibility= View.INVISIBLE
    }
}