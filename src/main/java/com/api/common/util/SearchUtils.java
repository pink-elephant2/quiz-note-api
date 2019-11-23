package com.api.common.util;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class SearchUtils {
	public static final String LIKE_ESCAPE = "\\";
	public static final String AIMAI_ASTERISK_AFTER = "%";
	public static final String AIMAI_QUESTION_AFTER = "_";

	public static String escapeLike(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;

		} else {
			String escaped = value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");

			return escaped;
		}
	}

	// TODO エラー
//	public static String createLikeValue(String value) {
//		return createLikeValue(value, LikeSearchOption.CONTAIN);
//	}

	// TODO エラー
//	public static String createLikeValue(String value, LikeSearchOption option) {
//       if(StringUtils.isEmpty(value)) {
//          return value;
//
//       } else {
//          String like = escapeLike(value);
//          switch(1.$SwitchMap$jp$co$aucnet$common$util$SearchUtils$LikeSearchOption[option.ordinal()]) {
//
//          case 1:
//             like = "%" + like + "%";
//             break;
//
//          case 2:
//             like = like + "%";
//             break;
//
//          case 3:
//             like = "%" + like;
//
//
//
//          }
//
//          return like;
//       }
//    }

	// TODO エラー
//	public static String createLikeValueUpperCase(String value) {
//		return StringUtils.isEmpty(value) ? value : createLikeValue(value.toUpperCase(), LikeSearchOption.CONTAIN);
//	}

	@Deprecated
	public static Optional<String> createPageOrderSql(Sort sort) {
		Optional result = Optional.empty();

		if (sort != null && sort.isSorted()) {
			String orderSql = (String) StreamSupport.stream(sort.spliterator(), false).map((order) -> {
				return order.getProperty() + " " + order.getDirection().toString();

			}).filter(Objects::nonNull).collect(Collectors.joining(","));
			result = Optional.of(orderSql);
		}
		return result;
	}

	public static Optional<String> createPageOrderSql(Sort sort, Map<String, String> param2Db) {
		Optional result = Optional.empty();

		if (sort != null) {
			String orderSql = (String) StreamSupport.stream(sort.spliterator(), false).map((order) -> {
				String columnName = order.getProperty();

				if (param2Db != null && param2Db.containsKey(columnName)) {
					columnName = (String) param2Db.get(columnName);
					return columnName + " " + order.getDirection().toString();
				} else {
					return null;
				}
			}).filter(Objects::nonNull).collect(Collectors.joining(","));
			result = Optional.of(orderSql);
		}

		return result;
	}
}
