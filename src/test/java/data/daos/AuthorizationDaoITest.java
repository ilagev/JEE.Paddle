package data.daos;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Role;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class AuthorizationDaoITest {

    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private DaosService daosService;

    @Test
    public void testFindRoleByUser() {
        List<Role> roles = authorizationDao.findRoleByUser((User) daosService.getMap().get("u4"));
        assertEquals(1, roles.size());
        assertEquals(Role.PLAYER, roles.get(0));
    }

}
