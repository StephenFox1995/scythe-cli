import com.stephenfox.scythe.Scythe;
import com.stephenfox.scythe.annotation.Option;

import java.util.Map;

public class Main {

  @Option(name = "--age", help = "The age of a person", type = Integer.class)
  @Option(name = "--gender", help = "The gender of the person")
  Object cliOptions;

  public static void main(String[] args) {
    final String[] arrrgs = new String[] {" --age", "1", "--gender", "male"};
    Map<String, Object> mappings = Scythe.cli(Main.class, arrrgs);
    System.out.println(mappings);
  }

  //  @Option(name="--age", help = "The age of a person")
  //  @SuppressWarnings("unused")
  //  @Option(name="--gender", help = "The gender of the person")
  public static void fakeMain(String age) {
    System.out.println(age);
  }
}
