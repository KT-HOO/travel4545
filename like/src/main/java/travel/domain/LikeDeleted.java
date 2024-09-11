package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class LikeDeleted extends AbstractEvent {

    private Long id;
    private Long memberId;
    private Long planId;
    private Long planOwnerId;

    public LikeDeleted(Like aggregate) {
        super(aggregate);
    }

    public LikeDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
