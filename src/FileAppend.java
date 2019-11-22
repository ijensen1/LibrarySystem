import java.io.*;

public class FileAppend {
  FileWriter fw;
  BufferedWriter bw;
  PrintWriter out;
  public FileAppend(String filename) { 
    try {
      fw = new FileWriter(filename, true);
      bw = new BufferedWriter(fw);
      out = new PrintWriter(bw);
    } catch (IOException e) {
      System.out.println("Failed to load " + filename + "for appending");
    }
  }
  
  public void print(String data) {
    out.print(data);
  }
  public void println(String data) {
    out.println(data);
  }
  public void close() {
    try {
      out.close();
      bw.close();
      fw.close();
    } catch (IOException e) {
      System.out.println("Failed to close file. How'd you even do that anyway?");
    }
    
  }
}
