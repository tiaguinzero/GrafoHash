package GrafoHash;
import List.ListaEncadeadaSimplesDesordenada2;

public class Grafo <TpIdVrt extends Comparable<TpIdVrt>,
                    TpInfVrt,
                    TpIdArt extends Comparable<TpIdArt>,
                    TpInfArt>
{
    private class Vertice implements Identificavel <TpIdVrt>, Cloneable
    {
        private TpIdVrt  id;
        private TpInfVrt info;
        
        public Vertice (TpIdVrt id, TpInfVrt inf) throws Exception
        {
            if (id==null) throw new Exception ("Vertice sem id");
            
            this.id     =id;
            this.info   =inf;
        }

        // getters, setters e overrides

        public TpIdVrt getIdVert(){
            return this.id;
        }

        public TpIdVrt getId(){
            return this.id;
        }

        public TpInfVrt getInfoVert(){
            return this.info;
        }

        public void setInfoVert(TpInfVrt inf){
            this.info = inf;
        }

        public void setIdVert(TpIdVrt id){
            this.id = id;
        }

        @Override
        public String toString()
        {
            return this.info.toString();
        }

        @Override
        public boolean equals (Object obj)
        {
            if (obj == this) return true;
            if (obj == null) return false;
            if (obj.getClass()!=this.getClass()) return false;
            
            Vertice outro = (Vertice) obj;
            return this.id.equals(outro.id);
        }

        @Override
		public int hashCode ()
		{
			int ret=1;
			
			ret=2*ret+this.info.hashCode();
		
		    if (ret<0) ret=-ret;
			
			return ret;
		}
    }
    
    private class Aresta implements Identificavel <TpIdArt>, Cloneable
    {
        private TpIdArt    id;
        private TpInfArt   info;
        private TpIdVrt origem;
        private TpIdVrt destino;
        
        public Aresta (TpIdArt id, TpInfArt inf, TpIdVrt org, TpIdVrt dst) throws Exception
        {
            if (id==null) throw new Exception ("Aresta sem id");
            if (org==null) throw new Exception ("Aresta sem origem");
            if (!vertices.tem(org)) throw new Exception ("Aresta com origem inexistente");
            if (dst==null) throw new Exception ("Aresta sem destino");
            if (!vertices.tem(org)) throw new Exception ("Aresta com destino inexistente");
            
            this.id     =id;
            this.info   =inf;
            this.origem =org;
            this.destino=dst;
        }
        
        // getters, setters e overrides

        public TpIdArt getId(){
            return this.id;
        }

        public TpIdArt getIdArt(){
            return this.id;
        }

        public TpInfArt getInfoArt(){
            return this.info;
        }

        public TpIdVrt getOrigem(){
            return this.origem;
        }

        public TpIdVrt getDestino(){
            return this.destino;
        }

        public void setIdArt(TpIdArt id){
            this.id = id;
        } 

        public void setInfoArt(TpInfArt inf){
            this.info = inf;
        }

        public void setOrigem(TpIdVrt org){
            this.origem = org;
        }

        public void setDestino(TpIdVrt dst){
            this.destino = dst;
        } 

        @Override
        public String toString()
        {
            return this.info.toString();
        }

        @Override
        public boolean equals (Object obj)
        {
            if (obj == this) return true;
            if (obj == null) return false;
            if (obj.getClass()!=this.getClass()) return false;
            
            Aresta outro = (Aresta) obj;
            return this.id.equals(outro.id);
        }

        @Override
		public int hashCode ()
		{
			int ret=1;
			
			ret=2*ret+this.info.hashCode();
		
		    if (ret<0) ret=-ret;

			return ret;
		}

        
    }

    ListaEncadeadaSimplesDesordenada2<TpIdVrt, Vertice> vertices;
    ListaEncadeadaSimplesDesordenada2<TpIdArt, Aresta>  arestas;
    
    public Grafo ()
    {
        this.vertices = new ListaEncadeadaSimplesDesordenada2 <TpIdVrt,Vertice> ();
        this.arestas  = new ListaEncadeadaSimplesDesordenada2 <TpIdArt,Aresta> ();
    }
    
    public void adicioneVertice (TpIdVrt id, TpInfVrt inf) throws Exception
    {
        if (vertices.tem(id)) throw new Exception ("esse Id de vértice já existe");
        this.vertices.guardeNoInicio(new Vertice(id,inf));
    }
    
    public void adicioneAresta (TpIdArt id, TpInfArt inf, TpIdVrt org, TpIdVrt dst) throws Exception
    {
        this.arestas.guardeNoInicio(new Aresta(id,inf,org,dst));
    }

    public void removaAresta (TpIdArt id) throws Exception
    {

    } 

    public void removaVertice (TpIdVrt id ) throws Exception
    {

    }

    public boolean temVertice (TpIdVrt id) throws Exception
    {
        if(vertices.tem(id)) throw new Exception("esse Vértice existe!");
        else throw new Exception("esse Vértice não existe!");  

    }

    public boolean temAresta (TpIdArt id) throws Exception
    {
        if(arestas.tem(id)) throw new Exception("essa Aresta existe!");
        else throw new Exception("essa Aresta não existe!");

    }

    public void numVertices() throws Exception
    {

    }
    
    public void numAresta() throws Exception
    {

    } 
}
