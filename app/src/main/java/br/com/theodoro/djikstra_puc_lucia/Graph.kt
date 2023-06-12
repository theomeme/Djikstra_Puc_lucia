package br.com.theodoro.djikstra_puc_lucia

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

data class VerticeClass(val indice: Int, val caminhos: List<Int>, val tempo: List<Int>)
data class ResultadoMenorCaminho(val caminho: List<Int>, val distancia: Int)

class Graph {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val predios = listOf(
        "Auditório D. Gilberto",
        "Bancos",
        "H-15",
        "CT/BA",
        "Praça de Alimentação e Serviços - 1",
        "Biblioteca Unidade - 1",
        "Prédio Reitoria",
        "Central de Atendimento ao Aluno (CAA)",
        "H-9",
        "Predio Adm. - 1 ELC",
        "H-7",
        "H-5",
        "H-1",
        "H-3",
        "H-0",
        "Capela",
        "H-10 (Prédio em Manutenção)",
        "H-12",
        "H-14",
        "CT/C1",
        "CT/C2",
        "B2",
        "B1",
        "H-8",
        "H-6",
        "Predio Adm. - 2 ECON",
        "H-4",
        "H-2",
        "Biblioteca Unidade - 2 Bloco B",
        "Bloco C",
        "Bloco A",
        "Bloco D",
        "Bloco G",
        "Complexo Esportivo - Bloco H",
        "Prédio Adm. - HJS - Bloco E",
        "Quadras Cobertas - Bloco F",
    )


    fun baixarMatrizFirebase(from: Int, to: Int): Task<List<Any>> {
        val collectionRef = db.collection("predios")

        return collectionRef.get()
            .continueWith { querySnapshot ->
                val vertices = mutableListOf<VerticeClass>()

                for (document in querySnapshot.result!!.documents) {
                    val indice = document.getLong("indice")?.toInt()
                    val caminhos = document.get("caminhos") as List<Int>
                    val tempo = document.get("tempo") as List<Int>

                    val predios = VerticeClass(indice!!, caminhos, tempo)
                    vertices.add(predios)
                }

                val adjacencyMatrix = createAdjacencyMatrix(vertices)

                val (caminho, distancia) = obterMenorCaminho(adjacencyMatrix, from, to)

                val textoCaminho = caminho.map {
                    predios[it]
                }.toList().joinToString(" -> ")

                listOf(textoCaminho, distancia)
            }
    }

    private fun createAdjacencyMatrix(vertices: List<VerticeClass>): Array<IntArray> {
        val numVertices = vertices.size
        val adjacencyMatrix = Array(numVertices) { IntArray(numVertices) }

        for (vertice in vertices) {
            for (i in vertice.caminhos.indices) {
                val dest = vertice.caminhos[i]
                val w = vertice.tempo[i]
                adjacencyMatrix[vertice.indice][dest] = w
            }
        }

        return adjacencyMatrix
    }

    private fun obterMenorCaminho(
        grafo: Array<IntArray>,
        origem: Int,
        destino: Int
    ): ResultadoMenorCaminho {
        val numVertices = grafo.size

        val distancias = IntArray(numVertices) { Int.MAX_VALUE }
        val visitados = BooleanArray(numVertices)
        val caminho = MutableList(numVertices) { -1 }

        distancias[origem] = 0

        for (i in 0 until numVertices - 1) {
            val u = encontrarVerticeComMenorDistancia(distancias, visitados)
            visitados[u] = true

            for (v in 0 until numVertices) {
                if (!visitados[v] && grafo[u][v] != 0 && distancias[u] != Int.MAX_VALUE && distancias[u] + grafo[u][v] < distancias[v]) {
                    distancias[v] = distancias[u] + grafo[u][v]
                    caminho[v] = u
                }
            }
        }

        val menorCaminho = mutableListOf<Int>()
        var atual = destino
        do {
            menorCaminho.add(0, atual)
            atual = caminho[atual]
        } while (atual != -1)

        return ResultadoMenorCaminho(menorCaminho, distancias[destino])
    }

    private fun encontrarVerticeComMenorDistancia(
        distancias: IntArray,
        visitados: BooleanArray
    ): Int {
        var menorDistancia = Int.MAX_VALUE
        var verticeMenorDistancia = -1

        for (v in distancias.indices) {
            if (!visitados[v] && distancias[v] <= menorDistancia) {
                menorDistancia = distancias[v]
                verticeMenorDistancia = v
            }
        }

        return verticeMenorDistancia
    }
}