package dr.sbs.admin.dao;

import dr.sbs.mp.entity.AdminMenu;
import dr.sbs.mp.entity.AdminResource;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** 后台用户角色自定义Dao */
public interface AdminRoleDao {
  List<AdminMenu> getMenuList(@Param("roleId") Integer roleId);

  List<AdminResource> getResourceList(@Param("roleId") Integer roleId);
}
