import model.BenutzerDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Date;

public class ApplicationTest {

    @Before
    public void setUp() throws Exception{
        BenutzerDao dao = BenutzerDao.getInstance();
        dao.resetDB();
    }

    @Test
    public void RegistrierungsTest(){
        BenutzerDao dao = BenutzerDao.getInstance();
        Assert.assertTrue(dao.registerUser("name", "email2@web.de", "geheim"));
        Assert.assertFalse(dao.registerUser("name", "email2@web.de", "geheim"));
    }

    @Test
    public void addFriendTest(){
        BenutzerDao dao = BenutzerDao.getInstance();
        Assert.assertNotNull(dao.createLink("email1@web.de", "alex@web.de"));
        Assert.assertNull(dao.createLink("email1@web.de", "alex@web.de"));
    }

    @Test
    public void checkFriendsTest(){
        BenutzerDao dao = BenutzerDao.getInstance();
        Assert.assertTrue(dao.existLink("fabian@web.de", "alex@web.de"));
        Assert.assertFalse(dao.existLink("email1@web.de", "keinuser@web.de"));
    }

    @Test
    public void sendMessageTest(){
        BenutzerDao dao = BenutzerDao.getInstance();
        Assert.assertTrue(dao.persistMessage("fabian@web.de", "alex@web.de", "Huhu", (new Date()).toString()));
        Assert.assertFalse(dao.persistMessage("email1@web.de", "falscheruser@web.de", "Wer bist du?!", (new Date()).toString()));
        Assert.assertFalse(dao.persistMessage("", "empfaenger@web.de", "Wer bin ich?!", (new Date()).toString()));
        Assert.assertFalse(dao.persistMessage("email1@web.de", "", "Hups vertippt.", (new Date()).toString()));
    }

}
