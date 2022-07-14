package com.example.crypto
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crypto.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityMainBinding
private lateinit var prg: ProgressDialog
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        prg = ProgressDialog(this)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//call buttons functions
        binding.btLogin.setOnClickListener {
            authenticate()

        }
        binding.btRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showProg() {

        prg.setMessage("HOLD ON A LITTLE...")
        prg.setCancelable(true)
        prg.show()
    }

    private fun hideProg() {
        prg.hide()
    }

    private fun authenticate() {
        showProg()
        auth.signInWithEmailAndPassword(
            binding.edEmail.text.toString(),
            binding.edPassword.text.toString()
        )
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show()
                    finish()

                } else {

                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
                hideProg()
            }
        hideProg()
    }

}