package com.product.api.repository;
import java.util.List;
import com.product.api.entity.Category;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository 
public interface RepoCategory extends JpaRepository<Category,Integer>{

    @Query(value = "SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> getAll();

    @Query(value = "SELECT * FROM category WHERE status = 1 ORDER BY category", nativeQuery = true)
    List<Category> findActive();

    @Query(value = "SELECT * FROM category WHERE parentCategoryId = :parentCategoryId", nativeQuery = true)
    List<Category> findChilds(@Param("parentCategoryId") Integer parentCategoryId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO category(category, tag, status, parentCategoryId) VALUES (:category, :tag, 1, :parentCategoryId)", nativeQuery = true)
    void create(@Param("category") String category, @Param("tag") String tag, @Param("parentCategoryId") Integer parentCategoryId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET category = :category, tag = :tag, parentCategoryId = :parentCategoryId WHERE category_id = :id", nativeQuery = true)
    void update(@Param("id") Integer id, @Param("category") String category, @Param("tag") String tag, @Param("parentCategoryId") Integer parentCategoryId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status = 1 WHERE category_id = :id", nativeQuery = true)
    void enable(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status = 0 WHERE category_id = :id", nativeQuery = true)
    void disable(@Param("id") Integer id);

    //Object findByStatusOrderByCategory(int i);

}
