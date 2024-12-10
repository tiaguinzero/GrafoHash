package Tree;

import java.lang.reflect.*;

public class ArvoreBinariaDeBusca <X extends Comparable<X>> implements Cloneable 
{
    private class No
    {
        private No esq;
        private X  info;
        private No dir;

        public No (No esq, X info, No dir)
        {
            this.esq =esq;
            this.info=info;
            this.dir =dir;
        }

        public No (X info) //construtor de folha, folha não tem filhos
        {
            this(null,info,null);
        }

        public No (No esq, X info)  //No com filho à esquerda
        {
            this(esq,info,null);
        }

        public No (X info, No dir) //No com filho à direita
        {
            this(null,info,dir);
        }

        public No getEsq ()
        {
            return this.esq;
        }

        public X getInfo ()
        {
            return this.info;
        }

        public No getDir ()
        {
            return this.dir;
        }

        public void setEsq (No esq)
        {
            this.esq=esq;
        }

        public void setInfo (X info)
        {
            this.info=info;
        }

        public void setDir (No dir)
        {
            this.dir=dir;
        }
        
        // métodos obrigatórios
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
            
            return ret;
        }

        @Override
        public No clone ()
        {
            No ret=null;

            if (this.esq!=null)
                ret=this.esq.clone();
            ret=new No (ret,this.info);
            if (this.dir!=null)
                ret=this.dir.clone();

            return ret;
        }
    }

    private No raiz;

    private X meuCloneDeX (X x)
    {
        X ret=null;

        try
        {
            Class<?> classe         = x.getClass();
            Class<?>[] tipoDosParms = null;
            Method metodo           = classe.getMethod("clone",tipoDosParms);
            Object[] parms          = null;
            ret                     = (X)metodo.invoke(x,parms);
        }
        catch(NoSuchMethodException erro)
        {}
        catch(IllegalAccessException erro)
        {}
        catch(InvocationTargetException erro)
        {}

        return ret;
    }
    
    public void inclua (X inf) throws Exception
    {
        if (inf==null) throw new Exception ("informacao ausente");

        X info;
        if (inf instanceof Cloneable)
            info = meuCloneDeX(inf);
        else
            info = inf;

        if (this.raiz==null)
        {
            this.raiz = new No (info);
            return;
        }

        No atual = this.raiz;

        for(;;) // forever
        {
            int comparacao = info.compareTo(atual.getInfo());
            if (comparacao==0) throw new Exception ("informacao repetida");

            if (comparacao<0) // deve-se inserir info para o lado esquerdo
               if (atual.getEsq()!=null) 
                   atual=atual.getEsq();
               else // achei onde inserir; eh para a esquerda do atual
               {
                   atual.setEsq (new No (info));
                   return;
               }
            else // deve-se inserir info para o lado direito
               if (atual.getDir()!=null) 
                   atual=atual.getDir();
               else // achei onde inserir; eh para a direito do atual
               {
                   atual.setDir (new No (info));
                   return;
               }
        }
    }

	public boolean tem (X info) throws Exception
	{
		if (info==null) throw new Exception ("informacao ausente");

        No atual=this.raiz;
        for(;;) // forever
        {
			if (atual==null) return false;
			
            int comparacao = info.compareTo(atual.getInfo());
            if (comparacao==0) return true;
            
            if (comparacao<0)
                atual=atual.getEsq();
            else // comparacao>0
                atual=atual.getDir();
        }
	}

    public X getMenor () throws Exception
    {
	    if (this.raiz==null) throw new Exception ("arvore vazia");
	    
        No atual=this.raiz;
        X ret=null;
        for(;;) // forever
        {
			if (atual.getEsq()==null)
			{
				if (atual.getInfo() instanceof Cloneable)
     				ret = (X)meuCloneDeX(atual.getInfo());
     			else
     			    ret = atual.getInfo();
     			
     			break;
			}
            else
                atual=atual.getEsq();
		}
		
		return ret;
    }

    public int getQtdDeNodos ()
    {
		return getQtdDeNodos (this.raiz);
	}

    private int getQtdDeNodos (No r)
    {
		if (r==null) return 0;
		return 1+getQtdDeNodos(r.getEsq())+getQtdDeNodos(r.getDir());
	}

    public void balanceieSe () throws Exception
    {
        balanceieSe(this.raiz);
    }
	
	private void balanceieSe (No r) throws Exception
	{
		if ( r == null ) return;

        int qtdDir = getQtdDeNodos(r.getDir());
        int qtdEsq = getQtdDeNodos(r.getEsq());

        while ( Math.abs(qtdDir - qtdEsq) > 1 ) {
            // enquanto a quantidade de nós a esquerda menos
            // a quantidade de nós a direita for maior 1,
            if (qtdEsq - qtdDir > 1) {
                X antigaRaiz = r.getInfo();
                No atual = r;

            // remova da esquerda a extrema direita, guardando
            // numa variável o valor ali presente; 
                atual = atual.getEsq();
                while (atual.getDir() != null){
                    atual = atual.getDir();
                } 

            // substitua por esse valor o valor presente na raiz, salvando-o
            // antes numa outra variavel;
                r.setInfo(atual.getInfo()); 
                remova(atual.getInfo());
                
            // insira na arvore o valor que estava presente na raiz
                inclua(antigaRaiz);
                qtdEsq--;
                qtdDir++;

            // OBS: CHAME 1 SÓ VEZ getQtdDeNodos PARA A ESQUERDA E
            //      PARA A DIREITA, ARMAZENANDO OS RESULTADOS EM
            //      VARIÁVEIS QUE VOCÊ ATUALIZA NO WHILE
            }
            
            // enquanto a quantidade de nós a direita menos
            // a quantidade de nós a esquerda for maior 1,
            if (qtdDir - qtdEsq > 1) {
                X antigaRaiz = r.getInfo();
                No atual = r;

            // remova da direita a extrema esquerda, guardando
            // numa variável o valor ali presente; 
                atual = atual.getDir();
                while (atual.getEsq() != null){
                    atual = atual.getEsq();
                } 
            
            // substitua por esse valor o valor presente na raiz, salvando-o
            // antes numa outra variavel; 
                r.setInfo(atual.getInfo()); 
                remova(atual.getInfo());
            
            // insira na arvore o valor que estava presente na raiz
            inclua(antigaRaiz);
            qtdEsq++;
            qtdDir--;

            // OBS: CHAME 1 SÓ VEZ getQtdDeNodos PARA A ESQUERDA E
            //      PARA A DIREITA, ARMAZENANDO OS RESULTADOS EM
            //      VARIÁVEIS QUE VOCÊ ATUALIZA NO WHILE
            }

		// faça recursão para a esquerda e para a direita
        balanceieSe(r.getDir());
        balanceieSe(r.getEsq());
        }
	}

    public X getMaior () throws Exception
    {
	    if (this.raiz==null) throw new Exception ("arvore vazia");
	    
        No atual=this.raiz;
        X ret=null;
        for(;;) // forever
        {
			if (atual.getDir()==null)
			{
				if (atual.getInfo() instanceof Cloneable)
     				ret = (X)meuCloneDeX(atual.getInfo());
     			else
     			    ret = atual.getInfo();
     			
     			break;
			}
            else
                atual=atual.getDir();
		}
		
		return ret;
    }
    
    public void remova (X info) throws Exception
    {
	    if(info == null){
            throw new Exception("Informação ausente");
        }
        if (this.raiz == null){
            throw new Exception("Nó nulo");
        }
        if (!tem(info)){ //seria melhor descobrir se tem na lógica de remover
            throw new Exception("Informação Inexistente");
        }

        No atual = this.raiz;
        No pai = null;
        boolean filhoEsquerdo = true;

        for (;;){
            int comparacao = info.compareTo(atual.getInfo());
            if (comparacao == 0){
                break;
            }
            pai = atual;
            if (comparacao < 0){
                atual = atual.getEsq();
                filhoEsquerdo = true;
            }
            else{
                atual = atual.getDir();
                filhoEsquerdo = false;
            }
        }

        // se a info for encontrada numa folha, deslique a folha da árvore,
        // fazendo o ponteiro que aponta para ela dentro do seu nó pai,
        // tornar-se null
        if ( atual.getEsq() == null && atual.getDir() == null){
            if (atual == this.raiz) {
                this.raiz = null;
            } else if (filhoEsquerdo){
                pai.setEsq(null);
            }
            else{
                pai.setDir(null);
            }
        }
        // se info for encontrada num nó N, que não é folha, sendo que N
        // só tem filho à esquerda, e sendo N filho esquerdo de um certo
        // pai P, faça o ponteiro esquerdo de P, passar a apontar para
        // esse filho que ha na esquerda de N
        else if ( atual.getDir() == null && filhoEsquerdo) {
            if (atual == this.raiz) {
                this.raiz = atual.getEsq();
            }
            else{
                pai.setEsq(atual.getEsq());
            }
        }

        // se info for encontrada num nó N, que não é folha, sendo que N
        // só tem filho à esquerda, e sendo N filho direito de um certo
        // pai P, faça o ponteiro direito de P, passar a apontar para
        // esse filho que ha na esquerda de N
        else if ( atual.getDir() == null && !filhoEsquerdo){
            if (atual == this.raiz) {
                this.raiz = atual.getEsq();
            }
            else {
                pai.setDir(atual.getEsq());
            }
        }

        // se info for encontrada num nó N, que não é folha, sendo que N
        // só tem filho à direita, e sendo N filho esquerdo de um certo
        // pai P, faça o ponteiro esquerdo de P, passar a apontar para
        // esse filho que ha na direita de N
        else if ( atual.getEsq() == null && filhoEsquerdo){
            if (atual == this.raiz) {
                this.raiz = atual.getDir();
            }
            else{
                pai.setEsq(atual.getDir());
            }
        }

        // se info for encontrada num nó N, que não é folha, sendo que N
        // só tem filho à direita, e sendo N filho direita de um certo
        // pai P, faça o ponteiro direito de P, passar a apontar para
        // esse filho que ha na direita de N
        else if (atual.getEsq() == null && !filhoEsquerdo){
            if (atual == this.raiz) {
                this.raiz = atual.getDir();
            }
            else{
                pai.setDir(atual.getDir());
            }
        }

        // se info for encontrada num nó N, que não é folha e tem 2 filhos,
        // encontre a informação info que existe à extrema esquerda da
        // subarvore direita de N ou à extrema direita da subarvore esquerda
        // de N; remova o nó que contém info e substitua dentro do nó N,
        // a informação que ali se encontra por info
        else{
            No sucessor = null;
            if (getQtdDeNodos(atual.getEsq()) > getQtdDeNodos(atual.getDir()) ) {
                pai = atual;
                sucessor = atual.getEsq();
                filhoEsquerdo = true;
                while (sucessor.getDir() != null){
                    pai = sucessor;
                    sucessor = sucessor.getDir();
                    filhoEsquerdo = false;
                }
            }
            else{
                pai = atual;
                sucessor = atual.getDir();
                filhoEsquerdo = false;
                while (sucessor.getEsq() != null){
                    pai = sucessor;
                    sucessor = sucessor.getEsq();
                    filhoEsquerdo = true;
                }
            }
            if (filhoEsquerdo){
                pai.setEsq(sucessor.getDir());
            }
            else{
                pai.setDir(sucessor.getEsq());
            }

            atual.setInfo(sucessor.getInfo());
        }
    }

    //1) Escreva uma função que verifica se duas árvores são uma o espelho da outra, retornando True ou False.
    public boolean isEspelho(ArvoreBinariaDeBusca <X> a, ArvoreBinariaDeBusca <X> b) {
        return isEspelho(a.raiz, b.raiz);
    }

    private boolean isEspelho(No a, No b)
    {
        if(a == null && b == null) return true;
        if(a == null || b == null) return false;

        if(!a.getInfo().equals(b.getInfo())) return false;
        if(!isEspelho(a.getEsq(), b.getDir())) return false;
        if(!isEspelho(a.getDir(), b.getEsq())) return false;
        return true;

    }

    //2) Escreva uma função que compare duas árvores binárias e determine se
    //elas são estruturalmente iguais (mesma forma, não necessariamente os 
    //mesmos valores), returnando True ou False.
    public boolean isIgual(ArvoreBinariaDeBusca <X> a, ArvoreBinariaDeBusca <X> b)
    {
        return isIgual(a.raiz, b.raiz);
    }
    
    private boolean isIgual(No a, No b)
    {
        if(a == null && b == null) return false;
        if(a == null || b == null) return true;

        if(!isEspelho(a.getEsq(), b.getEsq())) return false;
        if(!isEspelho(a.getDir(), b.getDir())) return false;
        return true;
    }

    //3) Escreva uma função que compare duas árvores binárias e determine
    //se elas são iguais (mesma forma e mesmos valores nos nodos), returnando
    //True ou False.
}

