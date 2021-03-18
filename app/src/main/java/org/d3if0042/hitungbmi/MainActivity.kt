package org.d3if0042.hitungbmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import org.d3if0042.hitungbmi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{hitung()}
        binding.button2.setOnClickListener{reset()}
    }
    private fun hitung(){
        val berat= binding.beratTf.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(this, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi= binding.tinggiTf.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(this, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggiCm= tinggi.toFloat() / 100
        val selectedId= binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale= selectedId==R.id.priaRB
        val bmi= berat.toFloat() / (tinggiCm * tinggiCm)
        val kategori= getKategori(bmi,isMale)
        binding.bmiTextView.text = getString(R.string.bmi_x, bmi)
        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
    }
    private fun getKategori(bmi: Float, isMale: Boolean): String {
        val stringRes = if (isMale) {
            when{
                bmi < 20.5 -> R.string.kurus
                bmi >= 27.0 -> R.string.gemuk
                else -> R.string.ideal
            }
        } else {
            when{
                bmi < 18.5 -> R.string.kurus
                bmi >= 25.0 -> R.string.gemuk
                else -> R.string.ideal
            }
        }
        return getString(stringRes)
    }
    private fun reset(){
        val berat= binding.beratTf.text.toString()
        val tinggi= binding.tinggiTf.text.toString()
        val selectedId= binding.radioGroup.checkedRadioButtonId
        if (TextUtils.isEmpty(berat) && TextUtils.isEmpty(tinggi) && selectedId== -1) {
            Toast.makeText(this, R.string.already_empty, Toast.LENGTH_LONG).show()
            return
        }
        binding.tinggiTf.setText("")
        binding.beratTf.setText("")
        binding.priaRB.setChecked(false)
        binding.wanitaRB.setChecked(false)
        binding.bmiTextView.setText("")
        binding.kategoriTextView.setText("")

    }

}
