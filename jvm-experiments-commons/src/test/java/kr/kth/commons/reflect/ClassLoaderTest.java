package kr.kth.commons.reflect;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * kr.kth.commons.reflect.ClassLoaderTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 21
 */
@Slf4j
public class ClassLoaderTest {

    static public class TestClass {
        public String name;

        public String toString() {
            return name;
        }
    }

    @Test
    public void differentClassLoader() throws Exception {
        ClassLoader testClassLoader = new ClassLoader() {
            @Override
            protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                Class c = findLoadedClass(name);
                if (c != null) return c;
                if (name.startsWith("java.")) return super.loadClass(name, resolve);
                if (!name.equals("kr.kth.commons.reflect.ClassLoaderTest$TestClass"))
                    throw new ClassNotFoundException("Class not found on purpose: " + name);
                ByteArrayOutputStream output = new ByteArrayOutputStream(32 * 1024);
                InputStream input = ClassLoaderTest.class.getResourceAsStream("/" + name.replace('.', '/') + ".class");
                if (input == null) return null;
                try {
                    byte[] buffer = new byte[4096];
                    int total = 0;
                    while (true) {
                        int length = input.read(buffer, 0, buffer.length);
                        if (length == -1) break;
                        output.write(buffer, 0, length);
                    }
                } catch (IOException ex) {
                    throw new ClassNotFoundException("Error reading class file.", ex);
                } finally {
                    try {
                        input.close();
                    } catch (IOException ignored) {
                    }
                }
                byte[] buffer = output.toByteArray();
                return defineClass(name, buffer, 0, buffer.length);
            }
        };
        Class testClass = testClassLoader.loadClass("kr.kth.commons.reflect.ClassLoaderTest$TestClass");
        Object testObject = testClass.newInstance();
        Assert.assertNotNull(testObject);

        FieldAccess access = FieldAccess.get(testObject.getClass());
        access.set(testObject, "name", "first");
        Assert.assertEquals("first", testObject.toString());
        Assert.assertEquals("first", access.get(testObject, "name"));
    }
}
