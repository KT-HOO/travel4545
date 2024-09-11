package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import travel.infra.AbstractEvent;

@Data
public class MemberCreated extends AbstractEvent {

    private Long id;
    private String oauthId;
    private String name;
    private String email;
    private Integer tokenAmount;
}
