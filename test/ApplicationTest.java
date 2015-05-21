import model.BenutzerDao;
import org.junit.Test;

public class ApplicationTest {

    @Test
    public void BenutzerTest(){
        BenutzerDao dao = new BenutzerDao();
        dao.registerUser("name", "email@web.de", "geheim");
    }



}
