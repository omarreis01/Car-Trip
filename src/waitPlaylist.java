
import java.util.ArrayList;
/**
 * The waitPlaylist class represents a min-heap which including songs extracted from playlist and waiting to send the epic blend
 */
public class waitPlaylist {
    public int i;
    private ArrayList<Song> heap;
    public int size;
    /**
     * Constructs a waitPlaylist object with the specified criteria identifier.
     * @param i Identifier indicating the criteria for song prioritization.
     */
    public waitPlaylist(int i) {
        this.i = i;
        this.size = 0;
        this.heap = new ArrayList();
        this.heap.add(null);
    }
    /**
     * Inserts a new song into the waitlist based on the specified criteria.
     * @param song The song to be inserted.
     */
    public void insert(Song song) {
        ++this.size;
        this.heap.add(song);
        int hole = this.size;

        while(hole > 1 && song.compareTo((Song)this.heap.get(hole / 2), this.i) < 0) {
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
     * Retrieves the song with the minimum priority from the waitlist without removing it.
     * @return The song with the minimum priority.
     */
    public Song peek() {
        return (Song)this.heap.get(1);
    }
    /**
     * Removes and returns the song with the minimum priority from the waitlist.
     *
     * @return The song with the minimum priority.
     */
    public Song pop() {
        Song maxItem = this.peek();
        this.heap.set(1, (Song)this.heap.get(this.size));
        --this.size;
        this.percolateDown(1);
        this.heap.remove(this.size + 1);
        if (this.i == 1) {
            maxItem.heartacheWaitIndex = -1;
        } else if (this.i == 2) {
            maxItem.roadtripWaitIndex = -1;
        } else if (this.i == 3) {
            maxItem.blissfulWaitIndex = -1;
        }
        return maxItem;

    }
    /**
     * Returns the current size of the waitlist.
     * @return The size of the waitlist.
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
            if (child != this.size && ((Song)this.heap.get(child + 1)).compareTo((Song)this.heap.get(child), this.i) < 0) {
                ++child;
            }

            if (((Song)this.heap.get(child)).compareTo(temp, this.i) >= 0) {
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
     * @param x The criteria identifier.
     * @param m The new index.
     * @param song The song whose index is to be adjusted.
     */
    public void changeIndex(int x, int m, Song song) {
        if (x == 1) {
            song.heartacheWaitIndex = m;
        } else if (x == 2) {
            song.roadtripWaitIndex = m;
        } else if (x == 3) {
            song.blissfulWaitIndex = m;
        }

    }
    /**
     * Removes a specific song from the waitlist at the given index.
     * @param song The song to be removed.
     * @param index The index of the song to be removed.
     * @return The removed song.
     */
    public Song remove(Song song, int index) {
        this.heap.set(index, (Song)this.heap.get(this.size));
        --this.size;
        this.percolateDown(index);
        if(i==1){percolateUp(song.heartacheWaitIndex);}
        if(i==2){percolateUp(song.roadtripWaitIndex);}
        if(i==3){percolateUp(song.blissfulWaitIndex);}
        this.heap.remove(this.size + 1);
        if (this.i == 1) {
            song.heartacheWaitIndex = -1;
        } else if (this.i == 2) {
            song.roadtripWaitIndex = -1;
        } else if (this.i == 3) {
            song.blissfulWaitIndex = -1;
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

            if (((Song) this.heap.get(parent)).compareTo(temp, this.i) <= 0) {
                break;
            }

            this.heap.set(hole, (Song) this.heap.get(parent));
            this.changeIndex(this.i, hole, (Song) this.heap.get(parent));
        }

        this.heap.set(hole, temp);
        this.changeIndex(this.i, hole, temp);
    }
}

