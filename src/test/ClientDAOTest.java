package test;
import dao.ClientDAO;
import model.Client;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDAOTest {

    @Test
    void testGetAllClients() {

        ClientDAO dao = new ClientDAO();
        List<Client> clients = dao.getAll();

        assertNotNull(clients);
        assertTrue(clients.size() > 0, "Should return clients");
    }

    @Test
    void testGetClientById() {

        ClientDAO dao = new ClientDAO();

        List<Client> all = dao.getAll();

        assertFalse(all.isEmpty(), "DB must have clients");

        Client first = all.get(0);

        Client c = dao.getById(first.getId());

        assertNotNull(c);
        assertEquals(first.getId(), c.getId());
    }
}