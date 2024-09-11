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
public class MemberModelViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MemberModelRepository memberModelRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMemberCreated_then_CREATE_1(
        @Payload MemberCreated memberCreated
    ) {
        try {
            if (!memberCreated.validate()) return;

            // view 객체 생성
            MemberModel memberModel = new MemberModel();
            // view 객체에 이벤트의 Value 를 set 함
            memberModel.setId(memberCreated.getId());
            memberModel.setName(memberCreated.getName());
            memberModel.setOauthId(memberCreated.getOauthId());
            memberModel.setEmail(memberCreated.getEmail());
            memberModel.setTokenAmount(memberCreated.getTokenAmount());
            // view 레파지 토리에 save
            memberModelRepository.save(memberModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
