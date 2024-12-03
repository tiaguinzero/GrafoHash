package Grafos.NewCode;

public class Grafo<TpIdVrt extends Comparable<TpIdVrt>, TpInfVrt, TpIdArt extends Comparable<TpIdArt>, TpInfArt> {
    // Classe interna Vertice
    private class Vertice implements Identificavel<TpIdVrt> {
        private TpIdVrt id;
        private TpInfVrt info;

        public Vertice(TpIdVrt id, TpInfVrt info) throws Exception {
            if (id == null)
                throw new Exception("Vértice sem id");
            this.id = id;
            this.info = info;
        }

        public TpIdVrt getId() {
            return this.id;
        }

        public TpInfVrt getInfo() {
            return this.info;
        }

        public void setInfo(TpInfVrt info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "Vertice{" + "id=" + id + ", info=" + info + '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Vertice vertice = (Vertice) obj;
            return id.equals(vertice.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    // Classe interna Aresta
    private class Aresta implements Identificavel<TpIdArt> {
        private TpIdArt id;
        private TpInfArt info;
        private TpIdVrt origem;
        private TpIdVrt destino;

        public Aresta(TpIdArt id, TpInfArt info, TpIdVrt origem, TpIdVrt destino) throws Exception {
            if (id == null)
                throw new Exception("Aresta sem id");
            if (origem == null)
                throw new Exception("Aresta sem origem");
            if (!vertices.tem(origem))
                throw new Exception("Origem inexistente");
            if (destino == null)
                throw new Exception("Aresta sem destino");
            if (!vertices.tem(destino))
                throw new Exception("Destino inexistente");

            this.id = id;
            this.info = info;
            this.origem = origem;
            this.destino = destino;
        }

        public TpIdArt getId() {
            return this.id;
        }

        public TpInfArt getInfo() {
            return this.info;
        }

        public TpIdVrt getOrigem() {
            return this.origem;
        }

        public TpIdVrt getDestino() {
            return this.destino;
        }

        @Override
        public String toString() {
            return "Aresta{" + "id=" + id + ", info=" + info + ", origem=" + origem + ", destino=" + destino + '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Aresta aresta = (Aresta) obj;
            return id.equals(aresta.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    // Listas de vértices e arestas
    private ListaEncadeadaSimplesDesordenada<TpIdVrt, Vertice> vertices;
    private ListaEncadeadaSimplesDesordenada<TpIdArt, Aresta> arestas;

    public Grafo() {
        this.vertices = new ListaEncadeadaSimplesDesordenada<>();
        this.arestas = new ListaEncadeadaSimplesDesordenada<>();
    }

    public void adicioneVertice(TpIdVrt id, TpInfVrt info) throws Exception {
        if (vertices.tem(id))
            throw new Exception("Vértice já existente");
        this.vertices.guardeNoInicio(new Vertice(id, info));
    }

    public String[] obterVizinhos(TpInfVrt info) throws Exception{
        int getTamanho = this.arestas.getTamanho();
        String[] vizinhos = new String[getTamanho];
        int j = 0;
        for (int i = 0; i < getTamanho; i++) {
            Aresta aresta = this.arestas.getX(i);
            Vertice vertice = this.vertices.get(aresta.getOrigem());
            if (aresta.getOrigem().equals(vertice.getId()) && vertice.getInfo().equals(info)) {
                Vertice verticeDestino = this.vertices.get(aresta.getDestino());
                vizinhos[j] = verticeDestino.getInfo().toString();
                j++;
            }
        }
        return vizinhos;
    }

    public void adicioneAresta(TpIdArt id, TpInfArt info, TpIdVrt origem, TpIdVrt destino) throws Exception {
        if (arestas.tem(id))
            throw new Exception("Aresta já existente");
        this.arestas.guardeNoInicio(new Aresta(id, info, origem, destino));
    }

    public void removerAresta(TpInfVrt origem, TpInfVrt destino) throws Exception {
        int getTamanho = this.arestas.getTamanho();
        for (int i = 0; i < getTamanho; i++) {
            Aresta aresta = this.arestas.getX(i);
            if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
                this.arestas.remova(aresta.getId());
                return;
            }
        }
    }

    public TpInfArt obterPeso(TpIdVrt origem, TpIdVrt destino) throws Exception {
        int getTamanho = this.arestas.getTamanho();
        for (int i = 0; i < getTamanho; i++) {
            Aresta aresta = this.arestas.getX(i);
            Vertice verticeOrigem = this.vertices.get(origem);
            Vertice verticeDestino = this.vertices.get(destino);
            if (aresta.getOrigem().equals(verticeOrigem.getId()) && aresta.getDestino().equals(
                    verticeDestino.getId())) {
                return aresta.getInfo();
            }
        }
        throw new Exception("Aresta inexistente");
    }

    public boolean existeAresta(TpIdArt id) {
        return this.arestas.tem(id);
    }

    public boolean existeAresta(TpInfArt info) throws Exception{
        int getTamanho = this.arestas.getTamanho();
        for (int i = 0; i < getTamanho; i++) {
            Aresta aresta = this.arestas.getX(i);
            if (aresta.getInfo().equals(info)) {
                return true;
            }
        }
        return false;
    }


    public boolean existeAresta(TpInfVrt origem , TpInfVrt destino) {
        int getTamanho = this.arestas.getTamanho();
        for (int i = 0; i < getTamanho; i++) {
            Aresta aresta = this.arestas.getX(i);
            if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
                return true;
            }
        }
        return false;
    }

    public int grauDoVertice(TpIdVrt idVertice) throws Exception {
        if (!vertices.tem(idVertice))
            throw new Exception("Vértice inexistente");

        int grau = 0;
        int tamanho = arestas.getTamanho();

        for (int i = 0; i < tamanho; i++) {
            Aresta aresta = arestas.getX(i);
            if (aresta.getOrigem().equals(idVertice) || aresta.getDestino().equals(idVertice)) {
                grau++;
            }
        }
        return grau;
    }

    public boolean éOrientado() {
        int tamanho = arestas.getTamanho();
        for (int i = 0; i < tamanho; i++) {
            Aresta aresta = arestas.getX(i);
            for (int j = 0; j < tamanho; j++) {
                Aresta outraAresta = arestas.getX(j);
                if (aresta.getOrigem().equals(outraAresta.getDestino())
                        && aresta.getDestino().equals(outraAresta.getOrigem())) {
                    return false; // Encontrou uma aresta oposta.
                }
            }
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Grafo{" + "vertices=" + vertices + ", arestas=" + arestas + '}';
    }

    public static void main(String[] args) {
        Grafo<Integer, String, Integer, String> grafo = new Grafo<>();
        try {
            grafo.adicioneVertice(1, "A");
            grafo.adicioneVertice(2, "B");
            grafo.adicioneVertice(3, "C");
            grafo.adicioneVertice(4, "D");
            grafo.adicioneAresta(3, "A", 1, 2);
            grafo.adicioneAresta(2, "B", 2, 3);
            grafo.adicioneAresta(1, "C", 3, 1);
            grafo.adicioneAresta(4, "C", 3, 2);
            grafo.adicioneAresta(5, "C", 3, 4);
            
            String[] lista = grafo.obterVizinhos("C");
            for (String s : lista) {
                if (s == null) {
                    continue;
                }
                System.out.println( s);
            }

            System.out.println("ObterPeso: ");
            System.out.println(grafo.obterPeso(1, 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
