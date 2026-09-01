package com.punto.venta.service;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.repository.ClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // GET
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // --- MÉTODOS AÑADIDOS ---

    public List<ClienteDTO> mostrarActivos() {
        return clienteRepository.findByEstadoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> mostrarActivosFiltro(String nombre) {
        return clienteRepository.findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> mostrarActivosFiltroTop(String nombre) {
        return clienteRepository.findTop2ByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ------------------------

    // POST
    public Cliente save(ClienteDTO dto) {

        if (clienteRepository.existsByNombreIgnoreCaseAndApellidoIgnoreCase(
                dto.getNombre(),
                dto.getApellido())) {

            throw new RuntimeException("El cliente ya existe");
        }

        Cliente cliente = new Cliente();

        cliente.setEstado(dto.getEstado());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setFechaRegistro(dto.getFechaRegistro());

        return clienteRepository.save(cliente);
    }

    // PUT - ACTUALIZAR
    public ClienteDTO actualizar(Integer idCliente, ClienteDTO dto) {

        Cliente clienteExistente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente no encontrado"
                ));

        if (dto.getNombre() != null) {
            clienteExistente.setNombre(dto.getNombre());
        }

        if (dto.getEstado() != null) {
            clienteExistente.setEstado(dto.getEstado());
        }

        if (dto.getEmail() != null) {
            clienteExistente.setEmail(dto.getEmail());
        }

        if (dto.getTelefono() != null) {
            clienteExistente.setTelefono(dto.getTelefono());
        }

        if (dto.getFechaRegistro() != null) {
            clienteExistente.setFechaRegistro(dto.getFechaRegistro());
        }

        return convertToDTO(clienteRepository.save(clienteExistente));
    }

    // DELETE - ELIMINAR
    public void eliminar(Integer idCliente) {

        if (!clienteRepository.existsById(idCliente)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente no encontrado"
            );
        }

        clienteRepository.deleteById(idCliente);
    }
    
    public ClienteDTO anular(Integer idCliente) {

        Cliente clienteExistente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente no encontrado"
                ));

        clienteExistente.setEstado(false);

        return convertToDTO(clienteRepository.save(clienteExistente));
    }

    private ClienteDTO convertToDTO(Cliente cliente) {

        ClienteDTO dto = new ClienteDTO();

        dto.setIdCliente(cliente.getIdCliente());
        dto.setEstado(cliente.getEstado());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setFechaRegistro(cliente.getFechaRegistro());

        return dto;
    }

}