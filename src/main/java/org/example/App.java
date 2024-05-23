package org.example;

import org.example.databaseService.DataSource;
import org.example.structureTable.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException{
        DataSource dataSource = DataSource.getInstance();
        Connection connection = dataSource.getConnection();
        ClientService clientService = new ClientService(connection);

        Client client = new Client();
        client.setId(15);
        client.setName("Leon");

        long result = clientService.create(client);
        System.out.println("1. INSERT Name = Leon : Id = " + result);

        String name = clientService.getById(15);
        System.out.println("2. SELECT by Id = 15 : name = " + name);

        name = clientService.setName(15, "Lora_client");
        System.out.println("3. UPDATE Id = 15 : New name = " + name);

        System.out.println("4. DELETE by ID = 15");
        clientService.deleteById(15);

        System.out.println("5. SELECT all TABLE client");
        List<Client> clients = clientService.listAll();
        for (Client clientt : clients) {
            System.out.println("Id = " + clientt.getId() + " Name = " + clientt.getName());
        }

    }
}
