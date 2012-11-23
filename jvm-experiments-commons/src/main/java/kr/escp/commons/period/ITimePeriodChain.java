package kr.escp.commons.period;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriodChain extends ITimePeriodContainer {

	ITimePeriod getFirst();

	ITimePeriod getLast();
}
