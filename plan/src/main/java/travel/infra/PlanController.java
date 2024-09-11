package travel.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/plans")
@Transactional
public class PlanController {

    @Autowired
    PlanRepository planRepository;

    @RequestMapping(
        value = "/plans/{id}//require",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Plan requireRecommendation(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /plan/requireRecommendation  called #####");
        Optional<Plan> optionalPlan = planRepository.findById(id);

        optionalPlan.orElseThrow(() -> new Exception("No Entity Found"));
        Plan plan = optionalPlan.get();
        plan.requireRecommendation();

        planRepository.save(plan);
        return plan;
    }
}
//>>> Clean Arch / Inbound Adaptor
