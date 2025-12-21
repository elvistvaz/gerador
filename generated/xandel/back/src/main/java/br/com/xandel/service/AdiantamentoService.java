package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Adiantamento;
import br.com.xandel.mapper.AdiantamentoMapper;
import br.com.xandel.repository.AdiantamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Adiantamento.
 */
@Service
@Transactional
public class AdiantamentoService {

    private final AdiantamentoRepository repository;
    private final AdiantamentoMapper mapper;

    public AdiantamentoService(
            AdiantamentoRepository repository,
            AdiantamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AdiantamentoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<AdiantamentoResponseDTO> findById(Integer idAdiantamento) {
        return repository.findById(idAdiantamento)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AdiantamentoResponseDTO create(AdiantamentoRequestDTO dto) {
        Adiantamento entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AdiantamentoResponseDTO> update(Integer idAdiantamento, AdiantamentoRequestDTO dto) {
        return repository.findById(idAdiantamento)
            .map(entity -> {
                mapper.updateEntity(dto, entity);
                return mapper.toResponseDTO(repository.save(entity));
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idAdiantamento) {
        if (repository.existsById(idAdiantamento)) {
            repository.deleteById(idAdiantamento);
            return true;
        }
        return false;
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idAdiantamento) {
        return repository.existsById(idAdiantamento);
    }
}
