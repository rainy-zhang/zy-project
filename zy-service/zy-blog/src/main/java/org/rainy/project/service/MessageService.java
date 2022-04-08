package org.rainy.project.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.project.entity.Message;
import org.rainy.project.param.MessageParam;
import org.rainy.project.repository.MessageRepository;
import org.rainy.project.constant.ValidateGroups;
import org.rainy.project.exception.BeanNotFoundException;
import org.rainy.project.exception.CommonException;
import org.rainy.project.util.BeanValidator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(MessageParam messageParam) {
        BeanValidator.validate(messageParam, ValidateGroups.INSERT.class);
        Message message = messageParam.convert();
        messageRepository.save(message);
    }

    public void update(MessageParam messageParam) {
        BeanValidator.validate(messageParam, ValidateGroups.UPDATE.class);
        Message message = messageParam.convert();
        Duration duration = Duration.between(message.getCreateTime(), LocalDateTime.now());
        if (duration.toMinutes() > 30) {
            throw new CommonException("留言发表超过30分钟，不允许更改");
        }
        messageRepository.save(message);
    }

    public void delete(Integer id) {
        Preconditions.checkNotNull(id, "消息ID不能为空");
        Message message = messageRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("消息已不存在"));
        Duration duration = Duration.between(message.getCreateTime(), LocalDateTime.now());
        if (duration.toMinutes() > 30) {
            throw new CommonException("留言发表超过30分钟，不允许删除");
        }
        messageRepository.deleteById(id);
    }

}
