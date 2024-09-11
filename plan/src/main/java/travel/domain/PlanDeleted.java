package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PlanDeleted extends AbstractEvent {

    private Long id;
    private Long memberId;
    private String location;
    private Date travelDate;
    private Integer budget;
    private Integer groupSize;
    private String details;
    private String aiRecommendation;

    public PlanDeleted(Plan aggregate) {
        super(aggregate);
    }

    public PlanDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
