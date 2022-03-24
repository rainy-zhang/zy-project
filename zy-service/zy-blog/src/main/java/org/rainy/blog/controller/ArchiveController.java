package org.rainy.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.ArchiveDto;
import org.rainy.blog.service.ArchiveService;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/archive")
public class ArchiveController {

    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @PostMapping(value = "/archives")
    public PageResult<ArchiveDto> archives(@RequestBody PageQuery pageQuery) {
        return archiveService.archivePage(pageQuery);
    }

}
