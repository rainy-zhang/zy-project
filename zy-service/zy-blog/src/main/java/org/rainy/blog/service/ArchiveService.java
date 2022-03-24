package org.rainy.blog.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.ArchiveDto;
import org.rainy.blog.entity.Archive;
import org.rainy.blog.repository.ArchiveRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveArticleService archiveArticleService;

    public ArchiveService(ArchiveRepository archiveRepository, ArchiveArticleService archiveArticleService) {
        this.archiveRepository = archiveRepository;
        this.archiveArticleService = archiveArticleService;
    }

    public PageResult<ArchiveDto> archivePage(PageQuery pageQuery) {
        BeanValidator.validate(pageQuery, ValidateGroups.SELECT.class);
        Page<Archive> page = archiveRepository.findAll(pageQuery.convert());
        Page<ArchiveDto> archivePage = page.map(archive -> {
            Integer archiveId = archive.getId();
            List<Integer> articleIds = archiveArticleService.findArticleIdsByArchiveId(archiveId);
            return ArchiveDto.builder()
                    .id(archiveId)
                    .name(archive.getName())
                    .description(archive.getDescription())
                    .articleIds(articleIds)
                    .articleCount(articleIds.size())
                    .build();
        });
        return PageResult.of(archivePage);
    }

}
