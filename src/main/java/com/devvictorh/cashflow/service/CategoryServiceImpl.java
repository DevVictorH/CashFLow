package com.devvictorh.cashflow.service;

import com.devvictorh.cashflow.dto.CategoryRequestDTO;
import com.devvictorh.cashflow.entity.UserEntity;
import com.devvictorh.cashflow.exceptions.BusinesException;
import com.devvictorh.cashflow.exceptions.ObjectNotFoundException;
import com.devvictorh.cashflow.repository.CategoryRepository;
import com.devvictorh.cashflow.repository.UserRepository;
import com.devvictorh.cashflow.service.mapper.CategoryMapper;
import com.devvictorh.cashflow.validator.CategoryValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository repository;
    private final CategoryValidator validator;
    private final CategoryMapper mapper;

    @Override
    public void createCategory(CategoryRequestDTO dto, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        validator.validar(dto);
        validator.validarCategoriaUnica(dto, user);

        var mappedCategory = mapper.toEntity(dto);
        mappedCategory.setUserEntity(user);
        repository.save(mappedCategory);
    }

    @Override
    public void updateCategory(Long id, CategoryRequestDTO dto, UserEntity entity) {
        var categoryFound = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));

        categoryFound.setName(dto.name());
        categoryFound.setType(dto.type());

        var mappedCategory = mapper.toEntity(dto);
        mappedCategory.setUserEntity(entity);
        repository.save(mappedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        var categoryFound = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
        repository.delete(categoryFound);
    }
}
