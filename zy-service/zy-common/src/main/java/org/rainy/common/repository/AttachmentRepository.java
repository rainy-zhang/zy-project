package org.rainy.common.repository;

import org.rainy.common.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *
 * </p>
 *
 * @author wt1734
 * @date 2021/11/30 0030 15:23
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
