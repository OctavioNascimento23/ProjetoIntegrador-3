package com.example.projetointegrador3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.projetointegrador3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o Firestore
        firestore = FirebaseFirestore.getInstance()

        // Salvar dados no Firestore
        binding.saveButton.setOnClickListener {
            val data = hashMapOf("nome" to "Usuário Teste", "idade" to 25)
            firestore.collection("usuarios")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // Listar dados do Firestore
        binding.listButton.setOnClickListener {
            firestore.collection("usuarios")
                .get()
                .addOnSuccessListener { result ->
                    val stringBuilder = StringBuilder()
                    for (document in result) {
                        stringBuilder.append("ID: ${document.id}, Nome: ${document.getString("nome")}, Idade: ${document.getLong("idade")}\n")
                    }
                    binding.dataTextView.text = stringBuilder.toString()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao listar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}