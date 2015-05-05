import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.WithApplication;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;


public class ApplicationTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));

    }

//    @Test
//    public void authenticateSuccess() {
//        Result result = callAction(
//                controllers.routes.ref.Application.authenticate(),
//                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
//                        "email", "bob@example.com",
//                        "password", "secret"))
//        );
//        assertThat(302 == status(result));
//        assertThat("bob@example.com".equals(session(result).get("email")));
//    }
}
