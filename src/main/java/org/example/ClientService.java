package org.example;

import org.example.structureTable.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createSt;
    private PreparedStatement readSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteSt;
    private PreparedStatement selectAllSt;

    public ClientService(Connection connection) throws SQLException {
        createSt = connection
                .prepareStatement("INSERT INTO client (id, name) VALUES (?, ?)");

        readSt = connection
                .prepareStatement("SELECT name FROM client WHERE id = ?");

        updateSt = connection
                .prepareStatement("UPDATE client SET name = ? WHERE id = ?");

        deleteSt = connection
                .prepareStatement("DELETE FROM client WHERE id = ?");

        selectAllSt = connection
                .prepareStatement(
                        "SELECT id, name FROM client"
                );
    }

    public long create(Client client) throws SQLException{
        createSt.setLong(1, client.getId());
        createSt.setString(2, client.getName());
        createSt.executeUpdate();
        return client.getId();
    }

    public String getById(long id) throws SQLException{
        readSt.setLong(1, id);

        ResultSet rs = readSt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        String name = rs.getString("name");
        return name;
    }

    public String setName(long id, String name) throws SQLException{
        updateSt.setString(1, name);
        updateSt.setLong(2, id);
        updateSt.executeUpdate();

        return getById(id);
    }

    public void deleteById(long id) throws SQLException{
        deleteSt.setLong(1, id);
        deleteSt.executeUpdate();
    }

    public List<Client> listAll() throws SQLException{
        List<Client> result = new ArrayList<>();

        try (ResultSet rs = selectAllSt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");

                Client client = new Client();
                client.setName(name);
                client.setId(id);

                result.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}
