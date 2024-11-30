public class HashMap <K,V> implements Cloneable
{
    private class Elemento
    {
        private K chave;
        private V valor;

        // getters, setters e overrides
    }

    private ListaEncadeadaSimplesDesordenada<Elemento>[] vetor;
    private int qtdElems=0, qtdPosOcupadas=0;
    private int capacidadeInicial;
    private float txMinDesperdicio, txMaxDesperdicio;

    private void redimensioneSe (int novaCap)
    {
      //X     [] novo = new X      [novaCap];
        Object[] novo = new Object [novaCap];

        for (int i=0; i<this.qtd; i++)
            if (this.vetor[i]!=null)
                while (this.vetor[i].getTamanho()!=0)
                {
					Elemento prim = this.vetor[i].getPrimeiro();
					this.vetor[i].removaPrimeiro();
					int posicao = prim.getChave().hashCode()%novo.length;
					if (novo[posicao]==null) novo[posicao] = new ListaEncadeadaSimplesDesordenada <Elemento> ();
					novo[posicao].guardeNoInicio(prim);
				}

        this.vetor = novo;
        // System.gc();
    }
    
    public void guardeUmItem (K chave, V valor) throws Exception
    {
		if (this.qtdPosOcupadas>this.vetor.length-this.txMinDesperdicio*this.vetor.length)
		    this.redimensioneSe(2*this.vetor.length);
		    
		Elemento elem = new Elemento (chave,valor);
		int posicao = chave.hashCode()%this.vetor.length;
		if (this.vetor[posicao]==null) this.vetor[posicao] = new ListaEncadeadaSimplesDesordenada <Elemento> ();
		this.vetor[posicao].guardeNoInicio(elem);
	}

    public V recupereUmItem (K chave) throws Exception
    {...}

    public void removaUmItem (K chave) throws Exception
    {...}

    // construtores e overrides
}
