package GrafoHash;
import List.ListaEncadeadaSimplesDesordenada2;

public class Grafo <TpIdVrt extends Comparable<TpIdVrt>,
                    TpInfVrt,
                    TpIdArt extends Comparable<TpIdArt>,
                    TpInfArt>
{
    private class Vertice implements Identificavel <TpIdVrt>
    {
        private TpIdVrt  id;
        private TpInfVrt info;
        
        public Vertice (TpIdVrt id, TpInfVrt info) throws Exception
        {
            if (id==null) throw new Exception ("Vertice sem id");
            
            this.id = id;
            this.info = info;
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
            this.info = info;
        }

        public void setIdVert(TpIdVrt id){
            this.id = id;
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
    
    private class Aresta implements Identificavel <TpIdArt>
    {
        private TpIdArt    id;
        private TpInfArt   info;
        private TpIdVrt origem;
        private TpIdVrt destino;
        
        public Aresta (TpIdArt id, TpInfArt info, TpIdVrt org, TpIdVrt dst) throws Exception
        {
            if (id==null) throw new Exception ("Aresta sem id");
            if (org==null) throw new Exception ("Aresta sem origem");
            if (!vertices.tem(org)) throw new Exception ("Aresta com origem inexistente");
            if (dst==null) throw new Exception ("Aresta sem destino");
            if (!vertices.tem(org)) throw new Exception ("Aresta com destino inexistente");
            
            this.id     =id;
            this.info   =info;
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

    ListaEncadeadaSimplesDesordenada2<TpIdVrt, Vertice> vertices;
    ListaEncadeadaSimplesDesordenada2<TpIdArt, Aresta>  arestas;
    
    public Grafo ()
    {
        this.vertices = new ListaEncadeadaSimplesDesordenada2 <TpIdVrt,Vertice> ();
        this.arestas  = new ListaEncadeadaSimplesDesordenada2 <TpIdArt,Aresta> ();
    }
    
    public void adicioneVertice (TpIdVrt id, TpInfVrt info) throws Exception
    {
        if (vertices.tem(id)) throw new Exception ("esse Id de vértice já existe");
        this.vertices.guardeNoInicio(new Vertice(id,info));
    }
    
    public void adicioneAresta (TpIdArt id, TpInfArt info, TpIdVrt org, TpIdVrt dst) throws Exception
    {
        if(arestas.tem(id)) throw new Exception("Aresta já existe");
        this.arestas.guardeNoInicio(new Aresta(id,info,org,dst));
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

    public void removaVertice (TpIdVrt id ) throws Exception
    {
        if(!vertices.tem(id)) throw new Exception("o ID do verticie não exite");
        
        for(Aresta aresta : arestas){
            if(aresta.getOrigem().equals(id) || aresta.getDestino().equals(id)){
                arestas.remova(aresta.getId());
            }
        }

        vertices.remova(id);
 
    }



    public boolean temVertice (TpIdVrt id) throws Exception
    {
        if(vertices.tem(id)) throw new Exception("esse Vertice existe");
        else throw new Exception("esse Vertice não existe!");

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
        System.out.println("Número de arestas: " + arestas.getTamanho());
    } 
}
