package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.CategoryRequestDTO;
import com.devvictorh.cashflow.dto.CategoryResponseDTO;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.CategoryRepository;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.CategoryMapper;
import com.devvictorh.cashflow.validator.CategoryValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository repository;
    private final CategoryValidator validator;
    private final CategoryMapper mapper;

    public void createCategory(Long userId, CategoryRequestDTO dto) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        validator.validar(dto);
        validator.validarCategoriaUnica(dto, user);

        var mappedCategory = mapper.toEntity(dto);
        mappedCategory.setUserEntity(user);
        repository.save(mappedCategory);
    }

    public void updateCategory(Long userId, CategoryRequestDTO dto) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
        var categoryFound = repository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));

        categoryFound.setName(dto.name());
        categoryFound.setType(dto.type());

        var mappedCategory = mapper.toEntity(dto);
        mappedCategory.setUserEntity(user);
        repository.save(mappedCategory);
    }

    public List<CategoryResponseDTO> listAllCategories(Long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        var list = repository.findByUserEntity(user);
        return mapper.toResponseList(list);
    }

    public void deleteCategory(Long id) {
        var categoryFound = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
        repository.delete(categoryFound);
    }
}
