package com.common.service.impl;

import com.common.exception.NotFoundException;
import com.common.model.BaseModel;
import com.common.repository.BaseRepository;
import com.common.service.contract.IBaseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.util.*;

/**
 * Lớp implementation base service cho SQL JPA.<br/>
 * Hỗ trợ các tính năng như Xóa mềm, Lọc Specifications động và Dynamic Column selection.<br/>
 * Created at 10/06/2026, Updated at 19/06/2026
 *
 * @param <TEntity>     Kiểu dữ liệu Entity kế thừa từ BaseModel
 * @param <TId>         Kiểu dữ liệu khóa chính Entity
 * @param <TRepository> Kiểu dữ liệu Repository kế thừa từ BaseRepository
 * @author txhoan
 * @see <a href="../../../../../resources/docs/spring_structure/service-guide.md">BaseService Specification Guide</a>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseServiceImpl<
        TEntity extends BaseModel,
        TId,
        TRepository extends BaseRepository<TEntity, TId>>
        implements IBaseService<TEntity, TId> {

    TRepository repository;

    @NonFinal
    EntityManager entityManager;

    /**
     * Phân tích kiểu lớp (Class) của TEntity bằng Reflection thông qua Generic Superclass.<br/>
     * Dùng để phục vụ tìm kiếm động, lọc và phản xạ các thuộc tính String.<br/>
     * Created at 19/06/2026
     *
     * @return Đối tượng Class của TEntity
     */
    @SuppressWarnings("unchecked")
    protected Class<TEntity> getEntityClass() {
        Class<?> clazz = getClass();
        // Duyệt dọc theo chuỗi kế thừa đến khi gặp lớp cha trực tiếp của BaseServiceImpl
        while (clazz != null && clazz.getSuperclass() != Object.class) {
            var genericSuper = clazz.getGenericSuperclass();
            if (genericSuper instanceof java.lang.reflect.ParameterizedType type) {
                var rawType = type.getRawType();
                // Kiểm tra xem rawType có phải là lớp BaseServiceImpl hay không
                if (rawType instanceof Class<?> rawClass && BaseServiceImpl.class.isAssignableFrom(rawClass)) {
                    // Trả về đối số Generic đầu tiên (index 0 chính là TEntity)
                    return (Class<TEntity>) type.getActualTypeArguments()[0];
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new IllegalStateException("Không thể phân tích kiểu Entity class cho " + getClass().getName());
    }

    /**
     * Lấy tên người dùng hiện tại đang đăng nhập từ Security Context.<br/>
     * Created at 19/06/2026
     *
     * @return Tên người dùng hoặc "SYSTEM" nếu không tìm thấy thông tin xác thực
     */
    private String getCurrentAuditorName() {
        var authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication();
        // Kiểm tra xem đối tượng authentication đã xác thực thành công hay chưa
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "SYSTEM"; // Trả về SYSTEM mặc định nếu chạy ngầm hoặc chưa đăng nhập
    }

    /**
     * Tạo mới một bản ghi thực thể vào cơ sở dữ liệu.<br/>
     * Created at 10/06/2026
     *
     * @param entity Đối tượng thực thể cần lưu
     * @return Đối tượng thực thể sau khi được lưu
     */
    @Override
    @Transactional
    public TEntity create(TEntity entity) {
        log.info("Đang tạo thực thể: {}", entity);
        // Lưu và trả về thực thể
        return repository.save(entity);
    }

    /**
     * Cập nhật thông tin bản ghi thực thể đã tồn tại.<br/>
     * Created at 10/06/2026
     *
     * @param entity Đối tượng thực thể cần cập nhật
     */
    @Override
    @Transactional
    public void update(TEntity entity) {
        log.info("Đang cập nhật thực thể: {}", entity);
        // Lưu đè dữ liệu lên bản ghi cũ
        repository.save(entity);
    }

    /**
     * Xóa một bản ghi thực thể.<br/>
     * Tự động phát hiện kiểu đối tượng để áp dụng xóa mềm (Soft Delete)
     * hoặc xóa cứng (Hard Delete) vật lý trong DB.<br/>
     * Created at 10/06/2026, Updated soft-delete at 19/06/2026
     *
     * @param entity Đối tượng thực thể cần xóa
     */
    @Override
    @Transactional
    public void delete(TEntity entity) {
        log.info("Đang xóa thực thể: {}", entity);
        repository.delete(entity);
    }

    /**
     * Tìm kiếm bản ghi theo ID.<br/>
     * Bỏ qua không trả về các thực thể đã bị xóa mềm.<br/>
     * Created at 10/06/2026, Updated soft-delete filter at 19/06/2026
     *
     * @param id Khóa chính cần tìm
     * @return Optional chứa thực thể tương ứng nếu tìm thấy và chưa bị xóa mềm
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TEntity> findById(TId id) {
        log.info("Đang tìm kiếm thực thể theo id: {}", id);
        return repository.findById(id);
    }

    /**
     * Tìm kiếm bản ghi theo ID, ném ra NotFoundException nếu không tìm thấy hoặc đã xóa mềm.<br/>
     * Created at 10/06/2026, Updated soft-delete filter at 19/06/2026
     *
     * @param id Khóa chính cần tìm
     * @return Thực thể tương ứng
     * @throws NotFoundException nếu không tìm thấy hoặc đã bị xóa mềm
     */
    @Override
    @Transactional(readOnly = true)
    public TEntity findByIdOrThrow(TId id) {
        log.info("Đang tìm kiếm thực thể theo id hoặc throw: {}", id);
        return repository.findByIdOrThrow(id);
    }
}
