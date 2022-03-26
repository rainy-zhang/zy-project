package org.rainy.blog.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.ArchiveDto;
import org.rainy.blog.entity.Archive;
import org.rainy.blog.repository.ArchiveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveArticleService archiveArticleService;

    public ArchiveService(ArchiveRepository archiveRepository, ArchiveArticleService archiveArticleService) {
        this.archiveRepository = archiveRepository;
        this.archiveArticleService = archiveArticleService;
    }

    public List<ArchiveDto> archives() {
        List<Archive> archives = archiveRepository.findAll();
        return archives.stream().map(archive -> {
            Integer archiveId = archive.getId();
            List<Integer> articleIds = archiveArticleService.findArticleIdsByArchiveId(archiveId);
            return ArchiveDto.builder()
                    .id(archiveId)
                    .name(archive.getName())
                    .description(archive.getDescription())
                    .articleIds(articleIds)
                    .articleCount(articleIds.size())
                    .build();
        }).collect(Collectors.toList());
    }

}
