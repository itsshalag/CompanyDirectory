import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.princeton.cs.algs4.Bag;

/**
 * This class represents a graph of contacts in a company directory.
 * It uses adjacency lists to store direct contact relationships. 
 * 
 * @author Yudith Mendoza & Shala Gutierrez
 */
class Graph {
    private Map<Integer, Contact> contactsMap; // Map to store contact by their ID
    private Map<Integer, Bag<Contact>> adj; // Adj list to store direct contacts
    private int edges; // num of edges or direct contacts in the graph

    /**
     * Constructor. Reads contact and direct contact relationship data from files and
     * initializes the graph.
     * 
     * @param contactFilename		The filename contains contact details.
     * @param directContactFilename	The filename containing direct contact relationship.
     */
    public Graph(String contactFilename, String directContactFilename) {
        contactsMap = new HashMap<>();	// initialize contacts map
        adj = new HashMap<>();	// initialize adj list
        edges = 0;	

        try {
            BufferedReader br = new BufferedReader(new FileReader(contactFilename));
            br.readLine(); // Skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 6) {
                    continue;
                }

                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                String phoneNumber = parts[3];
                String email = parts[4];
                String position = parts[5];

                Contact contact = new Contact(id, firstName, lastName, phoneNumber, email, position);
                contactsMap.put(id, contact); // add contact to contacts map
                adj.put(id, new Bag<>());	// initialize empty adj list for the contact
            }

            br.close(); // close file

            br = new BufferedReader(new FileReader(directContactFilename));
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 2) {
                    continue;
                }

                int sourceId = Integer.parseInt(parts[0]);
                int targetId = Integer.parseInt(parts[1]);

                if (contactsMap.containsKey(sourceId) && contactsMap.containsKey(targetId)) {
                    adj.get(sourceId).add(contactsMap.get(targetId)); // Add direct contact relationship
                    adj.get(targetId).add(contactsMap.get(sourceId)); // Add reverse relationship for undirected graph
                    edges++; // increment num of edges
                }
            }

            br.close();
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
        return contactsMap.size();
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
//	public void validateVertex(int id) {
//		if (id < 0 || id >= vertices) {
//			throw new IllegalArgumentException("Vertex ID is invalid");
//		}
//	}
//    /**
//	 * Returns an iterable collection of vertices adjacent to a given vertex
//	 * 
//	 * @param id the vertex ID
//	 * @return an iterable collection of adjacent vertices
//	 */
//	public Iterable<Integer> adj(int id) {
//		validateVertex(id);
//		return adj[id];
//	}
//    
//    public Contact getContact(int id) {
//		if (id < 0 || id >= vertices) {
//			throw new IllegalArgumentException("Invalid contact ID");
//		}
//		return contacts[id];
//	}
//	
    /**
     * Returns an iterable collection of direct contacts for a given contact ID.
     * If the contact ID is invalid or has no direct contacts, and empty
     * iterable is returned.
     * 
     * @param id 
     * @return iterable collection of direct contacts
     */
    public Iterable<Contact> getDirectContacts(int id) {
        return adj.getOrDefault(id, new Bag<>());
    }

    /**
	 * this is to test the graph class.
	 * Reads contact and direct contact relationship data from files.
	 * Allows user to input contact ID to see their direct contacts. 
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        Graph graph = new Graph("Resources/SmallShopsDirectory.txt", "Resources/DirectContact.txt");

        if (graph != null) {
            System.out.println("Number of vertices: " + graph.vertices());
            System.out.println("Number of edges: " + graph.edges());

            testDirectContacts(graph);
        } else {
            System.out.println("Graph is not properly initialized.");
        }
    }

    /**
     * Method to test and display direct contacts for specified employee ID.
     * Prompts user to enter a contact ID and displays the direct contacts for 
     * that contact. 
     * 
     * @param graph the graph containing contact info and direct contact relationships. 
     */
    public static void testDirectContacts(Graph graph) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the person to see their direct contacts:");
        int employeeId = scanner.nextInt();

        if (employeeId >= 1 && employeeId <= graph.vertices()) {
            Iterable<Contact> directContacts = graph.getDirectContacts(employeeId);
            System.out.println("Direct contacts for employee " + employeeId + ": ");
            for (Contact contact : directContacts) {
                System.out.println(contact);
            }
        } else {
            System.out.println("Invalid employee ID.");
        }
    }
}
