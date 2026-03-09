package dr.sbs.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import lombok.Data;

@Data
public class CommonPage<T> {
  private Integer pageNum;
  private Integer pageSize;
  private Integer pages;
  private Integer total;
  private List<T> list;

  public static <T> CommonPage<T> toPage(IPage<T> p) {
    CommonPage<T> result = new CommonPage<T>();

    result.setPages((int) p.getPages());
    result.setPageNum((int) p.getCurrent());
    result.setPageSize((int) p.getSize());
    result.setTotal((int) p.getTotal());
    result.setList(p.getRecords());

    return result;
  }
}
