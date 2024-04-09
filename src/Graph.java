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
	private int vertices;
	private int edges;
	private Bag<Integer>[] adj;
	private Contact[] contacts;

	public Graph(int vertices) {
		this.vertices = vertices;
		this.edges = 0;
		adj = (Bag<Integer>[]) new Bag[vertices];
		contacts = new Contact[vertices]; // initialize the contacts array
		for (int i = 0; i < vertices; i++)
			adj[i] = new Bag<>();
	}

	public Graph(String contactFilename, String directContactFilename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Resources/SmallShopsDirectory.txt"));
			String line;
			int vertexCount = 0;
			int edgeCount = 0;
			boolean skipHeader = true;

			while ((line = br.readLine()) != null) {
				if (skipHeader) {
					skipHeader = false;
					continue; // skip header
				}
				vertexCount++; // count each line as a vertex
			}

			// reset buffer reader to read from the beginning
			br.close();

			// initialize arrays after determining the number of vertices
			adj = (Bag<Integer>[]) new Bag[vertexCount];
			contacts = new Contact[vertexCount];

			for (int i = 0; i < vertexCount; i++) {
				adj[i] = new Bag<>();
			}

			// read and process contacts file
			br = new BufferedReader(new FileReader("Resources/SmallShopsDirectory.txt"));
			// skip header
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length < 6) {
					continue;
				}

				// parse contact details
				int id = Integer.parseInt(parts[0]);
				String firstName = parts[1];
				String lastName = parts[2];
				String phoneNumber = parts[3];
				String email = parts[4];
				String position = parts[5];

				// create new Contact object for each line
				Contact contact = new Contact(id, firstName, lastName, phoneNumber, email, position);
				contacts[id - 1] = contact; // decrement vertexCount before indexing
			

				// add edges based on position
				for (int i = 0; i < vertexCount ; i++) { // decrecment vertexCount before iterating
					if (contacts[i] != null && contacts[i].getPosition().equals(position) && i != id - 1) {
						adj[i].add(id);
						adj[id - 1].add(i); // adjust index
						edgeCount++;
					}
				}

			}

			br.close();

			// read and process direct contacts file
			br = new BufferedReader(new FileReader("Resources/DirectContact.txt"));
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length < 2) {
					continue;
				}

				int id1 = Integer.parseInt(parts[0]);
				int id2 = Integer.parseInt(parts[1]);

				adj[id1 - 1].add(id2 - 1);
				adj[id2 - 1].add(id1 - 1);
				edgeCount++;
			}
			br.close();

			this.vertices = vertexCount;
			this.edges = edgeCount / 2;

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
		return vertices;
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
		if (id < 0 || id >= vertices) {
			throw new IllegalArgumentException("Vertex ID is invalid");
		}
	}

	/**
	 * Returns an iterable collection of vertices adjacent to a given vertex
	 * 
	 * @param id the vertex ID
	 * @return an iterable collection of adjacent vertices
	 */
	public Iterable<Integer> adj(int id) {
		validateVertex(id);
		return adj[id];
	}

	public Contact getContact(int id) {
		if (id < 0 || id >= vertices) {
			throw new IllegalArgumentException("Invalid contact ID");
		}
		return contacts[id];
	}

	/**
	 * this is to test the graph with the text file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Graph graph = new Graph("Resources/SmallShopsDirectory.txt", "Resources/DirectContact.txt");
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
