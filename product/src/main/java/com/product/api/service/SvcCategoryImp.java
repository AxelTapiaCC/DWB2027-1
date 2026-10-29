package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SvcCategoryImp implements SvcCategory {

    private static final int MAX_CATEGORY_LENGTH = 50;
    private static final int MAX_TAG_LENGTH = 50;
    private static final Integer STATUS_ACTIVE = 1;

    private final RepoCategory repo;

    SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> findAll() {
        try {
            return repo.getAll();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findActive() {
        try {
            return repo.findActive();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findChilds(Integer id) {
        requireExistingCategory(id);
        try {
            return repo.findChilds(id);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void create(DtoCategoryIn in) {
        validateInput(in);
        String category = in.getCategory().trim();
        String tag = in.getTag().trim();

        validateParent(in.getParentCategoryId(), null);
        validateUniqueness(category, tag, null);

        try {
            repo.create(category, tag, in.getParentCategoryId());
        } catch (DataAccessException e) {
            // Red de seguridad ante condiciones de carrera
            validateDatabaseCategoryError(e);
            throw new DBAccessException(e);
        }
    }

    @Override
    public void update(DtoCategoryIn in, Integer id) {
        requireExistingCategory(id);
        validateInput(in);
        String category = in.getCategory().trim();
        String tag = in.getTag().trim();

        validateParent(in.getParentCategoryId(), id);
        validateUniqueness(category, tag, id);

        try {
            repo.update(id, category, tag, in.getParentCategoryId());
        } catch (DataAccessException e) {
            validateDatabaseCategoryError(e);
            throw new DBAccessException(e);
        }
    }

    @Override
    public void enable(Integer id) {
        Category category = requireExistingCategory(id);

        // No se puede activar una categoria hija bajo un padre inactivo
        Integer parentId = category.getParentCategoryId();
        if (parentId != null) {
            Category parent = findCategory(parentId)
                    .orElseThrow(() -> new ApiException(HttpStatus.CONFLICT, "La categoría padre no existe"));
            if (!STATUS_ACTIVE.equals(parent.getStatus())) {
                throw new ApiException(HttpStatus.CONFLICT,
                        "No se puede activar la categoría porque su categoría padre está inactiva");
            }
        }

        try {
            repo.enable(id);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void disable(Integer id) {
        requireExistingCategory(id);

        boolean hasActiveChildren;
        try {
            hasActiveChildren = repo.existsActiveChildren(id);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
        if (hasActiveChildren) {
            throw new ApiException(HttpStatus.CONFLICT, "La categoría tiene subcategorías activas");
        }

        try {
            repo.disable(id);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }


    private void validateInput(DtoCategoryIn in) {
        if (in == null || in.getCategory() == null || in.getCategory().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría es obligatoria");
        }
        if (in.getTag() == null || in.getTag().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El tag es obligatorio");
        }
        if (in.getCategory().trim().length() > MAX_CATEGORY_LENGTH) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "La categoría no puede exceder " + MAX_CATEGORY_LENGTH + " caracteres");
        }
        if (in.getTag().trim().length() > MAX_TAG_LENGTH) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "El tag no puede exceder " + MAX_TAG_LENGTH + " caracteres");
        }
    }

    private void validateParent(Integer parentCategoryId, Integer categoryId) {
        if (parentCategoryId == null) {
            return;
        }
        if (parentCategoryId <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El id de la categoría padre debe ser positivo");
        }
        if (parentCategoryId.equals(categoryId)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");
        }

        Category parent = findCategory(parentCategoryId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no existe"));
        if (!STATUS_ACTIVE.equals(parent.getStatus())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no está activa");
        }

        if (categoryId != null) {
            validateNoCycle(parent, categoryId);
        }
    }

    //Este metodo recorre los ancestros del nuevo padre, si aparece categoryId, se formaría un ciclo
    private void validateNoCycle(Category newParent, Integer categoryId) {
        Set<Integer> visited = new HashSet<>();
        Category current = newParent;
        while (current != null && current.getParentCategoryId() != null) {
            Integer ancestorId = current.getParentCategoryId();
            if (ancestorId.equals(categoryId)) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "La categoría padre seleccionada es una subcategoría de esta categoría");
            }
            if (!visited.add(ancestorId)) {
                break;
            }
            current = findCategory(ancestorId).orElse(null);
        }
    }

    private void validateUniqueness(String category, String tag, Integer excludeId) {
        try {
            if (repo.existsByCategory(category, excludeId)) {
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
            }
            if (repo.existsByTag(tag, excludeId)) {
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
            }
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }


    private Optional<Category> findCategory(Integer id) {
        try {
            return repo.findById(id);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private Category requireExistingCategory(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El id de la categoría debe ser positivo");
        }
        return findCategory(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe"));
    }

    private String databaseMessage(DataAccessException e) {
        Throwable root = e.getRootCause();
        String message = root != null ? root.getMessage() : e.getLocalizedMessage();
        return message != null ? message : "";
    }

    private void validateDatabaseCategoryError(DataAccessException e) {
        String message = databaseMessage(e);
        if (message.contains("ux_category")) {
            throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
        }
        if (message.contains("ux_tag")) {
            throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
        }
    }
}