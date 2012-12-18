package kr.kth.commons.period.tools;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
@Slf4j
public final class TimeTool {

	private TimeTool() {}

	// TODO : 구현 필요


	public static Date getNow() {
		return new Date();
	}

	public static Long getNowTime() {
		return new Date().getTime();
	}

	public static Timestamp getNowTimestamp() {
		return new Timestamp(getNowTime());
	}
}
