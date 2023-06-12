package br.com.theodoro.djikstra_puc_lucia

import com.google.firebase.firestore.FirebaseFirestore


fun baixarMatrizFirebase(onMatrizDownloaded: (Array<IntArray>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("vertices")

    collectionRef.get()
        .addOnSuccessListener { querySnapshot ->
            val vertices = mutableListOf<VerticeClass>()

            for (document in querySnapshot.documents) {
                val indice = document.getLong("indice")?.toInt()
                val caminhos = document.get("caminhos") as List<Int>
                val tempo = document.get("tempo") as List<Int>

                val vertice = VerticeClass(indice!!, caminhos, tempo)
                vertices.add(vertice)
            }

            val adjacencyMatrix = Array(vertices.size) { IntArray(vertices.size) }

            for (vertice in vertices) {
                for (i in vertice.caminhos.indices) {
                    val dest = vertice.caminhos[i]
                    val w = vertice.tempo[i]
                    adjacencyMatrix[vertice.indice][dest] = w
                }
            }

            onMatrizDownloaded(adjacencyMatrix)
        }
        .addOnFailureListener { e ->
            println("Erro ao obter documentos da coleção 'vertices': $e")
        }
}
