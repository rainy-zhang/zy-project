package org.rainy.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.param.MessageParam;
import org.rainy.blog.service.MessageService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(value = "/save")
    public void save(@RequestBody MessageParam messageParam) {
        messageService.save(messageParam);
    }

    @PutMapping(value = "/update")
    public void update(@RequestBody MessageParam messageParam) {
        messageService.update(messageParam);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id) {
        messageService.delete(id);
    }

}
