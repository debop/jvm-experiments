package org.scala.impatient

import annotation.meta.beanGetter
import collection.mutable.ArrayBuffer
import io.Source
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kr.kth.commons.base.ValueObjectBase
import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}

/**
 * org.scala.impatient.AdvancedTypeTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 5.
 */
class AdvancedTypeTest extends Logging {

  class Document extends ValueObjectBase {
    @beanGetter private var title: String = _
    @beanGetter private var author: String = _

    def setTitle(title: String): this.type = {
      this.title = title;
      this
    }

    def setAuthor(author: String): this.type = {
      this.author = author;
      this
    }

    protected override def buildStringHelper() = {
      super.buildStringHelper()
        .add("title", title)
        .add("author", author)
    }
  }

  class Book extends Document {
    @beanGetter private var chapters: ArrayBuffer[String] = new ArrayBuffer[String]()

    def addChapter(chapter: String): this.type = {
      chapters += chapter;
      this
    }

    protected override def buildStringHelper() = {
      super.buildStringHelper()
        .add("chapters", chapters)
    }
  }

  @Test
  def fluentSetting() {
    var book = new Book().setTitle("scala").setAuthor("bae").addChapter("chapter 01").addChapter("chapter 02")
    log.debug("book=[{}]", book)
  }

  // Type Projections

  class Network {

    class Member(val name: String) {
      val contacts = new ArrayBuffer[Network#Member]
    }

    private val members = new ArrayBuffer[Network#Member]

    def join(name: String) = {
      val m = new Member(name)
      members += m
      m
    }
  }

  @Test
  def typeProjectionTest() {
    val chatter = new Network
    val myFace = new Network

    val fred = chatter.join("Fred")
    val barney = myFace.join("Barney")

    Assert.assertEquals(fred.getClass, barney.getClass)
    Assert.assertEquals(fred.contacts.getClass, barney.contacts.getClass)

    fred.contacts += barney

  }

  // Type Aliases

  class Book2 extends Book {

    // Type aliases
    type Index = scala.collection.mutable.HashMap[String, (Int, Int)]
  }

  // Abstract Types

  trait Reader {
    type Contents

    def read(filename: String): Contents
  }

  class StringReader extends Reader {
    type Contents = String

    def read(filename: String) = Source.fromFile(filename, "UTF-8").mkString
  }

  class ImageReader extends Reader {
    type Contents = BufferedImage

    def read(filename: String) = ImageIO.read(new File(filename))
  }

  trait GenericReader[C] {
    def read(filename: String): C
  }

  class GenericStringReader extends GenericReader[String] {
    def read(filename: String): String = Source.fromFile(filename, "UTF-8").mkString
  }

  class GenericImageReader extends GenericReader[BufferedImage] {
    def read(filename: String) = ImageIO.read(new File(filename))
  }


}
