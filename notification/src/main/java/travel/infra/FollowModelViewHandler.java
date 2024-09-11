package travel.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import travel.config.kafka.KafkaProcessor;
import travel.domain.*;

@Service
public class FollowModelViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private FollowModelRepository followModelRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFollowCreated_then_CREATE_1(
        @Payload FollowCreated followCreated
    ) {
        try {
            if (!followCreated.validate()) return;

            // view 객체 생성
            FollowModel followModel = new FollowModel();
            // view 객체에 이벤트의 Value 를 set 함
            followModel.setId(followCreated.getId());
            followModel.setFromId(followCreated.getFromId());
            followModel.setToId(followCreated.getToId());
            // view 레파지 토리에 save
            followModelRepository.save(followModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFollowDeleted_then_DELETE_1(
        @Payload FollowDeleted followDeleted
    ) {
        try {
            if (!followDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            followModelRepository.deleteById(followDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
