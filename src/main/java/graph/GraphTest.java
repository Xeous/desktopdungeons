package graph;

public class GraphTest {
	
	public static void main(String[] args){
		Graph graph = new Graph();
		
		Node node1 = new Node<String>("Alma");
		Node node2 = new Node<String>("Körte");
		
		Edge edge = new Edge(node1, node2);
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);
		
		
		graph.printNodes();
		graph.printEdges();
	}
}
