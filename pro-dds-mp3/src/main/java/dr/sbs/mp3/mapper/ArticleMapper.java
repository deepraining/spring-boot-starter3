package dr.sbs.mp3.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import dr.sbs.mp3.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文章 Mapper 接口
 * </p>
 *
 * @author deepraining
 * @since 
 */
@DS("master3")
public interface ArticleMapper extends BaseMapper<Article> {

}
