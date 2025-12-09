/*
	Time complexity: O(V + E)
	Space complexity: O(V + E)
	
	Where V is the number of vertices,
	E is the number of edges in the graph.
*/

import java.util.ArrayList;
import java.util.Stack;

public class Solution 
{   
    public static Stack<Integer> stk;
    public static ArrayList<Boolean> visited;

    public static void dfs(int u, ArrayList<ArrayList<Integer>> graph)
    {
	    if (visited.get(u) == true)
	    {
		    // Current node is already visited. So, return.
		    return;
	    }

	    // Current node has not be explored before. So, mark it visited.
	    visited.set(u, true);

	    // Traverse the adjacent nodes.
	    int edges = graph.get(u).size();

	    for (int i = 0; i < edges; i++)
	    {
		    int v = graph.get(u).get(i);
		    dfs(v, graph);
	    }

	    return;
    }

    public static ArrayList<ArrayList<Integer>> getTranspose(int vertices, ArrayList<ArrayList<Integer>> graph)
    {
	    ArrayList<ArrayList<Integer>> transposeGraph = new ArrayList<>(vertices);
        for(int i=0 ; i<vertices ; i++)
        {
            transposeGraph.add(new ArrayList<>());
        }

	    //  Generate the transpose of the given graph by reversing the direction of every edge.
	    for (int i = 0; i < vertices; i++)
	    {
		    int edges = graph.get(i).size();

		    for (int j = 0; j < edges; j++)
		    {
			    int u = graph.get(i).get(j);

			    transposeGraph.get(u).add(i);
		    }
	    }

	    return transposeGraph;
    }

    public static void dfsToFindFinishTime(int u, ArrayList<ArrayList<Integer>> graph)
    {
	    if (visited.get(u) == true)
	    {
		    // Current node is already visited. So, return.
		    return;
	    }

	    // Current node has not be explored before. So, mark it visited.
	    visited.set(u, true);

	    // Traverse the adjacent nodes.
	    int edges = graph.get(u).size();

	    for (int i = 0; i < edges; i++)
	    {
		    int v = graph.get(u).get(i);

		    dfsToFindFinishTime(v, graph);
	    }

	    // Exploration of current node is complete. So, push it in the stack.
	    stk.push(u);
	
        return;
    }

    public static int stronglyConnectedComponents(int v, ArrayList<ArrayList<Integer>> edges) 
    {   
	    // Create adjacency list for the graph.
	    ArrayList<ArrayList<Integer>> graph = new ArrayList<>(v);
        for(int i=0 ; i<v ; i++)
        {
            graph.add(new ArrayList<Integer>());
        }

	    int e = edges.size();
	    for (int i = 0; i < e; i++)
	    {
		    int a = edges.get(i).get(0);
		    int b = edges.get(i).get(1);
		    graph.get(a).add(b);
	    } 

	    // Create an empty stack to store the vertices in descending order of their finished time.
	    stk = new Stack<>();

	    // Create a visited array to keep track of the vertices visited during DFS.
        visited = new ArrayList<>(v);
        for(int i=0; i<v ; i++)
        {
            visited.add(false);
        }

	    // Apply DFS to determine the finish time of all the vertices.
	    for (int i = 0; i < v; i++)
	    {
		    if (visited.get(i) == false)
		    {
			    dfsToFindFinishTime(i, graph);
		    }
	    }

	    // Get the tranpose of the given graph.
	    ArrayList<ArrayList<Integer>> transposeGraph = getTranspose(v, graph);

	    // Mark all the vertices as unvisited.
	    for (int i = 0; i < v; i++)
	    {
		    visited.set(i, false);
	    }

	    // Create a variable to store the number of connected components in the graph.
	    int count = 0;

	    // Visit all the SCCs one by one.
	    while (stk.isEmpty() == false)
	    {
		    int node = stk.peek();
		    stk.pop();

		    if (visited.get(node) == false)
		    {
			    dfs(node, transposeGraph);

			    // Traversed a SCC. So, increment the count by one.
			    ++count;
		    }
	    }

	    return count;
	}
}
