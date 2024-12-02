package List;

public class ListaEncadeadaSimplesDesordenada <X> implements Cloneable
{
	private class No implements Cloneable
	{
	    private X  info;
	    private No prox;
	    
	    public No (X i, No p)
	    {
	        this.info=i;
	        this.prox=p;
	    }
	    
	    public No (X i)
	    {
	        this.info=i;
	        this.prox=null;
	    }
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
	
	public void guardeNoInicio (X i) throws Exception
	{
		if (i==null) throw new Exception ("Informação ausente");
		
		this.primeiro = new No (i,this.primeiro);
	}
	
	public void guardeNoFinal (X i) throws Exception
	{
		if (i==null) throw new Exception ("Informação ausente");

        if (this.primeiro==null) this.primeiro = new No (i);
        
        No currentNo=this.primeiro;
        
        while (currentNo.getProx()!=null)
            currentNo=currentNo.getProx();
        
        currentNo.setProx(new No (i));
	}
	
	public boolean tem (X i)
	{
		No currentNo=this.primeiro;
		
		while (currentNo!=null)
		{
			if (currentNo.getInfo().equals(i)) return true;
			
			currentNo=currentNo.getProx();
		}
		
		return false;
	}
	
	public X getPrimeiro () throws Exception
	{
		if(this.primeiro == null) throw new Exception("Lista Vazia");

		return this.primeiro.getInfo();
	}
	
	public X getUltimo () throws Exception
	{
		if(this.primeiro == null) throw new Exception("Lista Vazia");

		if(this.primeiro.getProx() == null) return this.primeiro.info;

		No currentNo = this.primeiro;
		while (currentNo.getProx()!=null) {
			currentNo = currentNo.getProx();
		}
		return currentNo.getInfo();
	}
	
	// posicao poderá ser 0, 1, etc
	public X get (int posicao) throws Exception
	{


		if(posicao<0) throw new Exception("Posição Inválida.");

		if(posicao == 0) return this.primeiro.getInfo();

		int count = 1;
		No currentNo = this.primeiro;

		while (currentNo.getProx() != null) {

			if(count == posicao) return currentNo.getInfo();

			currentNo = currentNo.getProx();

			count++;
		}

		throw new Exception("Posição Inválida "+ posicao);

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
		
		while (atual.getProx()!=null && posAtual<posicao-1)
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

		No currentNo = this.primeiro;
		No predecessor = null;

		while (currentNo.getProx() != null) {
			predecessor = currentNo;
			currentNo = currentNo.getProx();
		}
		predecessor.setProx(null);
	}	

	public void remova(int posicao) throws Exception {
		if (posicao < 0) throw new Exception("Falta Posição");

		if (posicao == 0) {
			this.primeiro = this.primeiro.getProx();
			return;
		}

		No currentNo = this.primeiro;
		int contador = 1;
		No predecessor = null;

		while (currentNo != null) {
			if (contador == posicao) {
				predecessor.setProx(currentNo.getProx());
				return;
			}

			predecessor = currentNo;
			currentNo = currentNo.getProx();
			contador++;
		}

		throw new Exception("Posição não existente na Lista.");
	}

	public int getTamanho(){
		int count = 0;
		No currentNo = this.primeiro;

		while (currentNo != null) {
			count++;
			currentNo = currentNo.getProx();
		}

		return count;
	}



    @Override
    public String toString ()
    {
		if (this.primeiro==null) return "[]";
		
		String ret="[";
		
		ret+=this.primeiro.getInfo();
		
		No currentNo = this.primeiro.getProx();
		
		while (currentNo!=null)
		{
			ret+=", "+currentNo.getInfo();
			currentNo=currentNo.getProx();
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
		
		No currentNo=this.primeiro;
		
		while (currentNo!=null)
		{
			ret = ret*2 + currentNo.getInfo().hashCode();
			currentNo=currentNo.getProx();
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