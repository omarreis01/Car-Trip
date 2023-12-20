/**
 * The `Song` class represents a music track with various attributes, including an identifier,
 * name, play count, and scores for different criteria. It also includes indices for different
 * epic playlists, last playlists, and waiting lists.
 */
public class Song {
    public int id;
    public String name;
    public int playCount;
    public int heartacheScore;
    public int roadtripScore;
    public int blissfulScore;
    public int playlistId;
    public int heartacheEpicIndex = -1;
    public int roadtripEpicIndex = -1;
    public int blissfulEpicIndex = -1;
    public int heartachePlaylistIndex = -1;
    public int roadtripPlaylistIndex = -1;
    public int blissfulPlaylistIndex = -1;
    public int heartacheLastIndex = -1;
    public int roadtripLastIndex = -1;
    public int blissfulLastIndex = -1;
    public int heartacheWaitIndex=-1;
    public int roadtripWaitIndex=-1;
    public int blissfulWaitIndex=-1;
    /**
     * Constructs a Song with the specified attributes.
     * @param id           Unique identifier for the song.
     * @param name         The name of the song.
     * @param playCount    The number of times the song has been played.
     * @param heartacheScore Score indicating the heartache level for the song.
     * @param roadtripScore Score indicating the road trip suitability for the song.
     * @param blissfulScore Score indicating the blissfulness of the song.
     */
    public Song(int id, String name, int playCount, int heartacheScore, int roadtripScore, int blissfulScore) {
        this.id = id;
        this.name = name;
        this.playCount = playCount;
        this.heartacheScore = heartacheScore;
        this.roadtripScore = roadtripScore;
        this.blissfulScore = blissfulScore;
    }
    /**
     * Compares this song to another song based on a specified criteria index.
     * @param other The other song to compare to.
     * @param i     The criteria index (1 for heartache, 2 for road trip, 3 for blissful).
     * @return A negative integer, zero, or a positive integer as this song is less than,
     *         equal to, or greater than the specified song.
     */
    public int compareTo(Song other, int i) {
        int blissfulComparison;
        if (i == 1) {
            blissfulComparison = Integer.compare(other.heartacheScore, this.heartacheScore);
            return blissfulComparison == 0 ? this.name.compareTo(other.name) : blissfulComparison;
        } else if (i == 2) {
            blissfulComparison = Integer.compare(other.roadtripScore, this.roadtripScore);
            return blissfulComparison == 0 ? this.name.compareTo(other.name) : blissfulComparison;
        } else {
            blissfulComparison = Integer.compare(other.blissfulScore, this.blissfulScore);
            return blissfulComparison == 0 ? this.name.compareTo(other.name) : blissfulComparison;
        }
    }
    /**
     * Compares this song to another song based on play count.
     * @param other The other song to compare to.
     * @return A negative integer, zero, or a positive integer as this song's play count is
     *         less than, equal to, or greater than the specified song's play count.
     */
    public int comparePlayTo(Song other){
        int playComparison=Integer.compare(other.playCount,this.playCount);
        return playComparison == 0 ? this.name.compareTo(other.name) : playComparison;
    }
}
