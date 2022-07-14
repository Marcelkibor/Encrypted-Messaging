package com.example.crypto
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crypto.databinding.DecryptionActivityBinding
import com.scottyab.aescrypt.AESCrypt
import java.security.GeneralSecurityException

private lateinit var binding: DecryptionActivityBinding
class DecryptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DecryptionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        val msg = bundle?.getString("msg")
        //set text view with encrypted message
        binding.tvMessage.text = msg
//        call decryption function
        binding.btDecrypt.setOnClickListener{
            decryption()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun decryption() = //decrypt key {use encrypted text and password}
        try {
            val decrypted = AESCrypt.decrypt(
                binding.edDecKey.text.toString(),
                binding.tvMessage.text.toString()
            )
           binding.edDecKey.visibility = View.GONE
            binding.tvMessage.text = decrypted.toString()
            binding.tvDecrypt.text = "Decrypted Message"
            Toast.makeText(this,"Successfully Decrypted", Toast.LENGTH_SHORT).show()
            binding.btDecrypt.text = "GO BACK"
            binding.btDecrypt.setOnClickListener{
                val intent = Intent(this,DashboardActivity::class.java)
                startActivity(intent)
            }
        }
        catch (e: GeneralSecurityException){
            Toast.makeText(this,"FAILED! TRY AGAIN",Toast.LENGTH_SHORT).show()
            binding.edDecKey.setText("")
        }
}