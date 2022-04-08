package org.rainy.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainy.project.dto.ArchiveDto;
import org.rainy.project.service.ArchiveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/archive")
public class ArchiveController {

    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @PostMapping(value = "/archives")
    public List<ArchiveDto> archives() {
        return archiveService.archives();
    }

}
