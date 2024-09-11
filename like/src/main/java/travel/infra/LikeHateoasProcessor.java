package travel.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import travel.domain.*;

@Component
public class LikeHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Like>> {

    @Override
    public EntityModel<Like> process(EntityModel<Like> model) {
        return model;
    }
}
