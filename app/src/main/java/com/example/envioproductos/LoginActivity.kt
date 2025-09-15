package com.example.envioproductos

// Importaciones necesarias para trabajar con Activities, Intents, Logs, Firebase y Google Sign-In
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.envioproductos.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

// Clase principal LoginActivity que extiende AppCompatActivity
class LoginActivity : AppCompatActivity() {

    // Binding: permite acceder a los elementos del layout sin usar findViewById
    private lateinit var binding: ActivityLoginBinding

    // Cliente de inicio de sesión con Google
    private lateinit var googleSignInClient: GoogleSignInClient
    // Instancia de Firebase Authentication
    private lateinit var auth: FirebaseAuth

    companion object {
        // Código de solicitud único para identificar el resultado del login con Google
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos el binding para acceder a la vista
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configuración de Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // El ID del cliente obtenido desde la consola de Google Cloud
            .requestIdToken("112477068640-qutdnqn1rqfspkbhfmeoaovhof9dct2i.apps.googleusercontent.com")
            // Se solicita el correo del usuario
            .requestEmail()
            .build()

        // Inicializamos el cliente de Google con la configuración creada
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Asignamos acción al botón de login con Google
        binding.loginGoogleButton.setOnClickListener {
            signIn() // Llamamos a la función que lanza el flujo de inicio de sesión
        }
    }

    // Función para iniciar sesión con Google
    private fun signIn() {
        // Obtenemos el intent del cliente de Google
        val signInIntent = googleSignInClient.signInIntent
        // Lanzamos la actividad esperando un resultado
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Método que recibe el resultado de startActivityForResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Verificamos si el resultado corresponde al inicio de sesión de Google
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // ⚠️ Aquí se lanza MainActivity incluso antes de autenticar con Firebase
                // Lo recomendable sería hacerlo después de validar en Firebase
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()

                // Obtenemos la cuenta de Google
                val account = task.getResult(ApiException::class.java)!!
                // Autenticamos en Firebase usando el ID Token
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Si ocurre un error, lo mostramos en logs
                Log.w("LoginActivity", "Intento de Login fallido", e)
            }
        }
    }

    // Función que autentica en Firebase usando el token de Google
    private fun firebaseAuthWithGoogle(idToken: String) {
        // Creamos una credencial con el token
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        // Iniciamos sesión en Firebase con la credencial
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // ✅ Login exitoso
                    val user = auth.currentUser
                    Log.d("LoginActivity", "Obtuvo credenciales de acceso: ${user?.email}")

                    // Pasamos a la siguiente actividad (MainActivity)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // ❌ Login fallido, mostramos el error en logs
                    Log.w("LoginActivity", "Intento de Login fallido", task.exception)
                }
            }
    }
}
