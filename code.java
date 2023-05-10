/* Online Java Compiler and Editor */
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;

class ListNode {
    String song;
    ListNode prev;
    ListNode next;
    public ListNode(String song) {
        this.song = song;
        this.prev = null;
        this.next = null;
    }
}

class SongList {
    int capacity;
    ListNode head;
    ListNode tail;

    public SongList() {
        this.capacity = 0;
        this.head = null;
        this.tail = null;
    }

    public void addSong(int maxCapacity, String song) {
        this.capacity++;
        ListNode songNode = new ListNode(song);
        this.head.prev  = songNode;
        songNode.next = this.head;
        this.head = songNode;
        isAlreadyInList(song);
        if(this.capacity > maxCapacity) {
            this.capacity--;
            // remove least recent song
                this.tail = this.tail.prev;
                this.tail.next = null;
        } 
    }

    public void isAlreadyInList(String song) {
        // check after head
        ListNode temp = this.head.next;
        while(temp != null) {
            if(temp.song.compareTo(song) == 0) {
                this.capacity--;
                // remove this node
                if(temp == this.tail) {
                    this.tail = temp.prev;
                    this.tail.next = null;
                } else {
                    temp.prev.next = temp.next;
                temp.next = null;
                }
            }
            temp = temp.next;
        }
    }
    
    public void recentSongs() {
        ListNode temp = this.head;
        while(temp != null) {
            System.out.println(temp.song);
            temp = temp.next;
        }
    }
}

 class RecentlyPlayedStore {
    private int capacity;
    private Map<String, SongList> songMap; // user -> song mapping

    public RecentlyPlayedStore(int capacity) {
        this.capacity = capacity;
        this.songMap = new HashMap<>();
    }

    public void playSong(String song, String user) {
        if (songMap.containsKey(user)) {
            SongList node = songMap.get(user);
            node.addSong(this.capacity, song);
        } else {
            SongList node = new SongList();
            ListNode songNode = new ListNode(song);
            node.head = songNode;
            node.tail = songNode;
            node.capacity =1;
            songMap.put(user, node);
        }
    }

    public void getRecentlyPlayedSongs(String user) {
        if (songMap.containsKey(user)) {
            SongList node = songMap.get(user);
            System.out.println("Recently played songs for " + user);
            node.recentSongs();
        } else {
           System.out.println("No user found");
        }
    }
}


public class RecentlyPlayedStoreTest {
     public static void main(String []args){
        RecentlyPlayedStore store = new RecentlyPlayedStore(3);  // Capacity of 3 songs per user
        store.playSong("Song A", "User 1");
        store.playSong("Song B", "User 1");
        store.playSong("Song C", "User 1");
        store.playSong("Song D", "User 1");
        store.getRecentlyPlayedSongs("User 1");
        // Expected Output: 
        // Recently played songs for User 1
        // Song D
        // Song C
        // Song B
        store.playSong("Song C", "User 1");
        store.getRecentlyPlayedSongs("User 1");
        // Expected Output: 
        // Recently played songs for User 1
        // Song C
        // Song D
        // Song B
        store.playSong("Song X", "User 2");
        store.playSong("Song Y", "User 2");
        store.playSong("Song Z", "User 2");
        store.getRecentlyPlayedSongs("User 2");
        // Expected Output: 
        // Recently played songs for User 2
        // Song Z
        // Song Y
        // Song X
        store.getRecentlyPlayedSongs("User 3");
        // Expected Output: 
        // No user found
     }
}