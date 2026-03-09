# spring-boot-starter v2

与 [spring-boot-starter v1](https://github.com/deepraining/spring-boot-starter) 的不同点是使用 [mybatis-plus](https://baomidou.com/) 作为数据库驱动层

- `pro-common`: 通用代码
- `pro-mp`: [mybatis-plus-generator](https://baomidou.com/pages/779a6e/) 生成代码
- `pro-admin`: 使用 JWT 保持登陆状态的后台管理应用，包括基于角色的访问控制（RBAC），示例前端项目 [sbs-admin-web](https://github.com/deepraining/sbs-admin-web)
- `pro-front`: 使用 Session-Cookie 保持登陆状态的前端应用
- `pro-dds`: 使用 [dynamic-datasource](https://github.com/baomidou/dynamic-datasource) 的MySql多数据源支持

## 其他说明

- 因为 `mybatis-plus-generator` 源代码里的模版文件是 `CRLF` 换行符，所以复制到项目中统一为 `LF` 换行符
- MySql多数据源项目中，非 `primary` 数据源的代码，需要添加 `@DS("non-primary")`，如果使用 `mybatis-plus-generator` 生成代码，就需要修改`mapper`与`service`模版文件，[示例查看](./pro-dds-mp3/src/main/resources/tpl_modify) 

#### 在 MySql 中，如果数据表名或字段名是关键字，可以如下设置

在`application.yml`配置文件中，设置执行mysql语句时都用反引号包裹

```
mybatis-plus:
  global-config:
    db-config:
      table-format: '`%s`'
      column-format: '`%s`'
```

生成代码时，`entity`加上`@TableField`注解

```
.strategyConfig(builder -> {
  builder
    .entityBuilder()
    .enableTableFieldAnnotation();
})
```

修改`entity.java.ftl`模版文件中的`@TableField`（`keepGlobalFormat`默认为`false`，不使用全局配置）

```
@TableField(...)

==>

@TableField(..., keepGlobalFormat = true)
```
