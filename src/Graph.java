import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.princeton.cs.algs4.Bag;

/**
 * I copied and pasted this so I don't get off track but feel free to delete it
 * We will create a graph where the vertices will be a
 * Contact (id, first and last name, phone number and email). The user will
 * first be greeted by all employees, then when one is selected, it will then
 * show all other employees/contacts that work the same departments (in other
 * words, peers they work directly with) alongside their contact information. We
 * will display these relationships in a graph with edges. Then if another
 * contact is selected, it will show which other employees they have a direct
 * contact with.
 * 
 * @author Yudith Mendoza & Shala Gutierrez
 */
class Graph {
	private int id;
	private int edges;
	private Bag<Integer>[] adj;

	public Graph(int id, int edges) {
		this.id = id;
		this.edges = edges;
		adj = (Bag<Integer>[]) new Bag[edges];
		for (int i = 0; i < edges; i++) {
			adj[i] = new Bag<>();
		}
	}

	public Graph(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int vertices = 0;
			int edges = 0;
			boolean skipHeader = true;

			while ((line = br.readLine()) != null) {
				if (skipHeader) {
					skipHeader = false;
					continue; // skip header
				}

				String[] parts = line.split("\\|");
				if (parts.length < 7) {
					continue;
				}

				int v = Integer.parseInt(parts[0]); // Parse only the ID field

				for (int i = 3; i < parts.length; i++) { // get the phone number field as a string
					if (i != 4) {
						String fieldValue = parts[i];
						System.out.println(fieldValue);
					}

				}

				vertices++;
				edges += parts.length - 2;

			}

			this.edges = edges / 2;
			this.adj = (Bag<Integer>[]) new Bag[vertices];
			for (int i = 0; i < vertices; i++) {
				adj[i] = new Bag<>();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the number of vertices in the graph
	 * 
	 * @return the number of vertices
	 */
	public int vertices() {
		return adj.length;
	}

	/**
	 * Returns the number of edges in the graph.
	 * 
	 * @return the number of edges
	 */
	public int edges() {
		return edges;
	}

	/**
	 * Validates whether a given index ID is valid
	 * 
	 * @param id the vertex ID to check
	 */
	public void validateVertex(int id) {
		if (id < 0 || id >= adj.length) {
			throw new IllegalArgumentException("Vertex id is invalid");
		}
	}

	/**
	 * Adds an edge between two vertices in the graph
	 * 
	 * @param v	the first vertex
	 * @param w the second vertex
	 */
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		adj[v].add(w);
		adj[w].add(v);
	}

	/**
	 * Returns an iterable collection of vertices adjacent to a given vertex
	 * 
	 * @param id the vertex ID
	 * @return	an iterable collection of adjacent vertices
	 */
	public Iterable<Integer> adj(int id) {
		validateVertex(id);
		return adj[id];
	}

	/**
	 * this is here to test the text file. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Graph graph = new Graph("resources/SmallShopsDirectory.txt");
		System.out.println("Number of vertices: " + graph.vertices());
		System.out.println("Number of vertices: " + graph.edges());
		for (int v = 0; v < graph.vertices(); v++) {
			System.out.print("Vertex " + v + ": ");
			for (int w : graph.adj(v)) {
				System.out.print(w + " ");
			}

			System.out.println();
		}
	}
}
