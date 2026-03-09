package dr.sbs.admin.util;

import dr.sbs.mp.entity.AdminUser;

public class ResultFilter {
  public static AdminUser filterAdminUser(AdminUser adminUser) {
    adminUser.setPassword(null);
    return adminUser;
  }
}
