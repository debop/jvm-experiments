package kr.kth.commons.compress

import kr.kth.commons.tools.StringTool
import org.junit.{Assert, Test}
import kr.kth.commons.slf4j.Logger

/**
 * kr.kth.commons.compress.CompressorTestSuite
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class CompressorTestSuite {

	val log = Logger[this.type]

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
