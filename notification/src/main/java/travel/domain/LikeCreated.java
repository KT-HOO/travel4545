package travel.domain;

import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

@Data
@ToString
public class LikeCreated extends AbstractEvent {

    private Long id;
    private Long memberId;
    private Long planId;
    private Long planOwnerId;
}
