import java.io.*;
import java.util.*;
/**
 * The main class for Project3, which manages the song playlists and epic blends.
 */
public class Project3{
    //the song array which includes all the songs
    static Song[] allSongs;
    //max-heap including all heartache songs which is not in epic blend
    static Playlist heartachePlaylist = new Playlist(1);
    //max-heap including all roadtrip songs which is not in epic blend
    static Playlist roadTripPlaylist = new Playlist(2);
    //max-heap including all blissful songs which is not in epic blend
    static Playlist blissfulPlaylist = new Playlist(3);
    //heartache Epic blend
    static EpicBlend heartacheEpic = new EpicBlend(1);
    //roadtrip Epic blend
    static EpicBlend roadtripEpic = new EpicBlend(2);
    //blissful Epic blend
    static EpicBlend blissfulEpic = new EpicBlend(3);
    //hashmap including all the playlist
    static HashMap<Integer,TempList> playlistMap = new HashMap<>();
    //maximum number which epic blend have heartache songs
    static int maxEpicHeartache;
    //maximum number which epic blend have roadtrip songs
    static int maxEpicRoadtrip;
    //maximum number which epic blend have blissful songs
    static int maxEpicBlissful;


    //maximum number which each playlist can send songs to epic blend in each category
    static int maxContributeSong;


    /**
     * Main method to execute the project's functionality.
     *
     * @param args Command line arguments (not used in this project).
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        //initialize initial file
        File first = new File(args[0]);
        Scanner songsReader = new Scanner(first);
        FileWriter output= new FileWriter(args[2]);
        int numberSongs = Integer.parseInt(songsReader.nextLine());
        allSongs = new Song[numberSongs+1];
        //add the all songs to allsongs array
        for(int i =1;i<numberSongs+1;i++){
            String[] line = songsReader.nextLine().split(" ");
            int songId = Integer.parseInt(line[0]);
            String songName = line[1];
            int playCount = Integer.parseInt(line[2]);
            int heartacheScore = Integer.parseInt(line[3]);
            int roadtripScore = Integer.parseInt(line[4]);
            int blissfulScore = Integer.parseInt(line[5]);
            Song song= new Song(songId,songName,playCount,heartacheScore,roadtripScore,blissfulScore);
            allSongs[i] = song;
        }
        //initialize the test file
        File second = new File(args[1]);
        Scanner testReader = new Scanner(second);
        String[] line = testReader.nextLine().split(" ");
        maxContributeSong = Integer.parseInt(line[0]);
        maxEpicHeartache = Integer.parseInt(line[1]);
        maxEpicRoadtrip = Integer.parseInt(line[2]);
        maxEpicBlissful = Integer.parseInt(line[3]);
        int numberOfPlaylist = Integer.parseInt(testReader.nextLine());
        //initialize the playlists and add songs to playlists
        for(int i=0;i<numberOfPlaylist;i++){
            String[] playlistLine = testReader.nextLine().split(" ");
            int playId = Integer.parseInt(playlistLine[0]);
            TempList myTemp = new TempList(maxContributeSong);
            //initialize the playlist in map
            playlistMap.put(playId,myTemp);
            int playlistSongNumber = Integer.parseInt(playlistLine[1]);
            //if song number is 0 means if playlist is empty,then the line is empty and there is no song to add
            if(playlistSongNumber==0){
                testReader.nextLine();
            }
            else {
                //add each song to suitable playlists
                String[] songLine = testReader.nextLine().split(" ");
                for (int j = 0; j < playlistSongNumber; j++) {
                    int songIndex = Integer.parseInt(songLine[j]);
                    Song mySong = allSongs[songIndex];
                    mySong.playlistId = playId;
                    heartachePlaylist.insert(mySong);
                    roadTripPlaylist.insert(mySong);
                    blissfulPlaylist.insert(mySong);
                }
            }
        }
        // from initial max-heartache heap, send the suitable songs to epic blend
        while(heartacheEpic.size()< maxEpicHeartache && heartachePlaylist.size()>0){
            Song tempSong= heartachePlaylist.pop();
            if(playlistMap.get(tempSong.playlistId).currentHeartache>0){
                heartacheEpic.insert(tempSong);
                playlistMap.get(tempSong.playlistId).lastHeartache.insert(tempSong);
                playlistMap.get(tempSong.playlistId).currentHeartache-=1;
            }
            else{
                playlistMap.get(tempSong.playlistId).heartacheWait.insert(tempSong);
            }
        }
        // from initial max-roadtrip heap, send the suitable songs to epic blend
        while(roadtripEpic.size()<maxEpicRoadtrip&&roadTripPlaylist.size()>0){
            Song tempSong = roadTripPlaylist.pop();
            if(playlistMap.get(tempSong.playlistId).currentRoadtrip>0){
                roadtripEpic.insert(tempSong);
                playlistMap.get(tempSong.playlistId).lastRoadtrip.insert(tempSong);
                playlistMap.get(tempSong.playlistId).currentRoadtrip-=1;
            }
            else{
                playlistMap.get(tempSong.playlistId).roadtripWait.insert(tempSong);
            }
        }
        // from initial max-blissful heap, send the suitable songs to epic blend
        while(blissfulEpic.size()<maxEpicBlissful&&blissfulPlaylist.size()>0){
            Song tempSong =blissfulPlaylist.pop();
            if(playlistMap.get(tempSong.playlistId).currentBlissful>0){
                blissfulEpic.insert(tempSong);
                playlistMap.get(tempSong.playlistId).lastBlissful.insert(tempSong);
                playlistMap.get(tempSong.playlistId).currentBlissful-=1;
            }
            else{
                playlistMap.get(tempSong.playlistId).blissfulWait.insert(tempSong);
            }
        }
        int y=1;
        int numberOperation =Integer.parseInt(testReader.nextLine());
        for(int i=0;i<numberOperation;i++){
            String operation = testReader.next();
            // if method is adding
            if(operation.equals("ADD")){
                //initialize the numbers which can be added and removed
                int adding1=0;
                int adding2=0;
                int adding3=0;
                int removing1=0;
                int removing2=0;
                int removing3=0;
                int songId = Integer.parseInt(testReader.next());
                int playlistId = Integer.parseInt(testReader.next());
                Song mySong = allSongs[songId];
                mySong.playlistId=playlistId;
                //first situation is if song's playlist can send songs to epic blend
                if(playlistMap.get(playlistId).currentHeartache>0){
                    //if epic blend is full
                    if(maxEpicHeartache==heartacheEpic.size()) {
                        // then compare it the min element if our song is bigger than min element, the extract min element and insert our song
                        if (mySong.compareTo(heartacheEpic.peek(), 1) < 0) {
                            Song popSong = heartacheEpic.pop();
                            heartachePlaylist.insert(popSong);
                            playlistMap.get(popSong.playlistId).lastHeartache.pop();
                            playlistMap.get(playlistId).lastHeartache.insert(mySong);
                            heartacheEpic.insert(mySong);
                            playlistMap.get(popSong.playlistId).currentHeartache++;
                            playlistMap.get(playlistId).currentHeartache--;
                            adding1=songId;
                            removing1=popSong.id;
                        //if our song is less than min element then insert it heartache playlist
                        } else {
                            heartachePlaylist.insert(mySong);
                        }
                    }
                    //if epic blend is not full,then we can easily insert our song without removing anything
                    else{
                        heartacheEpic.insert(mySong);
                        playlistMap.get(playlistId).lastHeartache.insert(mySong);
                        adding1=songId;
                        playlistMap.get(playlistId).currentHeartache--;
                    }
                }
                //if song's playlist's number of songs which can be sent to epic blend is maximum,
                else{
                    //then compare our songs to last song which song's playlist send to, and if it is bigger,extract it and add our song
                    if (mySong.compareTo(playlistMap.get(playlistId).lastHeartache.peek(), 1) < 0) {
                        Song popSong = heartacheEpic.remove(playlistMap.get(playlistId).lastHeartache.peek(), playlistMap.get(playlistId).lastHeartache.peek().heartacheEpicIndex);
                        heartachePlaylist.insert(popSong);
                        heartacheEpic.insert(mySong);
                        playlistMap.get(popSong.playlistId).lastHeartache.pop();
                        playlistMap.get(playlistId).lastHeartache.insert(mySong);
                        adding1=songId;
                        removing1=popSong.id;
                    }
                    //if it is less than the last song whose playlist send to, then insert it heartache playlist
                    else{
                        heartachePlaylist.insert(mySong);
                    }
                }
                // same procedure as heartache
                if(playlistMap.get(playlistId).currentRoadtrip>0){
                    if(maxEpicRoadtrip==roadtripEpic.size()) {
                        if (mySong.compareTo(roadtripEpic.peek(), 2) < 0) {
                            Song popSong = roadtripEpic.pop();
                            roadTripPlaylist.insert(popSong);
                            playlistMap.get(popSong.playlistId).lastRoadtrip.pop();
                            playlistMap.get(playlistId).lastRoadtrip.insert(mySong);
                            roadtripEpic.insert(mySong);
                            playlistMap.get(popSong.playlistId).currentRoadtrip++;
                            playlistMap.get(playlistId).currentRoadtrip--;
                            adding2=songId;
                            removing2=popSong.id;
                        } else {
                            roadTripPlaylist.insert(mySong);
                        }
                    }
                    else{
                        roadtripEpic.insert(mySong);
                        playlistMap.get(playlistId).lastRoadtrip.insert(mySong);
                        adding2=songId;
                        playlistMap.get(playlistId).currentRoadtrip--;
                    }
                }
                else{
                    if (mySong.compareTo(playlistMap.get(playlistId).lastRoadtrip.peek(), 2) < 0) {
                        Song popSong = roadtripEpic.remove(playlistMap.get(playlistId).lastRoadtrip.peek(), playlistMap.get(playlistId).lastRoadtrip.peek().roadtripEpicIndex);
                        roadTripPlaylist.insert(popSong);
                        roadtripEpic.insert(mySong);
                        playlistMap.get(popSong.playlistId).lastRoadtrip.pop();
                        playlistMap.get(playlistId).lastRoadtrip.insert(mySong);
                        adding2=songId;
                        removing2=popSong.id;
                    }
                    else{
                        roadTripPlaylist.insert(mySong);
                    }
                }
                // same procedure as heartache
                if(playlistMap.get(playlistId).currentBlissful>0){
                    if(maxEpicBlissful==blissfulEpic.size()) {
                        if (mySong.compareTo(blissfulEpic.peek(), 3) < 0) {
                            Song popSong = blissfulEpic.pop();
                            blissfulPlaylist.insert(popSong);
                            playlistMap.get(popSong.playlistId).lastBlissful.pop();
                            playlistMap.get(playlistId).lastBlissful.insert(mySong);
                            blissfulEpic.insert(mySong);
                            playlistMap.get(popSong.playlistId).currentBlissful++;
                            playlistMap.get(playlistId).currentBlissful--;
                            adding3=songId;
                            removing3=popSong.id;
                        } else {
                            blissfulPlaylist.insert(mySong);
                        }
                    }
                    else{
                        blissfulEpic.insert(mySong);
                        playlistMap.get(playlistId).lastBlissful.insert(mySong);
                        adding3=songId;
                        playlistMap.get(playlistId).currentBlissful--;
                    }
                }
                else{
                    if (mySong.compareTo(playlistMap.get(playlistId).lastBlissful.peek(), 3) < 0) {
                        Song popSong = blissfulEpic.remove(playlistMap.get(playlistId).lastBlissful.peek(), playlistMap.get(playlistId).lastBlissful.peek().blissfulEpicIndex);
                        blissfulPlaylist.insert(popSong);
                        blissfulEpic.insert(mySong);
                        playlistMap.get(popSong.playlistId).lastBlissful.pop();
                        playlistMap.get(playlistId).lastBlissful.insert(mySong);
                        adding3=songId;
                        removing3=popSong.id;

                    }
                    else{
                        blissfulPlaylist.insert(mySong);
                    }
                }
                output.write(adding1 +" " +adding2+" "+adding3+"\n");
                output.write(removing1 + " " + removing2+ " "+removing3+"\n");
                output.flush();
            }
            //if our operation is removing
            else if(operation.equals("REM")){
                //initialize the numbers which can be added and removed
                int adding1=0;
                int adding2=0;
                int adding3=0;
                int removing1=0;
                int removing2=0;
                int removing3=0;
                int songId = Integer.parseInt(testReader.next());
                int playlistId = Integer.parseInt(testReader.next());
                //find the song to take our song's information
                Song mySong = allSongs[songId];
                // if it is in playlist,remove from playlist,there is no need any other procedure, because there is no song replace it or something
                if(allSongs[songId].heartachePlaylistIndex!=-1){
                    heartachePlaylist.remove(mySong,mySong.heartachePlaylistIndex);
                }
                // if it is in epic blend
                else if(allSongs[songId].heartacheEpicIndex!=-1){
                    //remove our song from epic blend
                    heartacheEpic.remove(mySong, mySong.heartacheEpicIndex);
                    playlistMap.get(playlistId).lastHeartache.remove(mySong,mySong.heartacheLastIndex);
                    removing1=songId;
                    //increase by 1 removing song's playlist number which is number of songs which can be sent to epic blend
                    playlistMap.get(playlistId).currentHeartache++;
                    //songs can be found in waitlist or playlist
                    // if both of them is empty, there is no need to do anything, because there is no song
                    if(heartachePlaylist.size()==0&&playlistMap.get(playlistId).heartacheWait.size()==0){}
                    //if waitlist is not empty, and playlist is empty, we can easily extract max song from wait list and add epic blend
                    else if(playlistMap.get(playlistId).heartacheWait.size()!=0&&heartachePlaylist.size()==0){
                        Song newSong = playlistMap.get(playlistId).heartacheWait.pop();
                        heartacheEpic.insert(newSong);
                        adding1=newSong.id;
                        playlistMap.get(playlistId).currentHeartache--;
                        playlistMap.get(playlistId).lastHeartache.insert(newSong);
                    }
                    //if waitlist is empty, and playlist is not empty, until finding suitable song enter the loop and when finding a suitable song, add it to epic blend
                    else if(playlistMap.get(playlistId).heartacheWait.size()==0&&heartachePlaylist.size()>0){
                        while(playlistMap.get(heartachePlaylist.peek().playlistId).currentHeartache==0){
                            Song waitSong= heartachePlaylist.pop();
                            playlistMap.get(waitSong.playlistId).heartacheWait.insert(waitSong);
                            if(heartachePlaylist.size()==0){
                                break;
                            }
                        }
                        // if after while loop the list is empty do nothing
                        if(heartachePlaylist.size()==0){
                        }
                        // if we find the suitable song add it to epic blend
                        else {
                            Song newSong = heartachePlaylist.pop();
                            heartacheEpic.insert(newSong);
                            adding1 = newSong.id;
                            playlistMap.get(newSong.playlistId).lastHeartache.insert(newSong);
                            playlistMap.get(newSong.playlistId).currentHeartache -= 1;
                        }
                    }
                    //if both of them is not empty,so we should check either waitlist and playlist max songs and compare it, then take the maximum one
                    else{
                        boolean m=false;
                        // firstly find the suitable song from playlist
                        while(heartachePlaylist.peek().compareTo(playlistMap.get(playlistId).heartacheWait.peek(),1)<0){
                            //if suitable song from playlist found, then insert it to epic blend
                            if(playlistMap.get(heartachePlaylist.peek().playlistId).currentHeartache>0){
                                Song newSong=heartachePlaylist.pop();
                                heartacheEpic.insert(newSong);
                                adding1=newSong.id;
                                playlistMap.get(newSong.playlistId).lastHeartache.insert(newSong);
                                playlistMap.get(newSong.playlistId).currentHeartache-=1;
                                m=true;
                                break;
                            }
                            // if suitable song from playlist cannot be found, then extract the max element from waitlist and add it to epic blend
                            else if(playlistMap.get(heartachePlaylist.peek().playlistId).currentHeartache==0){
                                Song waitSong= heartachePlaylist.pop();
                                playlistMap.get(waitSong.playlistId).heartacheWait.insert(waitSong);
                                if(heartachePlaylist.size()==0){
                                    break;
                                }
                            }
                        }
                        // then add the song to epic blend,if not already added
                        if(!m) {
                            Song newSong = playlistMap.get(playlistId).heartacheWait.pop();
                            heartacheEpic.insert(newSong);
                            adding1 = newSong.id;
                            playlistMap.get(playlistId).currentHeartache--;
                            playlistMap.get(playlistId).lastHeartache.insert(newSong);
                        }
                    }
                }
                //if it is in waitlist, remove it from playlist
                else{
                    playlistMap.get(playlistId).heartacheWait.remove(mySong,mySong.heartacheWaitIndex);
                }
                //same procedure as heartache
                if(allSongs[songId].roadtripPlaylistIndex!=-1){
                    roadTripPlaylist.remove(mySong,mySong.roadtripPlaylistIndex);
                }
                else if(allSongs[songId].roadtripEpicIndex!=-1){
                    roadtripEpic.remove(mySong, mySong.roadtripEpicIndex);
                    playlistMap.get(playlistId).lastRoadtrip.remove(mySong,mySong.roadtripLastIndex);
                    removing2=songId;
                    playlistMap.get(playlistId).currentRoadtrip++;
                    if(roadTripPlaylist.size()==0&&playlistMap.get(playlistId).roadtripWait.size()==0){}
                    else if(playlistMap.get(playlistId).roadtripWait.size()!=0&&roadTripPlaylist.size()==0){
                        Song newSong = playlistMap.get(playlistId).roadtripWait.pop();
                        roadtripEpic.insert(newSong);
                        playlistMap.get(playlistId).currentRoadtrip--;
                        adding2=newSong.id;
                        playlistMap.get(playlistId).lastRoadtrip.insert(newSong);
                    }
                    else if(playlistMap.get(playlistId).roadtripWait.size()==0&&roadTripPlaylist.size()>0){
                        while(playlistMap.get(roadTripPlaylist.peek().playlistId).currentRoadtrip==0){
                            Song waitSong= roadTripPlaylist.pop();
                            playlistMap.get(waitSong.playlistId).roadtripWait.insert(waitSong);
                            if(roadTripPlaylist.size()==0){
                                break;
                            }
                        }
                        if(roadTripPlaylist.size()==0){

                        }
                        else {
                            Song newSong = roadTripPlaylist.pop();
                            roadtripEpic.insert(newSong);
                            adding2 = newSong.id;
                            playlistMap.get(newSong.playlistId).lastRoadtrip.insert(newSong);
                            playlistMap.get(newSong.playlistId).currentRoadtrip -= 1;
                        }
                    }
                    else{
                        boolean m=false;
                        while(roadTripPlaylist.peek().compareTo(playlistMap.get(playlistId).roadtripWait.peek(),2)<0){
                            if(playlistMap.get(roadTripPlaylist.peek().playlistId).currentRoadtrip>0){
                                Song newSong=roadTripPlaylist.pop();
                                roadtripEpic.insert(newSong);
                                adding2=newSong.id;
                                playlistMap.get(newSong.playlistId).lastRoadtrip.insert(newSong);
                                playlistMap.get(newSong.playlistId).currentRoadtrip-=1;
                                m=true;
                                break;
                            }
                            else if(playlistMap.get(roadTripPlaylist.peek().playlistId).currentRoadtrip==0){
                                Song waitSong= roadTripPlaylist.pop();
                                playlistMap.get(waitSong.playlistId).roadtripWait.insert(waitSong);
                                if(roadTripPlaylist.size()==0){
                                    break;
                                }
                            }
                        }
                        if(!m) {
                            Song newSong = playlistMap.get(playlistId).roadtripWait.pop();
                            roadtripEpic.insert(newSong);
                            adding2 = newSong.id;
                            playlistMap.get(playlistId).currentRoadtrip--;
                            playlistMap.get(playlistId).lastRoadtrip.insert(newSong);
                        }
                    }
                }
                else{
                    playlistMap.get(playlistId).roadtripWait.remove(mySong,mySong.roadtripWaitIndex);
                }
                // same procedure as heartache
                if(allSongs[songId].blissfulPlaylistIndex!=-1){
                    blissfulPlaylist.remove(mySong,mySong.blissfulPlaylistIndex);
                }
                else if(allSongs[songId].blissfulEpicIndex!=-1){
                    blissfulEpic.remove(mySong, mySong.blissfulEpicIndex);
                    playlistMap.get(playlistId).lastBlissful.remove(mySong,mySong.blissfulLastIndex);
                    removing3=songId;
                    playlistMap.get(playlistId).currentBlissful++;
                    if(blissfulPlaylist.size()==0&&playlistMap.get(playlistId).blissfulWait.size()==0){}
                    else if(playlistMap.get(playlistId).blissfulWait.size()!=0&&blissfulPlaylist.size()==0){
                        Song newSong = playlistMap.get(playlistId).blissfulWait.pop();
                        blissfulEpic.insert(newSong);
                        adding3=newSong.id;
                        playlistMap.get(playlistId).currentBlissful--;
                        playlistMap.get(playlistId).lastBlissful.insert(newSong);
                    }
                    else if(playlistMap.get(playlistId).blissfulWait.size()==0&&blissfulPlaylist.size()>0){
                        while(playlistMap.get(blissfulPlaylist.peek().playlistId).currentBlissful==0){
                            Song waitSong= blissfulPlaylist.pop();
                            playlistMap.get(waitSong.playlistId).blissfulWait.insert(waitSong);
                            if(blissfulPlaylist.size()==0){
                                break;
                            }
                        }
                        if(blissfulPlaylist.size()==0){
                        }
                        else {
                            Song newSong = blissfulPlaylist.pop();
                            blissfulEpic.insert(newSong);
                            adding3 = newSong.id;
                            playlistMap.get(newSong.playlistId).lastBlissful.insert(newSong);
                            playlistMap.get(newSong.playlistId).currentBlissful -= 1;
                        }
                    }
                    else{
                        boolean m=false;
                        while(blissfulPlaylist.peek().compareTo(playlistMap.get(playlistId).blissfulWait.peek(),3)<0){
                            if(playlistMap.get(blissfulPlaylist.peek().playlistId).currentBlissful>0){
                                Song newSong=blissfulPlaylist.pop();
                                blissfulEpic.insert(newSong);
                                adding3=newSong.id;
                                playlistMap.get(newSong.playlistId).lastBlissful.insert(newSong);
                                playlistMap.get(newSong.playlistId).currentBlissful-=1;
                                m=true;
                                break;
                            }
                            else if(playlistMap.get(blissfulPlaylist.peek().playlistId).currentBlissful==0){
                                Song waitSong= blissfulPlaylist.pop();
                                playlistMap.get(waitSong.playlistId).blissfulWait.insert(waitSong);
                                if(blissfulPlaylist.size()==0){
                                    break;
                                }
                            }
                        }
                        if(!m) {
                            Song newSong = playlistMap.get(playlistId).blissfulWait.pop();
                            blissfulEpic.insert(newSong);
                            adding3 = newSong.id;
                            playlistMap.get(playlistId).currentBlissful--;
                            playlistMap.get(playlistId).lastBlissful.insert(newSong);
                        }

                    }
                }
                else{
                    playlistMap.get(playlistId).blissfulWait.remove(mySong,mySong.blissfulWaitIndex);
                }
                // write the added or removed songs to the output
                output.write(adding1 +" " +adding2+" "+adding3+"\n");
                output.write(removing1 + " " + removing2+ " "+removing3+"\n");
                output.flush();
            }
            // if the operation is asking
            else if(operation.equals("ASK")){
                // we sort the heap with heapSort method
                // creating a new heap add all songs to it,and pop min element until heap is empty,
                // create another min heap
                forAsk myHeap = new forAsk();
                // create hash set not to duplicate songs
                HashSet<Song> checkList = new HashSet<>();
                // add all songs from epic blend to min heap
                for(int j=heartacheEpic.size();j>0;j--){
                    myHeap.insert(heartacheEpic.heap.get(j));
                    checkList.add(heartacheEpic.heap.get(j));
                }
                // add all songs from epic blend to min heap
                for(int j=roadtripEpic.size();j>0;j--){
                    if(!checkList.contains(roadtripEpic.heap.get(j))) {
                        myHeap.insert(roadtripEpic.heap.get(j));
                        checkList.add(roadtripEpic.heap.get(j));
                    }
                }
                // add all songs from epic blend to min heap
                for(int j=blissfulEpic.size();j>0;j--){
                    if(!checkList.contains(blissfulEpic.heap.get(j))) {
                        myHeap.insert(blissfulEpic.heap.get(j));
                    }
                }
                // after adding all songs pop the min element until heap is empty
                while(myHeap.size()>0){
                    if(myHeap.size()==1){
                        output.write(myHeap.pop().id+"\n");
                        output.flush();
                    }
                    else {
                        output.write(myHeap.pop().id + " ");
                        output.flush();
                    }
                }
            }
        }
    }
}
