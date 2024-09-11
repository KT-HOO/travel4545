package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PlanUpdated extends AbstractEvent {

    private Long id;
    private Long memberId;
    private String location;
    private Date travelDate;
    private Integer budget;
    private Integer groupSize;
    private String details;
    private String aiRecommendation;

    public PlanUpdated(Plan aggregate) {
        super(aggregate);
    }

    public PlanUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
