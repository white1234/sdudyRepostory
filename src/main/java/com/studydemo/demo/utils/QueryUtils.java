package com.studydemo.demo.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.studydemo.demo.annotation.Query;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author czy
 * @describe 实体类转 QueryWrapper 构造条件
 * 支持常用类型（EQ、NOT_EQUAL、LIKE、LEFT LIKE、RIGHT LIKE、IN、NOT IN、BETWEEN、IS NULL、NOT NULL 等等）
 * IN、NOT IN、BETWEEN、NOT BETWEEN 支持数组、集合、字符串逗号分割
 * 排序 order by 关键字：sort，支持数组、集合、字符串逗号分割（建议建立公共查询类BaseQO存放这个属性）
 */
public class QueryUtils {
    private static String listErrorMsg = "between element cannot be less than two";
    private static String comma = ",";
    private static String sort = "sort";

    public static QueryWrapper buildQueryWrapper(Object qo) {
        QueryWrapper<Object> wrapper = Wrappers.query();

        try {
            List<Field> fields = fields(qo.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Query condition = field.getAnnotation(Query.class);
                if (sort.equals(field.getName())) {
                    List<Object> sorts = objToList(field.get(qo));
                    if (CollectionUtils.isNotEmpty(sorts)) {
                        sorts.forEach(item -> {
                            String[] split = item.toString().split("\\s+");
                            if (split.length == 1) {
                                wrapper.orderByAsc(split[split.length - 1]);
                            } else {
                                if (SqlKeyword.DESC.getSqlSegment().equals(split[1].toUpperCase())) {
                                    wrapper.orderByDesc(StringUtils.camelToUnderline(split[0]));
                                } else {
                                    wrapper.orderByAsc(StringUtils.camelToUnderline(split[0]));
                                }
                            }

                        });
                    }
                }
                if (Objects.nonNull(condition)) {
                    String fieldName = condition.field();
                    String like = condition.likes();
                    String name = StringUtils.camelToUnderline(StringUtils.isNotBlank(fieldName) ? fieldName : field.getName());
                    Object val = field.get(qo);

                     if (!Query.Type.IS_NULL.equals(condition.type()) && !Query.Type.NOT_NULL.equals(condition.type()) && Objects.isNull(val)) {
                        continue;
                    }

                    // 模糊多字段
                    if (StringUtils.isNotBlank(like)) {
                        String[] likes = like.split(comma);
                        Object finalVal = val;
                        wrapper.and(w -> {
                            for (String s : likes) {
                                w.like(StringUtils.camelToUnderline(s), finalVal).or();
                            }
                        });

                        continue;
                    }

                    // 参数集合，用于in、not in、between、not between 等等
                    List<Object> params = objToList(val);
                    switch (condition.type()) {
                        case EQ:
                            wrapper.eq(name, val);
                            break;
                        case GE:
                            wrapper.ge(name, val);
                            break;
                        case LT:
                            wrapper.lt(name, val);
                            break;
                        case LE:
                            wrapper.le(name, val);
                            break;
                        case LIKE:
                            wrapper.like(name, val);
                            break;
                        case LEFT_LIKE:
                            wrapper.likeLeft(name, val);
                            break;
                        case RIGHT_LIKE:
                            wrapper.likeRight(name, val);
                            break;
                        case NOT_LIKE:
                            wrapper.notLike(name, val);
                            break;
                        case IN:
                            wrapper.in(name, params);
                            break;
                        case NOT_IN:
                            wrapper.notIn(name, params);
                            break;
                        case NE:
                            wrapper.ne(name, val);
                            break;
                        case NOT_NULL:
                            wrapper.isNotNull(name);
                            break;
                        case IS_NULL:
                            wrapper.isNull(name);
                            break;
                        case BETWEEN:
                            verifyBetween(params);
                            wrapper.between(name, params.get(0), params.get(1));
                            break;
                        case NOT_BETWEEN:
                            verifyBetween(params);
                            wrapper.notBetween(name, params.get(0), params.get(1));
                            break;
                        default:
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wrapper;
    }

    /**
     * Object 转 List
     * 支持数组、集合、字符串逗号分割转List
     * @param val
     * @return
     */
    private static List<Object> objToList(Object val) {
        List<Object> between = null;
        if (val instanceof Collection) {
            between = new ArrayList((Collection<?>) val);
        } else if (val instanceof Object[]) {
            between = Arrays.asList((Object[]) val);
        } else if (val instanceof Map) {
            between = new ArrayList(((Map) val).values());
        } else if (val instanceof String) {
            between = Arrays.asList(((String) val).split(comma));
        }
        return between;
    }

    /**
     * 反射获取属性
     *
     * @param clazz
     * @param fields
     * @return
     */
    private static List<Field> fields(Class clazz, List<Field> fields) {
        if (!Objects.isNull(clazz)) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            fields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /**
     * 校验 between 参数，必须有2个元素
     * @param between
     */
    private static void verifyBetween(List<Object> between) {
        Assert.isTrue(between.size() >= 2, listErrorMsg);
    }

    /**
     * 校验 between 参数，必须有2个元素
     * @param between
     */
    private static void verifyBetween(String[] between) {
        Assert.isTrue(between.length >= 2, listErrorMsg);
    }
}