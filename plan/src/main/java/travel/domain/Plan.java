package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.PlanApplication;
import travel.domain.PlanCreated;
import travel.domain.PlanDeleted;
import travel.domain.PlanUpdated;
import travel.domain.RecommendationCreated;

@Entity
@Table(name = "Plan_table")
@Data
//<<< DDD / Aggregate Root
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long memberId;

    private String location;

    private Date travelDate;

    private Integer budget;

    private Integer groupSize;

    private String details;

    private String aiRecommendation;

    @PostPersist
    public void onPostPersist() {
        RecommendationCreated recommendationCreated = new RecommendationCreated(
            this
        );
        recommendationCreated.publishAfterCommit();

        PlanCreated planCreated = new PlanCreated(this);
        planCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        PlanUpdated planUpdated = new PlanUpdated(this);
        planUpdated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        PlanDeleted planDeleted = new PlanDeleted(this);
        planDeleted.publishAfterCommit();
    }

    public static PlanRepository repository() {
        PlanRepository planRepository = PlanApplication.applicationContext.getBean(
            PlanRepository.class
        );
        return planRepository;
    }

    //<<< Clean Arch / Port Method
    public void requireRecommendation() {
        //implement business logic here:

        RecommendationRequired recommendationRequired = new RecommendationRequired(
            this
        );
        recommendationRequired.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void createRecommendation(TokenDecreased tokenDecreased) {
        //implement business logic here:

        /** Example 1:  new item 
        Plan plan = new Plan();
        repository().save(plan);

        RecommendationCreated recommendationCreated = new RecommendationCreated(plan);
        recommendationCreated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(tokenDecreased.get???()).ifPresent(plan->{
            
            plan // do something
            repository().save(plan);

            RecommendationCreated recommendationCreated = new RecommendationCreated(plan);
            recommendationCreated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
