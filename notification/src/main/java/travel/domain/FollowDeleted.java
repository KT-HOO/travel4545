package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import travel.infra.AbstractEvent;

@Data
public class FollowDeleted extends AbstractEvent {

    private Long id;
    private Long fromId;
    private Long toId;
}
