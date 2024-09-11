package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class LikeCreated extends AbstractEvent {

    private Long id;
    private Long memberId;
    private Long planId;
    private Long planOwnerId;

    public LikeCreated(Like aggregate) {
        super(aggregate);
    }

    public LikeCreated() {
        super();
    }
}
//>>> DDD / Domain Event
