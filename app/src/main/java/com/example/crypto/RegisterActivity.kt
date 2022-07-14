package com.example.crypto
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crypto.databinding.RegisterActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: RegisterActivityBinding
lateinit var auth: FirebaseAuth
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_main)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btLoginRG.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.btRegisterRG.setOnClickListener{
            showProgress()
            checkCredentials()
        }
    }
    private fun checkCredentials() {
        val email = binding.edEmailRG
        val userName = binding.edUsernameRG
        val password = binding.edPasswordRG
        if (email.text.toString().isEmpty()) {
            email.error = "please enter email"
            email.requestFocus()
            return
        }
        if (password.text.toString().isEmpty()) {
            password.error = "Password cannot be empty"
            password.requestFocus()
            return
        }
        if (password.length() < 5) {
            password.error = "password too short!"
            password.requestFocus()
            return
        }
        if (userName.text.toString().isEmpty()) {
            userName.error = "Username cannot be empty"
        } else {
            auth.createUserWithEmailAndPassword(
                binding.edEmailRG.text.toString(),
                binding.edPasswordRG.text.toString()
            ).addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val dbRef = FirebaseDatabase.getInstance().getReference("users")
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val yourName = binding.edUsernameRG.text.toString()
                        val eMail = binding.edEmailRG.text.toString()
                        val myCustomUser = CustomUser(userId, yourName, eMail)
                        dbRef.child(userId).setValue(myCustomUser).addOnSuccessListener {
                            val intent = Intent(this, DashboardActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Custom user registered", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                    }
                    else{
                        Toast.makeText(this, "Custom reg error!", Toast.LENGTH_SHORT).show()
                        hideProgShow()
                    }
                }
                else{
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    hideProgShow()
                }
            }
                }
            }
private fun showProgress(){
    val progressShow = ProgressDialog(this)
    progressShow.setMessage("Creating an account for you...")
    progressShow.setCancelable(true)
    progressShow.show()
    return
}
    private fun hideProgShow() {
        val progressShow = ProgressDialog(this)
        progressShow.hide()
    }
}
