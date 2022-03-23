package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.UserDto;
import org.rainy.blog.entity.Message;
import org.rainy.blog.param.MessageParam;
import org.rainy.blog.repository.MessageRepository;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.CommonException;
import org.rainy.common.util.BeanValidator;
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
        // TODO: 调用permission服务创建用户 
        UserDto userDto = null;
        Message message = messageParam.convert();
        message.setUserId(userDto.getId());
        message.setOperator(userDto.getId());
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
