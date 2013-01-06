package org.scala.impatient

import kr.kth.commons.compress.GZipCompressor
import kr.kth.commons.tools.StringTool
import org.junit.{Assert, Test}
import kr.kth.commons.slf4j.Logging

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 2
 */
class FunctionTest extends Logging {

	val Text = "동해물과 백두산이 마르고 닳도록"

	@Test
	def compressTest() {
		log.debug("압축 테스트 시작")

		val compressor = new GZipCompressor
		val bytes = compressor.compress(StringTool.getUtf8Bytes(Text))
		val decompressed = compressor.decompress(bytes)
		val decompressedText = StringTool.getUtf8String(decompressed)

		Assert.assertEquals(Text, decompressedText)
	}
}
