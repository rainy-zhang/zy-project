package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.entity.Tag;
import org.rainy.blog.param.TagParam;
import org.rainy.blog.repository.TagRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.CommonStatus;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanExistException;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public PageResult<Tag> tags(PageQuery pageQuery) {
        BeanValidator.validate(pageQuery, ValidateGroups.SELECT.class);
        Page<Tag> tagPage = tagRepository.findAll(pageQuery.convert());
        return PageResult.of(tagPage);
    }

    public List<Tag> findByIds(List<Integer> ids) {
        Preconditions.checkNotNull(ids, "标签ID列表不能为空");
        return tagRepository.findAllById(ids);
    }

    public void save(TagParam tagParam) {
        BeanValidator.validate(tagParam, ValidateGroups.INSERT.class);
        if (tagRepository.findByName(tagParam.getName()) != null) {
            throw new BeanExistException("标签名称已存在");
        }
        Tag tag = tagParam.convert();
        tagRepository.save(tag);
    }

    public void update(TagParam tagParam) {
        BeanValidator.validate(tagParam, ValidateGroups.UPDATE.class);
        if (tagRepository.findByName(tagParam.getName()) != null) {
            throw new BeanExistException("标签名称已存在");
        }
        Tag tag = tagParam.convert();
        tagRepository.save(tag);
    }

    public void delete(Integer id) {
        Preconditions.checkNotNull(id, "标签id不能为空");
        tagRepository.deleteById(id);
    }

    public Long count() {
        Specification<Tag> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Tag.COLUMN.STATUS), CommonStatus.VALID.getCode());
        return tagRepository.count(specification);
    }
}
