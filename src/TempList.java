/**
 * it reprensents each playlist and stores current number which sent to epic blend, and stores wait and last heaps
 * which corresponds to the last songs which sent to epic blend and removed from playlist but didnt send epic blend yet
 */
public class TempList {
    // the number of songs which sent to heartache epic blend
    int currentHeartache;
    // the number of songs which sent to roadtrip epic blend
    int currentRoadtrip;
    // the number of songs which sent to blissful epic blend
    int currentBlissful;
    //max heap including waiting songs
    waitPlaylist heartacheWait = new waitPlaylist(1);
    //max heap including waiting songs
    waitPlaylist roadtripWait = new waitPlaylist(2);
    //max heap including waiting songs
    waitPlaylist blissfulWait = new waitPlaylist(3);
    //max heap including songs which goes to heartache epic blend
    lastEpic lastHeartache = new lastEpic(1);
    //max heap including songs which goes to roadtrip epic blend
    lastEpic lastRoadtrip = new lastEpic(2);
    //max heap including songs which goes to blissful epic blend
    lastEpic lastBlissful = new lastEpic(3);
    /**
     * Constructs a TempList object with specified maximum song limits for epic playlists.
     * @param maxSong The maximum number of songs allowed in each epic playlist.
     */
    TempList(int maxSong) {
        this.currentHeartache = maxSong;
        this.currentRoadtrip = maxSong;
        this.currentBlissful = maxSong;
    }
}

