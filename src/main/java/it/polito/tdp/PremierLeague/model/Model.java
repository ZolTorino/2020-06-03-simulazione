package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	public PremierLeagueDAO dao;
	private SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> grafo;
	Map<Integer, Player> idMap;
	public Model() {
		dao= new PremierLeagueDAO();
	}
	
	public void creaGrafo(double x) {
		idMap=new HashMap<>();
		dao.listVertices(idMap,x);
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo,idMap.values());
		System.out.println("creato con vertici: "+grafo.vertexSet().size());
		
		LinkedList<Adiacenza> adiacenze = new LinkedList<>(dao.getAdiacenze(idMap));
		
		for(Adiacenza a:adiacenze)
		{
			if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
				if(a.getPeso()>0)
					Graphs.addEdge(grafo, a.getP1(), a.getP2(), a.getPeso());
				if(a.getPeso()<0)
					Graphs.addEdge(grafo, a.getP2(), a.getP1(), -a.getPeso());
			}
		}
		System.out.println("creato con archi: "+grafo.edgeSet().size());

	}
	public Player getTopPlayer() {
		if(grafo == null)
			return null;
		
		Player best = null;
		Integer maxDegree = 0;
		for(Player p : grafo.vertexSet()) {
			if(grafo.outDegreeOf(p) > maxDegree) {
				maxDegree = grafo.outDegreeOf(p);
				best = p;
			}
			System.out.println(p.name+ " "+grafo.outDegreeOf(p));

		}
		
		
		return best;
		
	}
	int migliorTitolarita=0;
	LinkedList<Player> team;
	LinkedList<Player> players;
	public String dreamTeam(int k) {
		team=new LinkedList<Player>();
		LinkedList<Player> parziale=new LinkedList<Player>();
		migliorTitolarita=0;
	
		for(Player p: grafo.vertexSet())
		{
			p.titolarita=(grafo.outDegreeOf(p)-grafo.inDegreeOf(p));
		}
		calcola(parziale,new LinkedList<Player>(idMap.values()),k);
		String out="";
		for(Player p: team)
		{
			out+=p.name+"\n";
		}
		return out;	
	}
	public void calcola(LinkedList<Player>parziale, List<Player>players, int k)
	{
		if(parziale.size()==k)
		{
			if(titolarita(parziale)>migliorTitolarita)
			{
				team=new LinkedList<Player>(parziale);	
				migliorTitolarita=titolarita(parziale);
			}
			return;
		}
		else
		{
			for(Player p : players) {
				if(!parziale.contains(p)) {
					parziale.add(p);
					LinkedList<Player> remainingPlayers  = new LinkedList<>(players);
					remainingPlayers.removeAll(Graphs.successorListOf(grafo, p));
					
					calcola(parziale,remainingPlayers  ,k);
					parziale.remove(p);
				}
			}
				
		}
	}
	/*public int titolarita(LinkedList<Player> lista)
	{
		int titolarita=0;
		for(Player p: lista)
		{
			titolarita+=(grafo.outDegreeOf(p)-grafo.inDegreeOf(p));
		}
		return titolarita;
	}*/
	private int titolarita(LinkedList<Player> parziale) {
		double risultato = 0;
		for(Player p: parziale) {
			for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(p)) {
				risultato += grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e : grafo.incomingEdgesOf(p)) {
				risultato -= grafo.getEdgeWeight(e);
			}
		}
		return (int)risultato;
		
	}
	
}
