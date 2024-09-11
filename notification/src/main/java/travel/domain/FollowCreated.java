package travel.domain;

import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

@Data
@ToString
public class FollowCreated extends AbstractEvent {

    private Long id;
    private Long fromId;
    private Long toId;
}
