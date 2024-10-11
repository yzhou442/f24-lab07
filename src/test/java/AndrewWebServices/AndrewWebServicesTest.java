package AndrewWebServices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;


import org.junit.Before;
import org.junit.Test;

public class AndrewWebServicesTest {
    class RecSysStub extends RecSys {
        @Override
        public String getRecommendation(String accountName) {
            // Return the expected value immediately
            return "Animal House";
        }
    }

    Database database;
    RecSys recommender;
    PromoService promoService;
    AndrewWebServices andrewWebService;


    @Before
    public void setUp() {
        // You need to use some mock objects here
        // database = new Database(); // We probably don't want to access our real database...



        database = new InMemoryDatabase();
        recommender = new RecSysStub();
        promoService = mock(PromoService.class);

        andrewWebService = new AndrewWebServices(database, recommender, promoService);
    }

    @Test
    public void testLogIn() {
        // This is taking way too long to test
        assertTrue(andrewWebService.logIn("Scotty", 17214));
        assertFalse(andrewWebService.logIn("Scotty", 0));
        assertFalse(andrewWebService.logIn("Scotty2", 17214));
    }

    @Test
    public void testGetRecommendation() {
        // This is taking way too long to test
        assertEquals("Animal House", andrewWebService.getRecommendation("Scotty"));
    }

    @Test
    public void testSendEmail() {
        // How should we test sendEmail() when it doesn't have a return value?
        // Hint: is there something from Mockito that seems useful here?
        andrewWebService.logIn("Scotty", 17214);
        andrewWebService.sendPromoEmail("test@cmu.edu");
        verify(andrewWebService.promoService).mailTo("test@cmu.edu");
    }

    @Test
    public void testNoSendEmail() {
        // How should we test that no email has been sent in certain situations (like right after logging in)?
        // Hint: is there something from Mockito that seems useful here?
        andrewWebService.logIn("Scotty", 17214);
        verify(andrewWebService.promoService, never()).mailTo("test@cmu.edu");
    }
}
