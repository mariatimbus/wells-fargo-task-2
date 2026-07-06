package com.wellsfargo.counselor.service;

import com.wellsfargo.counselor.entity.Advisor;
import com.wellsfargo.counselor.entity.Client;
import com.wellsfargo.counselor.entity.Portfolio;
import com.wellsfargo.counselor.repository.AdvisorRepository;
import com.wellsfargo.counselor.repository.ClientRepository;
import com.wellsfargo.counselor.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AdvisorRepository advisorRepository;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AdvisorRepository advisorRepository, PortfolioRepository portfolioRepository) {
        this.clientRepository = clientRepository;
        this.advisorRepository = advisorRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(long clientId) {
        return clientRepository.findById(clientId);
    }

    public Client createClient(long advisorId, String firstName, String lastName, String address, String phone, String email, LocalDate dateOfBirth) {
        Optional<Advisor> advisor = advisorRepository.findById(advisorId);
        if (advisor.isEmpty()) {
            throw new IllegalArgumentException("Advisor not found: " + advisorId);
        }

        Client client = new Client(advisor.get(), firstName, lastName, address, phone, email, dateOfBirth);
        client = clientRepository.save(client);

        Portfolio portfolio = new Portfolio(client, LocalDate.now());
        portfolio = portfolioRepository.save(portfolio);
        client.setPortfolio(portfolio);

        return client;
    }

    public Optional<Client> updateClient(long clientId, String firstName, String lastName, String address, String phone, String email, LocalDate dateOfBirth) {
        Optional<Client> existing = clientRepository.findById(clientId);
        if (existing.isEmpty()) {
            return Optional.empty();
        }

        Client client = existing.get();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAddress(address);
        client.setPhone(phone);
        client.setEmail(email);
        client.setDateOfBirth(dateOfBirth);

        return Optional.of(clientRepository.save(client));
    }

    public boolean deleteClient(long clientId) {
        if (!clientRepository.existsById(clientId)) {
            return false;
        }
        clientRepository.deleteById(clientId);
        return true;
    }
}
