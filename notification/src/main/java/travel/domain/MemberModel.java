package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MemberModel_table")
@Data
public class MemberModel {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String oauthId;
    private String name;
    private String email;
    private Integer tokenAmount;
}
