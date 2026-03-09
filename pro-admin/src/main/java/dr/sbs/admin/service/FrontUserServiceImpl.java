package dr.sbs.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.FrontUserCreateParam;
import dr.sbs.common.util.SbsIdUtil;
import dr.sbs.mp.entity.FrontUser;
import dr.sbs.mp.service.FrontUserMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** 后台前端用户管理Service实现类 */
@Service
public class FrontUserServiceImpl implements FrontUserService {
  @Autowired private FrontUserMpService frontUserMpService;

  @Override
  public Page<FrontUser> list(String searchKey, Integer pageSize, Integer pageNum) {
    Page<FrontUser> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);
    QueryWrapper<FrontUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("status", 1);
    queryWrapper.orderByDesc("id");
    if (!StringUtils.isEmpty(searchKey)) {
      queryWrapper.like("username", searchKey);
    }
    return frontUserMpService.page(page, queryWrapper);
  }

  @Override
  public boolean create(FrontUserCreateParam frontUserCreateParam) {
    FrontUser frontUser = new FrontUser();
    BeanUtils.copyProperties(frontUserCreateParam, frontUser);
    frontUser.setId(SbsIdUtil.nextId());
    return frontUserMpService.save(frontUser);
  }

  @Override
  public boolean update(Long id, FrontUserCreateParam frontUserCreateParam) {
    FrontUser frontUser = new FrontUser();
    BeanUtils.copyProperties(frontUserCreateParam, frontUser);
    frontUser.setId(id);
    // 不能更新用户名
    frontUser.setUsername(null);
    return frontUserMpService.updateById(frontUser);
  }

  @Override
  public boolean delete(Long id) {
    FrontUser frontUser = new FrontUser();
    frontUser.setId(id);
    frontUser.setStatus((byte) 0);
    return frontUserMpService.updateById(frontUser);
  }
}
