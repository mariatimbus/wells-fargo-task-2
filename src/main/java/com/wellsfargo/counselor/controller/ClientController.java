package com.wellsfargo.counselor.controller;

import com.wellsfargo.counselor.entity.Client;
import com.wellsfargo.counselor.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Iterable<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable long clientId) {
        Optional<Client> client = clientService.getClientById(clientId);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody CreateClientRequest request) {
        Client client = clientService.createClient(
                request.advisorId(),
                request.firstName(),
                request.lastName(),
                request.address(),
                request.phone(),
                request.email(),
                request.dateOfBirth()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable long clientId, @RequestBody UpdateClientRequest request) {
        Optional<Client> client = clientService.updateClient(
                clientId,
                request.firstName(),
                request.lastName(),
                request.address(),
                request.phone(),
                request.email(),
                request.dateOfBirth()
        );
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable long clientId) {
        boolean deleted = clientService.deleteClient(clientId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    public record CreateClientRequest(
            long advisorId,
            String firstName,
            String lastName,
            String address,
            String phone,
            String email,
            LocalDate dateOfBirth
    ) {
    }

    public record UpdateClientRequest(
            String firstName,
            String lastName,
            String address,
            String phone,
            String email,
            LocalDate dateOfBirth
    ) {
    }
}
