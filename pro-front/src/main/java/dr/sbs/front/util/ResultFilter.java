package dr.sbs.front.util;

import dr.sbs.mp.entity.FrontUser;

public class ResultFilter {
  public static FrontUser filterFrontUser(FrontUser frontUser) {
    FrontUser returnFrontUser = new FrontUser();
    returnFrontUser.setId(frontUser.getId());
    returnFrontUser.setUsername(frontUser.getUsername());
    returnFrontUser.setEmail(frontUser.getEmail());
    return returnFrontUser;
  }
}
