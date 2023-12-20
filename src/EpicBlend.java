

import java.util.ArrayList;
/**
 * The `EpicBlend` class represents a priority queue implemented using a max-heap of songs,
 * allowing songs to be prioritized based on specific criteria indicated by the 'i' parameter.
 * The class also provides methods for inserting, peeking at, and removing songs from the heap,
 * as well as utility methods for adjusting song indices.
 */
public class EpicBlend {
    //An identifier representing the specific criteria for song prioritization.
    public int i;
    // ArrayList to store the max-heap of songs.
    public ArrayList<Song> heap;
    // The current size of the heap.
    private int size;
    /**
     * Constructs an `EpicBlend` object with the specified criteria identifier.
     * @param i Identifier indicating the criteria for song prioritization.
     */
    public EpicBlend(int i) {
        this.i = i;
        this.size = 0;
        this.heap = new ArrayList();
        this.heap.add(null);
    }
    /**
     * Inserts a new song into the priority queue based on the specified criteria.
     *
     * @param song The song to be inserted.
     */
    public void insert(Song song) {
        ++this.size;
        this.heap.add(song);
        int hole = this.size;

        while(hole > 1 && song.compareTo((Song)this.heap.get(hole / 2), this.i) > 0) {
            Song parent = (Song)this.heap.get(hole / 2);
            this.heap.set(hole / 2, song);
            this.heap.set(hole, parent);
            this.changeIndex(this.i, hole, parent);
            hole /= 2;
            this.changeIndex(this.i, hole, song);
        }

        this.changeIndex(this.i, hole, song);
    }
    /**
     * Retrieves the song with the maximum priority from the priority queue without removing it.
     *
     * @return The song with the maximum priority.
     */
    public Song peek() {
        return (Song)this.heap.get(1);
    }
    /**
     * Removes and returns the song with the maximum priority from the priority queue.
     *
     * @return The song with the maximum priority.
     */
    public Song pop() {
        Song minItem = this.peek();
        this.heap.set(1, (Song)this.heap.get(this.size));
        --this.size;
        this.percolateDown(1);
        this.heap.remove(this.size + 1);
        if (this.i == 1) {
            minItem.heartacheEpicIndex = -1;
        } else if (this.i == 2) {
            minItem.roadtripEpicIndex = -1;
        } else if (this.i == 3) {
            minItem.blissfulEpicIndex = -1;
        }

        return minItem;
    }

    /**
     * Returns the current size of the priority queue.
     * @return The size of the priority queue.
     */
    public int size() {
        return this.size;
    }
    /**
     * Restores the heap property by moving an element down the heap.
     * @param hole The index at which percolateDown begins.
     */
    private void percolateDown(int hole) {
        int child;
        Song temp;
        for(temp = (Song)this.heap.get(hole); hole * 2 <= this.size; hole = child) {
            child = hole * 2;
            if (child != this.size && ((Song)this.heap.get(child + 1)).compareTo((Song)this.heap.get(child), this.i) > 0) {
                ++child;
            }

            if (((Song)this.heap.get(child)).compareTo(temp, this.i) <= 0) {
                break;
            }

            this.heap.set(hole, (Song)this.heap.get(child));
            this.changeIndex(this.i, hole, (Song)this.heap.get(child));
        }

        this.heap.set(hole, temp);
        this.changeIndex(this.i, hole, temp);
    }
    /**
     * Adjusts the index of a song based on the specified criteria.
     *
     * @param x The criteria identifier.
     * @param m The new index.
     * @param song The song whose index is to be adjusted.
     */
    public void changeIndex(int x, int m, Song song) {
        if (x == 1) {
            song.heartacheEpicIndex = m;
        } else if (x == 2) {
            song.roadtripEpicIndex = m;
        } else if (x == 3) {
            song.blissfulEpicIndex = m;
        }

    }
    /**
     * Removes a specific song from the priority queue at the given index.
     *
     * @param song The song to be removed.
     * @param index The index of the song to be removed.
     * @return The removed song.
     */
    public Song remove(Song song, int index) {
        this.heap.set(index,this.heap.get(this.size));
        --this.size;
        this.percolateDown(index);
        if(i==1){this.percolateUp(song.heartacheEpicIndex);}
        if(i==2){this.percolateUp(song.roadtripEpicIndex);}
        if(i==3){this.percolateUp(song.blissfulEpicIndex);}
        this.heap.remove(this.size + 1);
        if (this.i == 1) {
            song.heartacheEpicIndex = -1;
        } else if (this.i == 2) {
            song.roadtripEpicIndex = -1;
        } else if (this.i == 3) {
            song.blissfulEpicIndex = -1;
        }

        return song;
    }
    /**
     * Restores the heap property by moving an element up the heap.
     * @param hole The index at which percolateUp begins.
     */
    private void percolateUp(int hole) {
        int parent;
        Song temp = (Song) this.heap.get(hole);

        for (; hole > 1; hole = parent) {
            parent = hole / 2;

            if (((Song) this.heap.get(parent)).compareTo(temp, this.i) >= 0) {
                break;
            }

            this.heap.set(hole, this.heap.get(parent));
            this.changeIndex(this.i, hole, (Song) this.heap.get(parent));
        }

        this.heap.set(hole, temp);
        this.changeIndex(this.i, hole, temp);
    }
}
