package Listas;

public class ListaEncadeadaSimplesDesordenada <X> implements Cloneable
{
	private class No implements Cloneable //classe interna da Lista
	{
	    private X  info; //informação do Tipo X(genérico; recebe qualquer tipo de parametro)
	    private No prox; //ponteiro o Proximo Nó(um nó com informação e ponteiro)
	    
	    public No (X i, No p) //poder criar um nó que pode ter uma informção e um ponteiro
	    {
	        this.info=i;
	        this.prox=p;
	    }
	    
	    public No (X i) //Um nó com informação mas sem o ponteiro(null), sendo o último nó da lista
	    {
	        this.info=i;
	        this.prox=null;
	    }
		//gettes e setters dos nós

	    public X getInfo ()
	    {
	        return this.info;
	    }
	    
	    public No getProx ()
	    {
	        return this.prox;
	    }
	    
	    public void setInfo (X i)
	    {
	        this.info=i;
	    }
	    
	    public void setProx (No p)
	    {
	        this.prox=p;
	    }
	    
	    @Override
        public String toString ()
        {
			return this.info.toString();
		}
		
		@Override
		public boolean equals (Object obj)
		{
			if (obj==this) return true;
			if (obj==null) return false;
			if (obj.getClass()!=this.getClass()) return false;
			
			if (!((No)obj).info.equals(this.info)) return false;
			
			return true;
		}
		
		@Override
		public int hashCode ()
		{
			int ret=1;
			
			ret=2*ret+this.info.hashCode();
		
		    if (ret<0) ret=-ret;
			
			return ret;
		}
		
		public No (No modelo) throws Exception
		{
			if (modelo==null) throw new Exception ("Modelo ausente");
			
			if (modelo.info instanceof Cloneable)
				this.info = new Clonador<X> ().clone (modelo.info);
			else
			    this.info = modelo.info;
		}
		
		public Object clone ()
		{
			No ret=null;
			
			try
			{
				ret = new No (this);
		    }
			catch (Exception erro)
			{} // sei que nao vai dar erro, pois o construtor de cópia só da erro ao receber parâmetro null, e o que estou passando como parâmetro para ele é o this, que NUNCA, NUNQUINHA MESMO, é null

			return ret;
		}
	}
	
	private No primeiro=null;
	
	public void guardeNoInicio (X i) throws Exception //recebe um parametro i do tipo X
	{
		if(i == null) throw new Exception("informação ausente");

		this.primeiro = new No(i, this.primeiro);
	}
	
	public void guardeNoFinal (X i) throws Exception
	{
		if(i == null) throw new Exception("inforamação ausente");

		if(this.primeiro == null) this.primeiro = new No (i); //se o primeiro No não existe(null), cria um novo Nó, pois o primeiro será o ultimo Nó também.

		No atual = this.primeiro;
		while(atual.getProx() != null)
			atual = atual.getProx();

		atual.setProx(new No(i));
	}
	
	public boolean tem (X i ) throws Exception
	{
		if(i == null) throw new Exception("informação ausente");

		No atual = this.primeiro;

		while(atual != null) //percorre a lista até o ultimo elemento
		{
			if(atual.getInfo().equals(i))
			return true; //se achar retonar true

			atual = atual.getProx();
		}
		return false; //se não retorna false
	}
	
	public X getPrimeiro() throws Exception //recebe i do tipo X e retorna o a informação do primeiro do tipo X
	{
		if(this.primeiro == null) throw new Exception("Lista Vazia");

		return this.primeiro.getInfo();
	}
	
	public X getUltimo() throws Exception
	{
		if(this.primeiro == null) throw new Exception("Lista vazia");
		
		if(this.primeiro.getProx() == null) return this.primeiro.getInfo();

		No atual = this.primeiro;
		while(atual.getProx() != null){
			atual = atual.getProx();
		}
		return atual.getInfo();
	}
	
	// posicao poderá ser 0, 1, etc
	public X get(int posicao) throws Exception
	{
		if(posicao < 0) throw new Exception("Posição invalida");

		if(posicao == 0) return this.primeiro.getInfo();

		int count = 1;
		No atual = this.primeiro;

		while(atual.getProx() != null)
		{
			if(posicao == count) return atual.getInfo();

			atual = atual.getProx();
			count ++;
		}

		throw new Exception("Essa posição é invalida" + posicao);
	}

	public void guardeEm (int posicao, X i) throws Exception
	{
		if (posicao<0) throw new Exception ("Posicao invalida");
		if (i==null) throw new Exception ("Falta o que inserir");
		
		X inf = new Clonador<X>().clone(i);
		
		if (posicao==0)
		{
			this.primeiro = new No (inf,this.primeiro);
			return;
		}
		
		No  atual   =this.primeiro;
		int posAtual=0;
		
		while (atual.getProx()!=null && posAtual < posicao-1)
		{
			atual=atual.getProx();
			posAtual++;
		}
		
		atual.setProx (new No (inf,atual.getProx()));
	}
	public void removaPrimeiro () throws Exception
	{
		if(this.primeiro == null) throw new Exception("Lista Vazia");

		//Não sei se pode remover o primeiro elemento mesmo a lista tendo apenas um elemento, deixando ela vazia.
		//Se sim, ficaria desta forma o método:
				if (this.primeiro.getProx() == null) {
					this.primeiro = null;
				} else {
					this.primeiro = this.primeiro.getProx();
				}
	}
	
	public void removaUltimo () throws Exception
	{
		if(this.primeiro == null) throw new Exception("Lista Vazia");

		if(this.primeiro.getProx()==null){
			this.primeiro = null;
			return;
		}

		No atual = this.primeiro;
		No predecessor = null;

		while (atual.getProx() != null) {
			predecessor = atual;
			atual = atual.getProx();
		}
		predecessor.setProx(null);
	}	

	public void remova(int posicao) throws Exception {
		if (posicao < 0) throw new Exception("Falta Posição");

		if (posicao == 0) {
			this.primeiro = this.primeiro.getProx();
			return;
		}

		No atual = this.primeiro;
		int contador = 1;
		No predecessor = null;

		while (atual != null) {
			if (contador == posicao) {
				predecessor.setProx(atual.getProx());
				return;
			}

			predecessor = atual;
			atual = atual.getProx();
			contador++;
		}

		throw new Exception("Posição não existente na Lista.");
	}

	public void inverta() throws Exception
	{
		if(this.primeiro == null) throw new Exception("Lista Vazia");

		No Atual = this.primeiro;
		No Proximo = Atual.getProx();

		while(Atual != null)
		{
			No Temp = Proximo.getProx();
			Proximo.setProx(Atual);
			Atual = Proximo;
			Proximo = Temp;
		}
	}

	public int getTamanho(){
		int count = 0;
		No atual = this.primeiro;

		while (atual != null) {
			count++;
			atual = atual.getProx();
		}

		return count;
	}


    @Override
    public String toString ()
    {
		if (this.primeiro==null) return "[]";
		
		String ret="[";
		
		ret+=this.primeiro.getInfo();
		
		No atual = this.primeiro.getProx();
		
		while (atual!=null)
		{
			ret+=", "+atual.getInfo();
			atual=atual.getProx();
		}
		
		return ret+"]";
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (obj==this) return true;
		if (obj==null) return false;
		if (obj.getClass()!=this.getClass()) return false;
		
		No atualDoThis = this.primeiro;
		No atualDoObj  = ((ListaEncadeadaSimplesDesordenada<X>)obj).primeiro;
		
		while (atualDoThis!=null && atualDoObj!=null)
		{
			if (!atualDoThis.getInfo().equals(atualDoObj.getInfo())) return false;
            atualDoThis=atualDoThis.getProx();
            atualDoObj =atualDoObj .getProx();
		}
		
		if (atualDoThis!=null || atualDoObj!=null) return false;
		
		return true;
	}
	
	@Override
	public int hashCode ()
	{
		int ret=1;
		
		No atual=this.primeiro;
		
		while (atual!=null)
		{
			ret = ret*2 + atual.getInfo().hashCode();
			atual=atual.getProx();
		}		
		
		if (ret<0) ret=-ret;
		return ret;
	}
	
	public ListaEncadeadaSimplesDesordenada (ListaEncadeadaSimplesDesordenada<X> modelo) throws Exception
	{
		if (modelo==null) throw new Exception ("Modelo ausente");
		
		if (modelo.primeiro==null)
		{
			this.primeiro=null;
			return;
		}
		
		this.primeiro = new No (modelo.primeiro.getInfo());
		
		No atualDoThis   = this.primeiro;
		No atualDoModelo = modelo.primeiro.getProx();
		
		while (atualDoModelo!=null)
		{
			atualDoThis.setProx (new No (atualDoModelo.getInfo()));
			
			atualDoThis  =atualDoThis  .getProx();
			atualDoModelo=atualDoModelo.getProx();
		}
	}

	public ListaEncadeadaSimplesDesordenada(X info){//CRIEI ESTE CONSTRUTOR APENAS PARA FAZER TESTES. NÃO TENHO CERTEZA SE ESTÁ CORRETO."ESTÁ FALTANDO EXCEPTION PARA INFO VAZIA".
		this.primeiro = new No(info);
	}

	public ListaEncadeadaSimplesDesordenada(){
		this.primeiro = null;
	}

	
	
	public Object clone ()
	{
		ListaEncadeadaSimplesDesordenada<X> ret=null;
		
		try
		{
			ret = new ListaEncadeadaSimplesDesordenada (this);
		}
		catch (Exception erro)
		{} // sei que nao vai dar erro, pois o construtor de cópia só da erro ao receber parâmetro null, e o que estou passando como parâmetro para ele é o this, que NUNCA, NUNQUINHA MESMO, é null

		return ret;
	}
}