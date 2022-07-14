package com.example.crypto
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crypto.databinding.DashboardActivityBinding
import com.example.crypto.databinding.RegisterActivityBinding
import com.scottyab.aescrypt.AESCrypt
private lateinit var binding: DashboardActivityBinding
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btEncrypt.setOnClickListener{
            encryption()
        }

    }
    private fun encryption() {
        val encrypted = AESCrypt.encrypt(
            binding.edEncryptKey.text.toString(),
            binding.edMessage.text.toString()
        )
       val intent = Intent(this@DashboardActivity,DecryptActivity::class.java)
        intent.putExtra("msg",encrypted.toString())
        startActivity(intent)
        Toast.makeText(this,"Successfully Encrypted",Toast.LENGTH_SHORT).show()
        binding.edEncryptKey.setText("")
        binding.edMessage.setText("")
    }
}