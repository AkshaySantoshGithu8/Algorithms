package PA2_Skeleton;
/*
 * Name: Jon Snow
 * EID: GoT0001
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission
// Do not add extra imports

import java.util.ArrayList;


public class Program2 {

    /**
     * findMinimumDistance
     *
     * @param problem  - contains the regions, the start region and the end region of the graph
     * @return the minimum distance possible to get from start region to end region in the given problem.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(Problem problem) {
        // TODO

        Heap<Region> heap = new Heap<>();
        ArrayList<Region> regions = problem.getRegions(); //adjacency list
        for (Region r : regions)
            r.resetMinDist();
        

        Region start = problem.getStartRegion();
        start.setMinDist(0);
        heap.buildHeap(regions);
        Region end = problem.getEndRegion();
        
        
        while (heap.getSize() > 0)
        {
            Region curr = heap.extractMin();
            
            if (curr == null)
                continue;
            //target  node
            if (curr.equals(end))
            {
                return curr.getMinDist();
            }
           
            ArrayList<Integer> weights = curr.getWeights();
            //for every neighbor
            for (int index = 0; index < curr.getNeighbors().size(); index++)
            {
                Region r = curr.getNeighbors().get(index);
                if (!heap.contains(r))
                    continue;
                int currCand = curr.getMinDist() + weights.get(index);
                if (currCand < r.getMinDist())
                {
                    heap.changeKey(r, currCand);
                }
                
            }
        }

        return -1;
    }

}
