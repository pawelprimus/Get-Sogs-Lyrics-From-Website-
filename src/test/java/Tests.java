import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @org.junit.jupiter.api.Test
    void checkIfArtistExist() {
        //given
        Artist artist_1 = new Artist("lona");
        Artist artist_2 = new Artist("taco hemingway");
        Artist artist_3 = new Artist("asdasdsdsffds");
        Artist artist_4 = new Artist("");
        Artist artist_5 = new Artist(" ");
        // then
        assertEquals(true, artist_1.checkIfArtistExist() );
        assertEquals(true, artist_2.checkIfArtistExist() );
        assertEquals(true, artist_3.checkIfArtistExist() );
        assertEquals(false, artist_4.checkIfArtistExist() );
        assertEquals(false, artist_5.checkIfArtistExist() );



    }
}