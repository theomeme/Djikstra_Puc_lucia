package br.com.theodoro.djikstra_puc_lucia

fun obterMenorCaminho(grafo: Array<IntArray>, origem: Int, destino: Int): List<Int> {
    val numVertices = grafo.size

    // Inicialização das estruturas de dados do vertice
    val distancias = IntArray(numVertices) { Int.MAX_VALUE }
    val visitados = BooleanArray(numVertices)
    val caminho = MutableList(numVertices) { -1 }

    distancias[origem] = 0

    // Execução do algoritmo de Dijkstra
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

    // Construção do caminho mínimo
    val menorCaminho = mutableListOf<Int>()
    var atual = destino
    while (atual != -1) {
        menorCaminho.add(0, atual)
        atual = caminho[atual]
    }

    return menorCaminho
}

fun encontrarVerticeComMenorDistancia(distancias: IntArray, visitados: BooleanArray): Int {
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