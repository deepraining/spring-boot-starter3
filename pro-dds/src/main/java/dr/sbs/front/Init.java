package dr.sbs.front;

import dr.sbs.common.util.SbsCacheKeyUtil;
import dr.sbs.common.util.SbsIdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements ApplicationRunner {
  @Value("${app.workerId}")
  private long workerId;

  @Value("${app.dataCenterId}")
  private long dataCenterId;

  @Value("${spring.profiles.active}")
  private String springProfilesActive;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // Init SbsIdUtil
    SbsIdUtil.init(workerId, dataCenterId);

    // cache env
    SbsCacheKeyUtil.ENV = springProfilesActive;

    // app env
    AppEnv.isLocalDevEnv = springProfilesActive.equals("dev");
    AppEnv.isOnlineTestEnv = springProfilesActive.equals("test");
    AppEnv.isOnlineProdEnv = springProfilesActive.equals("prod");
  }
}
