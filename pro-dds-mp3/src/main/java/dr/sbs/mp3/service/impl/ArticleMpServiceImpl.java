package dr.sbs.mp3.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import dr.sbs.mp3.entity.Article;
import dr.sbs.mp3.mapper.ArticleMapper;
import dr.sbs.mp3.service.ArticleMpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Service
@DS("master3")
public class ArticleMpServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleMpService {

}
