package travel.domain;

import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

@Data
@ToString
public class TokenDecreased extends AbstractEvent {

    private Long id;
    private String oauthId;
    private String name;
    private String email;
    private Integer tokenAmount;
}
