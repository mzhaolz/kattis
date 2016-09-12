package cs4150;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;
import java.nio.charset.StandardCharsets;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

public class Start {
  static final String TEST_STRING = "5 3\n2 7 1\n3 1 4\n1 5 9\n2 6 5\n9 7 3";
  static final String TEST_STRING_2 = "3 4\n3 1 2 40000\n3 4 2 1\n33 42 17 23";

  public static void main(String[] args) {
    test();
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public static void driver() {
    BitArrayNode.countUnique(getReader(TEST_STRING));
  }

  public static void test() {
    System.out.println(BitArrayNode.countUnique(getReader(TEST_STRING)));
    System.out.println(BitArrayNode.countUnique(getReader(TEST_STRING_2)));
  }

  public static BitArrayNode.InputReader getReader(String testString) {
    return new BitArrayNode.InputReader(new ByteArrayInputStream(
          testString.getBytes(StandardCharsets.US_ASCII)));
  }
}
