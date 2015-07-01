package edu.coursera.ipetrushin.algorithms.partone.percolation;

/**
 * We model a percolation system using an N-by-N grid of sites.
 * Each site is either open or blocked. A full site is an open site that can be connected to an open site
 * in the top row via a chain of neighboring (left, right, up, down) open sites.
 * We say the system percolates if there is a full site in the bottom row.
 * In other words, a system percolates if we fill all open sites connected to the top row
 * and that process fills some open site on the bottom row.
 * <p/>
 * (For the insulating/metallic materials example, the open sites correspond to metallic materials,
 * so that a system that percolates has a metallic path from top to bottom, with full sites conducting.
 * For the porous substance example, the open sites correspond to empty space through which water might flow,
 * so that a system that percolates lets water fill open sites, flowing from top to bottom.)
 * <p/>
 * In a famous scientific problem, researchers are interested in the following question:
 * if sites are independently set to be open with probability p (and therefore blocked with probability 1 ? p),
 * what is the probability that the system percolates? When p equals 0, the system does not percolate;
 * when p equals 1, the system percolates.
 * <p/>
 * <p/>
 * When N is sufficiently large, there is a threshold value p* such that when p < p* a random N-by-N grid almost never percolates,
 * and when p > p*, a random N-by-N grid almost always percolates.
 * No mathematical solution for determining the percolation threshold p* has yet been derived.
 * Your task is to write a computer program to estimate p*.
 * <p/>
 * Corner cases.
 * By convention, the row and column indices i and j are integers between 1 and N,
 * where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException if any argument to open(),
 * isOpen(), or isFull() is outside its prescribed range.
 * The constructor should throw a java.lang.IllegalArgumentException if N ? 0.
 * <p/>
 * Performance requirements.
 * The constructor should take time proportional to N2;
 * all methods should take constant time plus a constant number of calls
 * to the union-find methods union(), find(), connected(), and count().
 * <p/>
 * Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:
 * <p/>
 * Initialize all sites to be blocked.
 * Repeat the following until the system percolates:
 * Choose a site (row i, column j) uniformly at random among all blocked sites.
 * Open the site (row i, column j).
 * The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
 */
public class Percolation {

    private int flatSitesGrid[];
    private int componentWeights[];
    private int countOfComponents;
    private int sitesGridSize;
    private int gridDimension;
    private final static int COUNT_OF_VIRTUAL_SITES = 2;
    private final static byte SITE_BLOCKED_STATE = -1;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("wrong size of grid");
        }

        gridDimension = N;
        countOfComponents = gridDimension * gridDimension;
        sitesGridSize = countOfComponents + COUNT_OF_VIRTUAL_SITES;

        flatSitesGrid = new int[sitesGridSize];
        componentWeights = new int[sitesGridSize];

        resetGrid();

         /*
         assign default size  ("1") for virtual site components.
         all other components will get "0" while initialization of componentWeights array by default
         */
        componentWeights[0] = 1;
        componentWeights[sitesGridSize - 1] = 1;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (!isOpenSingleton(i, j)) {

            int currentSiteIndex = getSiteIndexInArray(i, j);

            //open site with link to itself for default root and default weight
            if (!isOpen(i, j)) {
                flatSitesGrid[currentSiteIndex] = currentSiteIndex;
                componentWeights[currentSiteIndex] = 1;
                componentWeights[0] = ++componentWeights[0]; //magnetize :)
            }

            //look up
            boolean isTopVirtualSite = ((i - 1) == 0);
            int topSiteIndex;

            if (isTopVirtualSite) {
                topSiteIndex = 0;
                weightAndUnion(topSiteIndex, currentSiteIndex);
            } else {
                unionSiteWithItsSabling(i - 1, j, currentSiteIndex);
            }

            //look to the left
            if (!isIndexOutOfBound(j - 1)) {
                unionSiteWithItsSabling(i, j - 1, currentSiteIndex);
            }

            //look to the right
            if (!isIndexOutOfBound(j + 1)) {
                unionSiteWithItsSabling(i, j + 1, currentSiteIndex);
            }

            //look to bottom
            boolean isBottomVirtualSite = ((i + 1) > gridDimension);
            int rootOfBottomSiteIndex;
            if (isBottomVirtualSite) {
                rootOfBottomSiteIndex = sitesGridSize - 1;
                weightAndUnion(currentSiteIndex, rootOfBottomSiteIndex);
            } else {
                unionSiteWithItsSabling(i + 1, j, currentSiteIndex);
            }
        }
    }

    private void unionSiteWithItsSabling(int i, int j, int currentSiteIndex) {
        //look to the right
        if (isOpen(i, j)) {
            int sablingIndex = getRoot(getSiteIndexInArray(i, j));
            weightAndUnion(currentSiteIndex, sablingIndex);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (isIndexOutOfBound(i) || isIndexOutOfBound(j)) {
            throw new IndexOutOfBoundsException("indexes: (i==" + i + " ,j==" + j + ") are out ouf range : 1.." + gridDimension);
        }

        int index = getSiteIndexInArray(i, j);
        return flatSitesGrid[index] != SITE_BLOCKED_STATE;
    }

    private boolean isOpenSingleton(int i, int j) {
        int index = getSiteIndexInArray(i, j);
        return isOpen(i, j) && flatSitesGrid[index] == index;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return isOpen(i, j) &&
                isOpenFallSafe(i - 1, j) && //top
                isOpenFallSafe(i + 1, j) && //bottom
                isOpenFallSafe(i, j - 1) && //left
                isOpenFallSafe(i, j + 1); //right
    }

    private int getSiteIndexInArray(int i, int j) {
        return (i - 1) * gridDimension + j;
    }


    // does the system percolate?
    public boolean percolates() {
        return getRoot(0) == getRoot(sitesGridSize - 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private boolean isPositionOutOfBound(int position) {
        return position < 1 || position > sitesGridSize - 2;/* || (index % gridDimension) == 0;*/
    }

    private boolean isIndexOutOfBound(int index) {
        return (index < 1 || index > gridDimension);
    }

    private boolean isOpenFallSafe(int i, int j) {
        return isIndexOutOfBound(i) ||
                isIndexOutOfBound(j) ||
                isOpen(i, j);
    }

    private void weightAndUnion(int p, int k) {
        int pRoot = getRoot(p);
        int weightParentOfP = componentWeights[pRoot];

        int kRoot = getRoot(k);
        int weightParentOfK = componentWeights[kRoot];

        if (weightParentOfP >= weightParentOfK) {
            flatSitesGrid[k] = pRoot;
            componentWeights[pRoot] = ++componentWeights[pRoot];
        } else {
            flatSitesGrid[p] = kRoot;
            componentWeights[kRoot] = ++componentWeights[kRoot];
        }
    }

    private int getRoot(int c) {
        int parent = flatSitesGrid[c];
        if (c != parent) {
            return getRoot(parent);
        } else {
            return c;
        }
    }

    private boolean find(int p, int k) {
        return getRoot(p) == getRoot(k);
    }

    public void resetGrid() {

       /*
        mark very first site, and a last one  as a virtual top and bottom sites,
         which are open (have reference to a root) by default
         */
        flatSitesGrid[0] = 0; // in fact, it's initialized with zero by default, so the operation is redundant
        flatSitesGrid[sitesGridSize - 1] = sitesGridSize - 1;

        //initialize grid with blocked sites, despite virtual  sites
        for (int i = 1; i <= sitesGridSize - 2; i++) {
            flatSitesGrid[i] = SITE_BLOCKED_STATE;
        }


    }

    public void resetSiteWeights() {

         /*
         assign default size  ("1") for virtual site components.
         all other components will get "0" while initialization of componentWeights array by default
         */
        componentWeights[0] = 1;
        componentWeights[sitesGridSize - 1] = 1;


        for (int weight = 1; weight < sitesGridSize - 1; weight++) {
            componentWeights[weight] = 0;
        }
    }
}