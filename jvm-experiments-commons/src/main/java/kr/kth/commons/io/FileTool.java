package kr.kth.commons.io;

import kr.kth.commons.parallelism.AsyncTaskTool;
import kr.kth.commons.tools.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 파일 관련 Tool
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 11
 */
@Slf4j
public class FileTool {

	private FileTool() {}

	public static Path createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
		return Files.createDirectory(dir, attrs);
	}

	public static Path createDirectories(Path dir, FileAttribute<?>... attrs) throws IOException {
		return Files.createDirectories(dir, attrs);
	}


	public static void copy(Path source, Path target, CopyOption... options) throws IOException {
		Files.copy(source, target, options);
	}

	public static Future<Void> copyAsync(final Path source,
	                                     final Path target,
	                                     final CopyOption... options) {
		return
			AsyncTaskTool.startNew(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					copy(source, target, options);
					return null;
				}
			});
	}

	public static void delete(Path path) throws IOException {
		Files.delete(path);
	}

	public static void deleteIfExists(Path path) throws IOException {
		if (exists(path))
			Files.deleteIfExists(path);
	}

	public static void deleteDirectory(Path directory, boolean deep) throws IOException {
		if (log.isDebugEnabled())
			log.debug("directory=[{}] 를 삭제합니다. deep=[{}]", directory, deep);

		if (!deep) {
			deleteIfExists(directory);
		} else {
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}
			});
		}
	}

	public static Future<Void> deleteDirectoryAsync(final Path directory, final boolean deep) {
		return
			AsyncTaskTool.startNew(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					deleteDirectory(directory, deep);
					return null;
				}
			});
	}

	public static boolean exists(Path path, LinkOption... linkOptions) {
		return Files.exists(path, linkOptions);
	}

	public static byte[] readAllBytes(Path path) throws IOException {
		return Files.readAllBytes(path);
	}

	public static Future<byte[]> readAllBytesAsync(final Path path) {
		return
			AsyncTaskTool.startNew(new Callable<byte[]>() {
				@Override
				public byte[] call() throws Exception {
					return readAllBytes(path);
				}
			});
	}

	public static List<String> readAllLines(Path path) throws IOException {
		return readAllLines(path, StringTool.UTF8);
	}

	public static List<String> readAllLines(Path path, Charset cs) throws IOException {
		return Files.readAllLines(path, cs);
	}

	public static Future<List<String>> readAllLinesAsync(final Path path) {
		return readAllLinesAsync(path, StringTool.UTF8);
	}

	public static Future<List<String>> readAllLinesAsync(final Path path, final Charset cs) {
		return
			AsyncTaskTool.startNew(new Callable<List<String>>() {
				@Override
				public List<String> call() throws Exception {
					return readAllLines(path, cs);
				}
			});
	}

	public static void write(Path target, byte[] bytes, OpenOption... openOptions) throws IOException {
		Files.write(target, bytes, openOptions);
	}

	public static void write(Path target,
	                         Iterable<CharSequence> lines,
	                         Charset cs,
	                         OpenOption... openOptions) throws IOException {
		Files.write(target, lines, cs, openOptions);
	}


	public static Future<Void> writeAsync(final Path target,
	                                      final byte[] bytes,
	                                      final OpenOption... openOptions) {
		return
			AsyncTaskTool.startNew(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					write(target, bytes, openOptions);
					return null;
				}
			});
	}

	@Async
	public static Future<Void> writeAsync(final Path target,
	                                      final Iterable<CharSequence> lines,
	                                      final Charset cs,
	                                      final OpenOption... openOptions) {
		return
			AsyncTaskTool.startNew(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					write(target, lines, cs, openOptions);
					return null;
				}
			});
	}
}
