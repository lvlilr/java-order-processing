import java.io.*;
public class RegressionTest {
 public static void main(String[] args) throws Exception {
 ByteArrayOutputStream bytes=new ByteArrayOutputStream(); PrintStream original=System.out; System.setOut(new PrintStream(bytes));
 OrderQueue a=new OrderQueue(); a.addOrder("N","Demo","-2x Phone",false);
 OrderQueue b=new OrderQueue(); b.addOrder("R","Demo","3x Phone, 3x Phone",false);
 OrderQueue c=new OrderQueue(); c.addOrder("C","Demo","5x Phone",false); c.cancelOrder("C");c.addOrder("D","Demo","1x Phone",false);
 System.setOut(original);String output=bytes.toString();
 if(output.contains("Order added: N")||output.contains("Order added: R")||!output.contains("Order added: D"))throw new AssertionError(output);
 System.out.println("PASS 3 inventory regression cases");
 }}
