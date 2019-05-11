/**
 * Public Transit
 * Author: Your Name and Carolyn Yao
 * Does this compile? Y
 */

/**
 * This class contains solutions to the Public, Public Transit problem in the
 * shortestTimeToTravelTo method. There is an existing implementation of a
 * shortest-paths algorithm. As it is, you can run this class and get the solutions
 * from the existing shortest-path algorithm.
 */
public class FastestRoutePublicTransit {

    /**
     * The algorithm that could solve for shortest travel time from a station S
     * to a station T given various tables of information about each edge (u,v)
     *
     * @param 'S' the s th vertex/station in the transit map, start From
     * @param 'T' the t th vertex/station in the transit map, end at
     * @param 'startTime the start time in terms of number of minutes from 5:30am
     * @param 'lengths lengths[u][v] The time it takes for a train to get between two adjacent stations u and v
     * @param 'first first[u][v] The time of the first train that stops at u on its way to v, int in minutes from 5:30am
     * @param 'freq freq[u][v] How frequently is the train that stops at u on its way to v
     * @return shortest travel time between S and T
     */

        public void myShortestTravelTime(
            int S,
            int T,
            int startTime,
            int[][] lengths,
            int[][] first,
            int[][] freq
    ) {
        // Your code along with comments here. Feel free to borrow code from any
        // of the existing method. You can also make new helper methods.

        //initialize all things
        if(S<0 || T>lengths.length-1){
            System.out.println("Source Or Destination is out of Range");
            return;
        }
        //get shortest path from S to T.
        int shortestPath[] = shortestPath(lengths, S, T);
        //get index of S in shortest path.
        int ind = getBeginningOfShortestPath(shortestPath,S);
        //get minimumTime to reach from S to T.
        int minimumTime = getMinimunTime(startTime,ind,lengths,first,freq,shortestPath);
        System.out.println("_______________________________\n");
        System.out.println("| Source | Destination | Time |");
        System.out.println("|   "+S+"    |  "+"    "+T+"      |  "+minimumTime+"   |");
        System.out.println("_______________________________");
    }


    /**
     * Finds the vertex with the minimum time from the source that has not been
     * processed yet.
     * @param 'times The shortest times from the source
     * @param 'processed boolean array tells you which vertices have been fully processed
     * @return the index of the vertex that is next vertex to process
     */

    public int findNextToProcess(int[] times, Boolean[] processed) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < times.length; i++) {
            if (processed[i] == false && times[i] <= min) {
                min = times[i];
                minIndex = i;
            }
        }
        return minIndex;
    }


    public void printShortestTimes(int times[]) {
        System.out.println("Vertex Distances (time) from Source");
        for (int i = 0; i < times.length; i++)
            System.out.println(i + ": " + times[i] + " minutes");
    }

    /**
     * Given an adjacency matrix of a graph, implements
     * @param graph The connected, directed graph in an adjacency matrix where
     *              if graph[i][j] != 0 there is an edge with the weight graph[i][j]
     * @param source The starting vertex
     */
    public int [] shortestPath(int graph[][], int source, int destination) {
        int numVertices = graph[0].length;
        int[] times = new int[numVertices];
        Boolean[] processed = new Boolean[numVertices];
        int[] path= new int[numVertices];
        int[] prev= new int[numVertices];
        prev[source]=-1;

        // Initialize all distances as INFINITE and processed[] as false
        for (int v = 0; v < numVertices; v++) {
            times[v] = Integer.MAX_VALUE;
            path[v] = -1;
            processed[v] = false;
        }

        // Distance of source vertex from itself is always 0
        times[source] = 0;

        // Find shortest path to all the vertices
        for (int count = 0; count < numVertices - 1 ; count++) {
            // Pick the minimum distance vertex from the set of vertices not yet processed.
            // u is always equal to source in first iteration.
            // Mark u as processed.
            int u = findNextToProcess(times, processed);
            processed[u] = true;

            // Update time value of all the adjacent vertices of the picked vertex.
            for (int v = 0; v < numVertices; v++) {
                // Update time[v] only if is not processed yet, there is an edge from u to v,
                // and total weight of path from source to v through u is smaller than current value of time[v]
                if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
                    times[v] = times[u] + graph[u][v];
                    prev[v]=u;
                }
            }
        }
        printShortestTimes(times);
        // Get connected path from source node to destination node.
        path = getShortestPathFromSourceToDest(destination,prev, path);
        return path;
    }

    public void shortestTime(int graph[][], int source) {
        int numVertices = graph[0].length;

        // This is the array where we'll store all the final shortest times
        int[] times = new int[numVertices];

        // processed[i] will true if vertex i's shortest time is already finalized
        Boolean[] processed = new Boolean[numVertices];

        // Initialize all distances as INFINITE and processed[] as false
        for (int v = 0; v < numVertices; v++) {
            times[v] = Integer.MAX_VALUE;
            processed[v] = false;
        }

        // Distance of source vertex from itself is always 0
        times[source] = 0;

        // Find shortest path to all the vertices
        for (int count = 0; count < numVertices - 1 ; count++) {
            // Pick the minimum distance vertex from the set of vertices not yet processed.
            // u is always equal to source in first iteration.
            // Mark u as processed.
            int u = findNextToProcess(times, processed);
            processed[u] = true;

            // Update time value of all the adjacent vertices of the picked vertex.
            for (int v = 0; v < numVertices; v++) {
                // Update time[v] only if is not processed yet, there is an edge from u to v,
                // and total weight of path from source to v through u is smaller than current value of time[v]
                if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
                    times[v] = times[u] + graph[u][v];
                }
            }

        }


        printShortestTimes(times);
    }
    
    public int getBeginningOfShortestPath(int [] shortestPath, int source){
        int i = shortestPath.length-1;
        while(shortestPath[i]!= source && i>0) {
            i --;
        }
        return i;
    }


    public int[] getShortestPathFromSourceToDest(int destination, int [] previous_nodes_array, int [] path){
        int index = destination;
        int currentIndex=0;
        while(previous_nodes_array[index]!=-1){
            path[currentIndex]= index;
            currentIndex++;
            index= previous_nodes_array[index];
        }
        path[currentIndex]= index;
        return path;
    }


    public int getMinimunTime(int startTime,int index, int[][]lengths,int[][]first,int[][]freq,int[]shortestPath){
        int minTime = 0;
        int nextTrainTime = 0;
        int timeCurrent = startTime;
        for(int i=index ; i>=1; i--) {
            if(i==0) {
                nextTrainTime = 0;
            }
            else{
                int currentStation = shortestPath[index];
                int nextStation = shortestPath[index-1];
                int firstTrainTime = first[currentStation][nextStation];
                //finding the next train time
                int j = 0;
                while(firstTrainTime < timeCurrent){
                    firstTrainTime= first[currentStation][nextStation] + (j*freq[currentStation][nextStation]);
                    j++;
                }
                nextTrainTime =  firstTrainTime + lengths[currentStation][nextStation];
            }
            minTime = minTime+ (nextTrainTime - timeCurrent);
            timeCurrent = nextTrainTime;
        }
        return minTime;
    }

    public static void main (String[] args) {
        int first[][];
        int lengths[][];
        int freq[][];

        int lengthTimeGraph[][] = new int[][]{
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };
        FastestRoutePublicTransit t = new FastestRoutePublicTransit();
        t.shortestTime(lengthTimeGraph, 0);
        
        FastestRoutePublicTransit t1 = new FastestRoutePublicTransit();
        t1.shortestPath(lengthTimeGraph, 0, 7);


        //YOUR TEST CASSES ARE HERE

        //Test Cases Experiments.
        FastestRoutePublicTransit experiment_no_1 = new FastestRoutePublicTransit();
        FastestRoutePublicTransit experiment_no_2 = new FastestRoutePublicTransit();
        FastestRoutePublicTransit experiment_no_3 = new FastestRoutePublicTransit();

        System.out.println("\n######################################");
        System.out.println("### SELF MADE TEST CASESES RESULT ####");
        System.out.println("######################################\n");


        //Create Own test cases.
        System.out.println("#### Test Case # 1 ###");
        //test case 1

        /*length(e)*/
        lengths = new int [][]{
                {0, 0,9, 5, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 3, 0, 7, 0},
                {9, 0, 0, 1, 5, 0, 0, 0, 4},
                {5, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 5, 5, 0, 4, 0, 0, 0},
                {0, 3, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 2, 0},
                {0, 7, 0, 0, 0, 0, 2, 0, 5},
                {1, 0, 4, 0, 0, 0, 0, 5, 0}
        };
        /*first(e)*/
        first = new int [][]{
                {0, 0, 4, 5, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 12, 0, 17, 0},
                {9, 0, 0, 11, 15, 0, 0, 0, 14},
                {6, 0, 10, 0, 0, 0, 0, 0, 0},
                {0, 0, 14, 15, 0, 24, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 12, 0, 0},
                {0, 0, 0, 0, 0, 9, 0, 2, 0},
                {0, 6, 0, 0, 0, 0, 8, 0, 15},
                {1, 0, 4, 0, 0, 0, 0, 8, 0}
        };
        /*freq(e)*/
        freq = new int [][]{
                {0, 0, 19,18, 0, 0, 0, 0, 17},
                {0, 0, 0, 0, 0, 3, 0, 7, 0},
                {9, 0, 0, 8, 7, 0, 0, 0, 6},
                {5, 0, 4, 0, 0, 0, 0, 0, 0},
                {0, 0, 5, 4, 0, 3, 0, 0, 0},
                {0, 3, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 0},
                {0, 17, 0, 0, 0, 0, 12, 0, 15},
                {11, 0, 14, 0, 0, 0, 0, 12, 0}};
        experiment_no_1.myShortestTravelTime(2,7,10,lengths,first,freq);

        System.out.println("\n#### Test Case # 2 ###");
        //test case 2

        /*length(e)*/
        lengths = new int[][]{
                {0, 9, 0, 1, 10, 0, 12},
                {9, 0, 0, 9, 1, 5, 1},
                {0, 0, 0, 6, 9, 7, 5},
                {1, 9, 6, 0, 1, 1, 0},
                {10, 1, 9, 1, 0, 0, 0},
                {0, 5, 7, 1, 0, 0, 11},
                {12, 1, 5, 0, 0, 11, 0}
        };

        /*first(e)*/
        first = new int[][]{
                {0, 6, 0, 2, 5, 0, 10},
                {6, 0, 0, 10, 10, 5, 10},
                {0, 0, 0, 8, 14, 15, 6},
                {2, 3, 4, 0, 4, 4, 0},
                {6, 7, 4, 10, 0, 0, 0},
                {0, 15, 4, 1, 0, 0, 6},
                {9, 4, 3, 0, 0, 5, 0}
        };

        /*freq(e)*/
        freq = new int[][]{
                {0, 15, 0, 20, 15, 0, 10},
                {6, 0, 0, 5, 7, 5, 9},
                {0, 0, 0, 6, 7, 9, 12},
                {10, 9, 8, 0, 7, 6, 0},
                {5, 4, 3, 10, 0, 0, 0},
                {0, 11, 20, 11, 0, 0, 16},
                {19, 14, 13, 0, 0, 2, 0}
        };
        experiment_no_2.myShortestTravelTime(3,5,15,lengths,first,freq);


        System.out.println("\n#### Test Case # 3 ###");
        //test case 3
        lengths = new int[][]{
                {0, 10, 20, 15, 0, 0, 9},
                {10, 0, 6, 8, 0, 0, 1},
                {20, 6, 0, 4, 4, 0, 0},
                {15, 8, 4, 0, 9, 1, 2},
                {0, 0, 4, 9, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 9},
                {9, 1, 0, 2, 0, 9, 0}
        };
        first = new int[][]{
                {0, 5, 10, 5, 0, 0, 9},
                {10, 0, 9, 8, 0, 0, 7},
                {5, 4, 0, 14, 3, 0, 0},
                {9, 8, 7, 0, 6, 5, 4},
                {0, 0, 14, 19, 0, 0, 0},
                {0, 0, 0, 11, 0, 0, 4},
                {5, 11, 0, 12, 0, 3, 0}
        };
        freq = new int[][]{
                {0, 10, 15, 20, 0, 0, 25},
                {1, 0, 2, 3, 0, 0, 4},
                {4, 3, 0, 2, 1, 0, 0},
                {10, 8, 6, 0, 4, 2, 11},
                {0, 0, 14, 15, 0, 0, 0},
                {0, 0, 0, 6, 0, 0, 11},
                {11, 2, 0, 10, 0, 5, 0}
        };
        experiment_no_3.myShortestTravelTime(2,6,10,lengths,first,freq);
    }




}
