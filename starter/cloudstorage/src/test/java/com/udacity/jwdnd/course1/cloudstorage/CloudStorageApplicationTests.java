package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private String baseURL;
    private final String sampleUsername = "username";
    private final String samplePassword = "password";
    private final String sampleFirstName = "Fizz";
    private final String sampleLastName = "Buzz";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    @Order(1)
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /*
    Try accessing any page while unauthorized
    Expected to be rerouted to login page
     */
    @Test
    @Order(2)
    public void tryAccessWhileUnauthorized() {
        driver.get(baseURL + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
        driver.get(baseURL + "/");
        Assertions.assertEquals("Login", driver.getTitle());
    }


    @Test
    @Order(3)
    public void testLoginAndLogout() {
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signupUser(sampleFirstName, sampleLastName, sampleUsername, samplePassword);

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(sampleUsername, samplePassword);

        Assertions.assertEquals("Home", driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.logoutUser();

        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(4)
    public void createNoteTest() {
        loginUser();
        String noteTitle = "I am a Title";
        String noteDescription = "I am a Description";
        driver.get(baseURL + "/home");
        NotePage notePage = new NotePage(driver);
        notePage.createNote(driver, noteTitle, noteDescription);
        driver.get(baseURL + "/home");
        Note note = notePage.getFirstNote(driver);

        Assertions.assertEquals(noteTitle, note.getNoteTitle());
        Assertions.assertEquals(noteDescription, note.getNoteDescription());
    }

    @Test
    @Order(5)
    public void editNoteTest() {
        loginUser();
        String newNoteTitle = "I am a NEW Title";
        String newNoteDescription = "I am a NEW Description";
        driver.get(baseURL + "/home");
        NotePage notePage = new NotePage(driver);
        notePage.editFirstNote(driver, newNoteTitle, newNoteDescription);
        driver.get(baseURL + "/home");
        Note note = notePage.getFirstNote(driver);

        Assertions.assertEquals(newNoteTitle, note.getNoteTitle());
        Assertions.assertEquals(newNoteDescription, note.getNoteDescription());
    }

    @Test
    @Order(6)
    public void deleteNoteTest() {
        loginUser();
        driver.get(baseURL + "/home");
        NotePage notePage = new NotePage(driver);
        notePage.deleteFirstNote(driver);
        driver.get(baseURL + "/home");

        Assertions.assertThrows(NoSuchElementException.class, () -> notePage.getFirstNote(driver));
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    private void loginUser() {
        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(sampleUsername, samplePassword);
    }
}
