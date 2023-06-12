package br.com.theodoro.djikstra_puc_lucia

import com.google.firebase.firestore.FirebaseFirestore
import br.com.theodoro.djikstra_puc_lucia.obterMenorCaminho


fun baixarMatrizFirebase() {
    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("predios")

    collectionRef.get()
        .addOnSuccessListener { querySnapshot ->
            val vertices = mutableListOf<VerticeClass>()

            for (document in querySnapshot.documents) {
                val indice = document.getLong("indice")?.toInt()
                val caminhos = document.get("caminhos") as List<Int>
                val tempo = document.get("tempo") as List<Int>

                val predios = VerticeClass(indice!!, caminhos, tempo)
                vertices.add(predios)
            }

            var adjacencyMatrix = Array(vertices.size) { IntArray(vertices.size) }

            for (vertice in vertices) {
                for (i in vertice.caminhos.indices) {
                    val dest = vertice.caminhos[i]
                    val w = vertice.tempo[i]
                    adjacencyMatrix[vertice.indice][dest] = w
                }
            }

            for (i in adjacencyMatrix.indices) {
                for (j in adjacencyMatrix[i].indices) {
                    print(adjacencyMatrix[i][j].toString())
                    print(" ")
                }
                println()
            }
            //Chamar isso na principal
            //Origem destino como input\
            // matriz visivel no main
            val (caminho, distancia) = obterMenorCaminho(adjacencyMatrix, 0, 35)
            println("Caminho completo: ${caminho.joinToString(" -> ")}")
            println("Distância do menor caminho: $distancia")



        }
        .addOnFailureListener { e ->
            println("Erro ao obter documentos da coleção 'vertices': $e")
        }
}
