package com.common.service.contract;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface chung định nghĩa các phương thức nghiệp vụ CRUD cơ bản và phân trang.<br/>
 * Phục vụ cả các implementation cho SQL (JPA) và NoSQL (MongoDB).<br/>
 * Created at 19/06/2026
 *
 * @param <TEntity> Kiểu dữ liệu thực thể (Entity/Document)
 * @param <TId>     Kiểu dữ liệu khóa chính (ID)
 * @author txhoan
 */
@Service
public interface IBaseService<TEntity, TId> {
    TEntity create(TEntity entity);

    void update(TEntity entity);

    void delete(TEntity entity);

    Optional<TEntity> findById(TId id);

    @Transactional(readOnly = true)
    TEntity findByIdOrThrow(TId id);
}
