package travel.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import travel.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "memberModels",
    path = "memberModels"
)
public interface MemberModelRepository
    extends PagingAndSortingRepository<MemberModel, Long> {}
