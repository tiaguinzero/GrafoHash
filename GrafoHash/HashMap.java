package GrafoHash;
import List.ListaEncadeadaSimplesDesordenada;

public class HashMap<K, V> {
    private class Elemento {
        private K chave;
        private V valor;

        Elemento(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public K getChave() {
            return chave;
        }

        public V getValor() {
            return valor;
        }

        public void setValor(V valor) {
            this.valor = valor;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Elemento other = (Elemento) obj;

            if (other.chave != this.chave || other.valor != this.valor)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int ret = 777;

            ret += 13 * ret + chave.hashCode();
            ret += 13 * ret + valor.hashCode();

            return ret < 0 ? -ret : ret;
        }

        @Override
        public String toString() {
            return "(" + chave + ", " + valor + ")";
        }
    }

    private ListaEncadeadaSimplesDesordenada<Elemento>[] vetor;
    private int qtdElems = 0;
    private int capacidadeInicial;
    private float txMinDesperdicio, txMaxDesperdicio;

    // Construtor principal
    public HashMap(int capacidadeInicial, float txMinDesperdicio, float txMaxDesperdicio) {
        this.capacidadeInicial = capacidadeInicial;
        this.txMinDesperdicio = txMinDesperdicio;
        this.txMaxDesperdicio = txMaxDesperdicio;
        this.vetor = new ListaEncadeadaSimplesDesordenada[capacidadeInicial];
    }

    // Construtor alternativo com taxas padrão
    public HashMap(int capacidadeInicial) {
        this(capacidadeInicial, 0.7f, 0.9f); // Valores padrão de 70% e 90%
    }

    private int calcularIndice(K chave) {
        int ret = chave.hashCode();
        if (ret < 0) {
            ret = -ret;
        }
        return ret % capacidadeInicial;
    }

    private void verificarTaxaEDimensionar() {
        float taxaOcupacao = (float) qtdElems / capacidadeInicial;

        if (taxaOcupacao > txMaxDesperdicio) {
            redimensionar(capacidadeInicial * 2); // Aumenta o tamanho
        } else if (taxaOcupacao < txMinDesperdicio && capacidadeInicial > 1) {
            int novaCapacidade = capacidadeInicial / 2;
            if (novaCapacidade < 1) {
                novaCapacidade = 1;
            }
            redimensionar(novaCapacidade); // Diminui o tamanho, mas não abaixo de 1
                                           // capacidadeInicial
        }
    }

    private void redimensionar(int novaCapacidade) {
        ListaEncadeadaSimplesDesordenada<Elemento>[] novaTabela = new ListaEncadeadaSimplesDesordenada[novaCapacidade];
        for (ListaEncadeadaSimplesDesordenada<Elemento> lista : vetor) {
            if (lista != null) {
                for (int i = 0; i < lista.getTamanho(); i++) {
                    try {
                        Elemento elemento = lista.get(i);
                        int hash = elemento.getChave().hashCode();
                        int novoIndice = hash < 0 ? -hash : hash % novaCapacidade;
                        if (novaTabela[novoIndice] == null) {
                            novaTabela[novoIndice] = new ListaEncadeadaSimplesDesordenada<>();
                        }
                        novaTabela[novoIndice].guardeNoFinal(elemento);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        vetor = novaTabela;
        capacidadeInicial = novaCapacidade;
    }

    public void guardeUmItem(K chave, V valor) throws Exception {
        if (chave == null) {
            throw new Exception("Chave não pode ser nula");
        }

        int indice = calcularIndice(chave);

        if (vetor[indice] == null) {
            vetor[indice] = new ListaEncadeadaSimplesDesordenada<>();
        }

        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice];
        Elemento novoElemento = new Elemento(chave, valor);

        for (int i = 0; i < lista.getTamanho(); i++) {
            Elemento atual = lista.get(i);
            if (atual.getChave().equals(chave)) {
                throw new Exception("Chave repetida! Não é possível adicionar um item com chave já existente.");
            }
        }

        lista.guardeNoFinal(novoElemento);
        qtdElems++;
        verificarTaxaEDimensionar();
    }

    public V recupereUmItem(K chave) throws Exception {
        if (chave == null) {
            throw new Exception("Chave não pode ser nula");
        }

        int indice = calcularIndice(chave);
        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice];

        if (lista != null) {
            for (int i = 0; i < lista.getTamanho(); i++) {
                Elemento atual = lista.get(i);
                if (atual.getChave().equals(chave)) {
                    return atual.getValor();
                }
            }
        }

        throw new Exception("Item não encontrado");
    }

    public void removaUmItem(K chave) throws Exception {
        int indice = calcularIndice(chave);
        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice];

        if (lista != null) {
            for (int i = 0; i < lista.getTamanho(); i++) {
                Elemento atual = lista.get(i);
                if (atual.getChave().equals(chave)) {
                    lista.remova(i);
                    qtdElems--;
                    verificarTaxaEDimensionar(); // Verifica e redimensiona se necessário
                    return;
                }
            }
        }

        throw new Exception("Item não encontrado");
    }
    
    
    public void altereUmItem(K chave, V novoValor) throws Exception {
        int indice = calcularIndice(chave);
        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice];

        if (lista != null) {
            for (int i = 0; i < lista.getTamanho(); i++) {
                Elemento atual = lista.get(i);
                if (atual.getChave().equals(chave)) {
                    atual.setValor(novoValor);
                    return;
                }
            }
        }

        throw new Exception("Item não encontrado");
    }

    public K recupereUmaChave(int index) throws Exception {
        if (index < 0 || index >= qtdElems) {
            throw new Exception("Índice fora dos limites");
        }

        int currentIndex = 0;
        for (ListaEncadeadaSimplesDesordenada<Elemento> lista : vetor) {
            if (lista != null) {
                for (int i = 0; i < lista.getTamanho(); i++) {
                    if (currentIndex == index) {
                        return lista.get(i).getChave();
                    }
                    currentIndex++;
                }
            }
        }

        throw new Exception("Item não encontrado");
    }

    public int getTamanho() {
        return qtdElems;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        HashMap<K, V> other = (HashMap<K, V>) obj;

        if (other.qtdElems != this.qtdElems || other.capacidadeInicial != this.capacidadeInicial
                || other.txMinDesperdicio != this.txMinDesperdicio || other.txMaxDesperdicio != this.txMaxDesperdicio)
            return false;

            try{
                for (int i = 0; i < capacidadeInicial; i++) {
                    ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[i];
                    ListaEncadeadaSimplesDesordenada<Elemento> otherLista = other.vetor[i];
        
                    if (lista == null && otherLista != null || lista != null && otherLista == null)
                        return false;
        
                    if (lista != null && otherLista != null) {
                        if (lista.getTamanho() != otherLista.getTamanho())
                            return false;
        
                        for (int j = 0; j < lista.getTamanho(); j++) {
                            if (!lista.get(j).equals(otherLista.get(j)))
                                return false;
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 777;

        ret += 13 * ret + qtdElems;
        ret += 13 * ret + Integer.valueOf(capacidadeInicial).hashCode();
        ret += 13 * ret + Float.valueOf(txMinDesperdicio).hashCode();
        ret += 13 * ret + Float.valueOf(txMaxDesperdicio).hashCode();

        try {
            for (int i = 0; i < capacidadeInicial; i++) {
                ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[i];
                if (lista != null) {
                    for (int j = 0; j < lista.getTamanho(); j++) {
                        ret += 13 * ret + lista.get(j).hashCode();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret < 0 ? -ret : ret;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (ListaEncadeadaSimplesDesordenada<Elemento> lista : vetor) {
            if (lista != null) {
                for (int i = 0; i < lista.getTamanho(); i++) {
                    try {
                        Elemento elemento = lista.get(i);
                        sb.append(elemento.toString()).append(", ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            // Criação do HashMap com capacidade inicial de 10 e taxas de desperdício padrão
            HashMap<String, Integer> mapa = new HashMap<>(10);

            // Adicionando alguns elementos
            mapa.guardeUmItem("Alice", 25);
            mapa.guardeUmItem("Bob", 30);
            mapa.guardeUmItem("Charlie", 35);

            // Tentando adicionar uma chave repetida
            try {
                mapa.guardeUmItem("Alice", 40); // Deve lançar exceção
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Exibindo o conteúdo do HashMap
            System.out.println("Conteúdo do HashMap: " + mapa.toString());


            HashMap<String, HashMap<String, Integer>> terminais = new HashMap<>(10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
