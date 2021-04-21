/**
 * https://tutorialspoint.dev/data-structure/graph-data-structure/minimum-cut-in-a-directed-graph
 * 
 * CPCS324 Final Project: Phase 3
 * Task 1: Maximum Flow and MinimumCut
 * Shahad Shamsan
 * Suha Shafi 
 * Safia Aljahdali
 */

import java.util.LinkedList; 
import java.util.Queue; 
  
public class MaximumFlow { 
 
    /**
     * 
     * @param rGraph
     * @param s
     * @param t
     * @param parent 
     * @return Returns true if there is a path 
     * from source 's' to sink 't' in residual graph.
     * Also fills parent[] to store the path 
     */
    private static boolean bfs(int[][] rGraph, int s, int t, int[] parent) { 
          
        // Create a visited array and mark  
        // all vertices as not visited      
        boolean[] visited = new boolean[rGraph.length]; 
          
        // Create a queue, enqueue source vertex 
        // and mark source vertex as visited      
        Queue<Integer> q = new LinkedList<Integer>(); 
        q.add(s); 
        visited[s] = true; 
        parent[s] = -1; 
          
        // Standard BFS Loop      
        while (!q.isEmpty()) { 
            int v = q.poll(); 
            for (int i = 0; i < rGraph.length; i++) { 
                if (rGraph[v][i] > 0 && !visited[i]) { 
                    q.offer(i); 
                    visited[i] = true; 
                    parent[i] = v; 
                } 
            } 
        } 
          
        // If we reached sink in BFS starting  
        // from source, then return true, else false      
        return (visited[t] == true); 
    } 
      
    
    /**
     * A DFS based function to find all reachable vertices from s.
     * The function marks visited[i] as true if i is reachable from s. 
     * The initial values in visited[] must be false.
     * We can also use BFS to find reachable vertices 
     * @param rGraph
     * @param s source
     * @param visited array to check visited vertices
     */
    private static void dfs(int[][] rGraph, int s, 
                                boolean[] visited) { 
        visited[s] = true; 
        for (int i = 0; i < rGraph.length; i++) { 
                if (rGraph[s][i] > 0 && !visited[i]) { 
                    dfs(rGraph, i, visited); 
                } 
        } 
    } 
  
    /**
     * Prints the maximum flow and corresponding minimum s-t cut
     * @param graph The graph in the problem
     * @param s source 
     * @param t sink
     */
    private static void MaxFlowMinCut(int[][] graph, int s, int t) { 
        int u,v; 
          
        // Create a residual graph and fill the residual  
        // graph with given capacities in the original  
        // graph as residual capacities in residual graph 
        // rGraph[i][j] indicates residual capacity of edge i-j 
        int[][] rGraph = new int[graph.length][graph.length];  
        for (int i = 0; i < graph.length; i++) { 
            for (int j = 0; j < graph.length; j++) { 
                rGraph[i][j] = graph[i][j]; 
            } 
        } 
  
        // This array is filled by BFS and to store path 
        int[] parent = new int[graph.length];  
        
        
        int maxFlow = 0; // There is no flow initially
        
        // Augment the flow while tere is path from source to sink      
        while (bfs(rGraph, s, t, parent)) { 
            String path = "";
            
            // Find minimum residual capacity of the edhes  
            // along the path filled by BFS. Or we can say  
            // find the maximum flow through the path found. 
            int pathFlow = Integer.MAX_VALUE;          
            for (v = t; v != s; v = parent[v]) { 
                String direction="<-";
                
                u = parent[v]; 
                pathFlow = Math.min(pathFlow, rGraph[u][v]); 
                if(graph[u][v]!=0) 
                    direction="->";
                
                path = direction+(v+1)+path;
            } 
            // add to path
            path = (v+1)+path;
            
            // print augmented path
            System.out.printf("%-17s %s %d \n",path,"flow: ",pathFlow);
              
            // update residual capacities of the edges and  
            // reverse edges along the path 
            for (v = t; v != s; v = parent[v]) { 
                u = parent[v]; 
                rGraph[u][v] = rGraph[u][v] - pathFlow; 
                rGraph[v][u] = rGraph[v][u] + pathFlow; 
            } 
            
            // Add path flow to overall flow
            maxFlow += pathFlow;
        }
        System.out.println("\nFinal Maximum Flow is: " + maxFlow+"\n");
          
        // Flow is maximum now, find vertices reachable from s      
        boolean[] isVisited = new boolean[graph.length];      
        dfs(rGraph, s, isVisited); 
          
        // Print all edges that are from a reachable vertex to 
        // non-reachable vertex in the original graph    
        System.out.println("Min-Cut:");
        for (int i = 0; i < graph.length; i++) { 
            for (int j = 0; j < graph.length; j++) { 
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) { 
                    System.out.println(i + " - " + j); 
                } 
            } 
        } 
    } 
  
    /**
     * Driver Program 
     * Creates the graph and calls the method to apply MaxFlow and MinCut
     * @param args 
     */
    public static void main(String args[]) { 
          
        // Let us create a graph shown in the above example 
        int graph[][] = new int[][]{{0, 2, 7, 0, 0, 0},
                                   {0, 0, 0, 3, 4, 0},
                                   {0, 0, 0, 4, 2, 0},
                                   {0, 0, 0, 0, 0, 1},
                                   {0, 0, 0, 0, 0, 5},
                                   {0, 0, 0, 0, 0, 0}};
        MaxFlowMinCut(graph, 0, 5); 
    } 
} 
