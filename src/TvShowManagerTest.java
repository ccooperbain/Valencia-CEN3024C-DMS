import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class TvShowManagerTest {
    TvShowManager tvShowManager = new TvShowManager();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    /**
     * Affirmitave tests
     */
    @org.junit.jupiter.api.Test
    @DisplayName("loadApp test function")
    void loadAppTest() {
        assertEquals(true,tvShowManager.loadApp(),"The load app did not correctly load the file");
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Adding objects to the array")
    void addObjectTest(){
        TvShow show = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        tvShowManager.addShow(show);
        assertEquals(1,tvShowManager.getTvShows().size(), "the number of shows is not the same as the expected amount");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove object from the array")
    void removeObjectTest(){
        TvShow tvshow1 = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        TvShow tvshow2 = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        tvShowManager.getTvShows().add(tvshow1);
        tvShowManager.getTvShows().add(tvshow2);
        tvShowManager.getTvShows().remove(tvshow2);
        assertEquals(1,tvShowManager.getTvShows().size(), "the number of shows is not the same as the expected amount");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update object from the array")
    void updateArrayTest(){
        TvShow tvshow1 = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        String tvshow1Text = tvshow1.toString();
        tvShowManager.getTvShows().add(tvshow1);
        tvShowManager.getTvShows().get(0).setTitle("new tvshow title");

        System.out.println(tvshow1Text);
        System.out.println(tvShowManager.getTvShows().get(0).toString());

        assertNotEquals(tvshow1Text,tvShowManager.getTvShows().get(0).toString());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("custom Action test Average")
    void customFunctionTest(){
        tvShowManager.getTvShows().add(new TvShow(1,"Breaking Bad",false,5,10,2020,9.0,true));
        tvShowManager.getTvShows().add(new TvShow(2,"Stranger Things",false,6,12,2021,8.0,true));
        tvShowManager.getTvShows().add(new TvShow(3,"Naruto",true,4,3,2019,7.0,true));
        tvShowManager.getTvShows().add(new TvShow(4,"The Office",false,7,15,2018,6.0,false));
        tvShowManager.getTvShows().add(new TvShow(5,"Dragon Ball Z",true,8,20,2017,5.0,false));
        System.out.println(tvShowManager.calculateAverageRating());

        assertEquals(7.0,tvShowManager.calculateAverageRating());
    }

    /**
     * Negative tests
     */
    @org.junit.jupiter.api.Test
    @DisplayName("NegativeloadApp test function")
    void NegativeloadAppTest() {
        assertNotEquals(true,tvShowManager.loadApp(),"The load app did not correctly load the file");
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Negative Adding objects to the array")
    void NegativeaddObjectTest(){
        TvShow show = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        tvShowManager.addShow(show);
        assertNotEquals(1,tvShowManager.getTvShows().size(), "the number of shows is not the same as the expected amount");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Negative Remove object from the array")
    void NegativeremoveObjectTest(){
        TvShow tvshow1 = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        TvShow tvshow2 = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        tvShowManager.getTvShows().add(tvshow1);
        tvShowManager.getTvShows().add(tvshow2);
        tvShowManager.getTvShows().remove(tvshow2);
        assertNotEquals(1,tvShowManager.getTvShows().size(), "the number of shows is not the same as the expected amount");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Negative Update object from the array")
    void NegativeupdateArrayTest(){
        TvShow tvshow1 = new TvShow(1,"Tvshow Name",1,1,1,2000,5.5,1);
        String tvshow1Text = tvshow1.toString();
        tvShowManager.getTvShows().add(tvshow1);
        tvShowManager.getTvShows().get(0).setTitle("new tvshow title");

        System.out.println(tvshow1Text);
        System.out.println(tvShowManager.getTvShows().get(0).toString());

        assertEquals(tvshow1Text,tvShowManager.getTvShows().get(0).toString());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Negative custom Action test Average")
    void NegativecustomFunctionTest(){
        tvShowManager.getTvShows().add(new TvShow(1,"Breaking Bad",false,5,10,2020,9.0,true));
        tvShowManager.getTvShows().add(new TvShow(2,"Stranger Things",false,6,12,2021,8.0,true));
        tvShowManager.getTvShows().add(new TvShow(3,"Naruto",true,4,3,2019,7.0,true));
        tvShowManager.getTvShows().add(new TvShow(4,"The Office",false,7,15,2018,6.0,false));
        tvShowManager.getTvShows().add(new TvShow(5,"Dragon Ball Z",true,8,20,2017,5.0,false));

        assertNotEquals(7.0,tvShowManager.calculateAverageRating());
    }

}