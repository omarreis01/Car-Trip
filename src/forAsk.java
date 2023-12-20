

import java.util.ArrayList;
/**
 * The `forAsk` class represents a priority queue implemented using a min-heap of songs.
 * Songs are prioritized based on their play order.
 */
public class forAsk {
    //ArrayList to store the heap of songs.
    public ArrayList<Song> heap;
    // The current size of the heap
    private int size;
    /**
     * Constructs an empty `forAsk` object.
     * Initializes the size to 0 and creates an ArrayList to store the heap, starting with a null element.
     */
    public forAsk() {
        this.size = 0;
        this.heap = new ArrayList<>();
        this.heap.add(null);
    }
    /**
     * Inserts a new song into the priority queue.
     *
     * @param song The song to be inserted.
     */
    public void insert(Song song) {
        ++this.size;
        this.heap.add(song);
        int hole = this.size;

        while(hole > 1 && song.comparePlayTo((Song)this.heap.get(hole / 2)) < 0) {
            Song parent = (Song)this.heap.get(hole / 2);
            this.heap.set(hole / 2, song);
            this.heap.set(hole, parent);
            hole /= 2;
        }
    }
    /**
     * Retrieves the song with the minimum play order from the priority queue without removing it.
     *
     * @return The song with the minimum play order.
     */
    public Song peek() {
        return (Song)this.heap.get(1);
    }
    /**
     * Removes and returns the song with the minimum play order from the priority queue.
     *
     * @return The song with the minimum play order.
     */
    public Song pop() {
        Song minItem = this.peek();
        this.heap.set(1, (Song)this.heap.get(this.size));
        --this.size;
        this.percolateDown(1);
        this.heap.remove(this.size + 1);
        return minItem;
    }
    /**
     * Returns the current size of the priority queue.
     *
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
            if (child != this.size && ((Song)this.heap.get(child + 1)).comparePlayTo((Song)this.heap.get(child)) < 0) {
                ++child;
            }

            if (((Song)this.heap.get(child)).comparePlayTo(temp) >= 0) {
                break;
            }

            this.heap.set(hole, (Song)this.heap.get(child));
        }

        this.heap.set(hole, temp);
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
        this.heap.remove(this.size + 1);

        return song;
    }
    /**
     * Restores the heap property by moving an element up the heap.
     *
     * @param hole The index at which percolateUp begins.
     */
    private void percolateUp(int hole) {
        int parent;
        Song temp = (Song) this.heap.get(hole);

        for (; hole > 1; hole = parent) {
            parent = hole / 2;

            if (((Song) this.heap.get(parent)).comparePlayTo(temp) <= 0) {
                break;
            }

            this.heap.set(hole, this.heap.get(parent));
        }
        this.heap.set(hole, temp);
    }
}

