

import java.util.ArrayList;

/**
 * The `lastEpic` class represents a playlist with a max-heap structure based on a specified criterion.
 * Songs are organized in descending order based on their comparison using the specified criterion.
 */
public class lastEpic {
    public int i;
    public ArrayList<Song> heap;
    private int size;
    /**
     * Constructs a new lastEpic playlist with the specified criterion identifier.
     *
     * @param i The criterion identifier (1 for heartache, 2 for road trip, 3 for blissful).
     */
    public lastEpic(int i) {
        this.i = i;
        this.size = 0;
        this.heap = new ArrayList();
        this.heap.add(null);
    }
    /**
     * Inserts a song into the playlist, maintaining the max-heap property.
     *
     * @param song The song to be inserted into the playlist.
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
     * Retrieves the top song from the playlist without removing it.
     *
     * @return The top song in the playlist.
     */
    public Song peek() {
        return (Song)this.heap.get(1);
    }
    /**
     * Removes and returns the top song from the playlist, maintaining the max-heap property.
     *
     * @return The top song that has been removed from the playlist.
     */
    public Song pop() {
        Song minItem = this.peek();
        this.heap.set(1, (Song)this.heap.get(this.size));
        --this.size;
        this.percolateDown(1);
        this.heap.remove(this.size + 1);
        if (this.i == 1) {
            minItem.heartacheLastIndex = -1;
        } else if (this.i == 2) {
            minItem.roadtripLastIndex = -1;
        } else if (this.i == 3) {
            minItem.blissfulLastIndex = -1;
        }

        return minItem;
    }
    /**
     * Builds the max-heap by percolating down from the middle of the playlist.
     */
    private void buildHeap() {
        for(int i = this.size / 2; i > 0; --i) {
            this.percolateDown(i);
        }

    }
    /**
     * Returns the current size of the playlist.
     *
     * @return The size of the playlist.
     */
    public int size() {
        return this.size;
    }
    /**
     * Percolates down from the specified hole to maintain the max-heap property.
     *
     * @param hole The starting index for percolating down.
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
     * Changes the index of a song within the playlist based on the specified criterion.
     *
     * @param x    The criterion identifier (1 for heartache, 2 for road trip, 3 for blissful).
     * @param m    The new index to be set for the song.
     * @param song The song whose index is to be changed.
     */
    public void changeIndex(int x, int m, Song song) {
        if (x == 1) {
            song.heartacheLastIndex = m;
        } else if (x == 2) {
            song.roadtripLastIndex = m;
        } else if (x == 3) {
            song.blissfulLastIndex = m;
        }

    }
    /**
     * Removes a song at the specified index from the playlist, maintaining the max-heap property.
     *
     * @param song  The song to be removed.
     * @param index The index of the song in the playlist.
     * @return The song that has been removed from the playlist.
     */
    public Song remove(Song song, int index) {
        this.heap.set(index, (Song)this.heap.get(this.size));
        --this.size;
        this.percolateDown(index);
        if(i==1){percolateUp(song.heartacheLastIndex);}
        if(i==2){percolateUp(song.roadtripLastIndex);}
        if(i==3){percolateUp(song.blissfulLastIndex);}
        this.heap.remove(this.size + 1);
        if (this.i == 1) {
            song.heartacheLastIndex = -1;
        } else if (this.i == 2) {
            song.roadtripLastIndex = -1;
        } else if (this.i == 3) {
            song.blissfulLastIndex = -1;
        }

        return song;
    }
    /**
     * Percolates up from the specified hole to maintain the max-heap property.
     *
     * @param hole The starting index for percolating up.
     */
    private void percolateUp(int hole) {
        int parent;
        Song temp = this.heap.get(hole);

        while (hole > 1) {
            parent = hole / 2;

            if (temp.compareTo(this.heap.get(parent), this.i) <= 0) {
                break;
            }

            this.heap.set(hole, this.heap.get(parent));
            this.changeIndex(this.i, hole, this.heap.get(parent));
            hole = parent;
        }

        this.heap.set(hole, temp);
        this.changeIndex(this.i, hole, temp);
    }
}
