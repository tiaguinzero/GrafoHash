package GrafoHash;
import Listas.*;

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

        public void setChave(K chave) {
            this.chave = chave;
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

    private ListaEncadeadaSimplesDesordenada<Elemento>[] vetor; // vetor tem elemento, elemento tem chave e valor
    private int qtdElems = 0; //conta a qtd de elementos no hashMap
    private int numBuckets; //número de buckets
    private float txMinDesperdicio, txMaxDesperdicio; //controla o redimensionamento

    // Construtor principal
    public HashMap(int numBuckets, float txMinDesperdicio, float txMaxDesperdicio) {
        this.numBuckets = numBuckets;
        this.txMinDesperdicio = txMinDesperdicio;
        this.txMaxDesperdicio = txMaxDesperdicio;
        this.vetor = new ListaEncadeadaSimplesDesordenada[numBuckets];
    }

    // Construtor alternativo com taxas padrão
    public HashMap(int numBuckets) { 
        this(numBuckets, 0.7f, 0.9f); // Valores padrão de 70% e 90%
    }

    private int calcularIndice(K chave) { //indicie da chave
        int ret = chave.hashCode(); //usa o valor da chave para para criar um hashcode
        if (ret < 0) { //se o hash code for negativo
            ret = -ret; //tranforma em positivo
        }
        return ret % numBuckets; //modulo entre hash da chave e numBuckets para saber em qual bucket a chave será armazenada.
    }

    private void verificarTaxaEDimensionar() {
        float taxaOcupacao = (float) qtdElems / numBuckets; //ocupação = numElemento/numBuckets

        if (taxaOcupacao > txMaxDesperdicio) { //se taxa de ocupação for maior q 90%
            redimensionar(numBuckets * 2); // Dobra a capacidade
        } else if (taxaOcupacao < txMinDesperdicio && numBuckets > 1) { //numBuckets precisa tem pelos menos 1 bucket
            int novaCapacidade = numBuckets / 2; //diminui se a taxaOcupação for menor que 70%
            if (novaCapacidade < 1) { //se não tem bucket
                novaCapacidade = 1; //o bucket será igual a 1
            }
            redimensionar(novaCapacidade); // Diminui o tamanho, mas não abaixo de 1
                                           // numBuckets
        }
    }

    private void redimensionar(int novaCapacidade) { //redimencionar é duplicar ou dividir o NUMERO DE BUCKETS
        ListaEncadeadaSimplesDesordenada<Elemento>[] novaTabela = new ListaEncadeadaSimplesDesordenada[novaCapacidade]; //crie uma nova tabela com a capacidade nova
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
        numBuckets = novaCapacidade;
    }

    public void guardeUmItem(K chave, V valor) throws Exception {
        if (chave == null) { //verifica se a chave foi passada pelo o úsuario
            throw new Exception("Chave não pode ser nula");
        }

        int indice = calcularIndice(chave); //calcula qual indicie do bucket para armazenar a chave

        if (vetor[indice] == null) { //se não exite um bucket com esse indicie 
            vetor[indice] = new ListaEncadeadaSimplesDesordenada<>(); //cria um novo bucket com esse indicie
        }

        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice]; // tranforma o indicie em uma ListaEncadeada/bucket
        Elemento novoElemento = new Elemento(chave, valor); //cria o elemento a partir da chave e valor do usuario.

        for (int i = 0; i < lista.getTamanho(); i++) {
            Elemento atual = lista.get(i); //percorre a lista
            if (atual.getChave().equals(chave)) { //verefica se já existe uma chave igual 
                throw new Exception("Chave repetida! Não é possível adicionar um item com chave já existente.");
            }
        }

        lista.guardeNoFinal(novoElemento); //se não existir guarda no final da lista
        qtdElems++;
        verificarTaxaEDimensionar(); // após adicionar verifica a taxa para ver se falta ou soubrou espeça para ser redimencionada
    }

    public V recupereUmItem(K chave) throws Exception { //fala se existe algum valor a partir da chave.
        if (chave == null) {
            throw new Exception("Chave não pode ser nula"); // se a chave for nula, lança exceção
        }

        int indice = calcularIndice(chave); //qual indicie o bucket estará
        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice]; //cria um bucket a partir da posição do indicie

        if (lista != null) { //percorre as listas q não são nulas
                for (int i = 0; i < lista.getTamanho(); i++) { //verifica se os valores da lista
                    Elemento atual = lista.get(i);
                    if (atual.getChave().equals(chave)) { //são iguais ou não 
                        return atual.getValor(); //se for igual printa o valor para o usuario
                }
            }
        }

        throw new Exception("Item não encontrado"); // se não, lança exceção
    }

    public void removaUmItem(K chave) throws Exception { //remova um valor a partir da chave
        int indice = calcularIndice(chave); //calcula em qual bucket a chave está
        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice];

        if (lista != null) { //percorre as listas q não nullas
            for (int i = 0; i < lista.getTamanho(); i++) { //percorre a lista 
                Elemento atual = lista.get(i); //coloca o valor da lista em um ELemento atual
                if (atual.getChave().equals(chave)) { //compara a chave com o elemente atual da lista
                    lista.remova(i); //se for igual, remove a chave
                    qtdElems--; //diminui o contade de elemento do hashMap
                    verificarTaxaEDimensionar(); // Verifica e redimensiona se necessário
                    return;
                }
            }
        }

        throw new Exception("Item não encontrado"); //se não encontrar lança exceção
    }
    
    
    //---------------------------------------------------------------------------------------------------------------------------------


    public void altereUmItem(K chave, V novoValor) throws Exception { //método de pull em algum valor a partir da chave
        int indice = calcularIndice(chave); //calcula em qual bucket a chave está
        ListaEncadeadaSimplesDesordenada<Elemento> lista = vetor[indice]; //acessa a listaEncadeada a partir do indicie

        if (lista != null) { //percorre a lista se não for nula
            for (int i = 0; i < lista.getTamanho(); i++) { //percorre a lista
                Elemento atual = lista.get(i);
                if (atual.getChave().equals(chave)) { //compara os valores para ver se igual a chave
                    atual.setValor(novoValor); //se for igual muda para o novoValor
                    return;
                }
            }
        }

        throw new Exception("Item não encontrado"); //se não encontrar lança exceção
    }

    public K recupereUmaChave(int index) throws Exception {
        if (index < 0 || index >= qtdElems) { //verifica se o existe, nos padrões
            throw new Exception("Índice fora dos limites");//exceção
        }

        int currentIndex = 0; //seta indexAtual=0
        for (ListaEncadeadaSimplesDesordenada<Elemento> lista : vetor) { //percorre os buckets no vetor
            if (lista != null) {  //se lista não for nula
                for (int i = 0; i < lista.getTamanho(); i++) { // percorre seus valores da lista
                    if (currentIndex == index) { //se o IndexAtal for igual ao do user
                        return lista.get(i).getChave(); //retorna a chave encontrada
                    }
                    currentIndex++; //aumenta o contador de index
                }
            }
        }

        throw new Exception("Item não encontrado"); // se n achar, int's over.
    }

    public int getTamanho() { // retorna qtd de elemento no hash
        return qtdElems; // só dar return no atributo qtdELementos.
    }


//===============================Métodos Obrigatórios===============================


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        HashMap<K, V> other = (HashMap<K, V>) obj;

        if (other.qtdElems != this.qtdElems || other.numBuckets != this.numBuckets
                || other.txMinDesperdicio != this.txMinDesperdicio || other.txMaxDesperdicio != this.txMaxDesperdicio)
            return false;

            try{
                for (int i = 0; i < numBuckets; i++) {
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
        ret += 13 * ret + Integer.valueOf(numBuckets).hashCode();
        ret += 13 * ret + Float.valueOf(txMinDesperdicio).hashCode();
        ret += 13 * ret + Float.valueOf(txMaxDesperdicio).hashCode();

        try {
            for (int i = 0; i < numBuckets; i++) {
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


//==========================================Main===========================================


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
