import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class LibraryManager {
  private Account[] accounts;
  private Library[] libraries;
  private static final int ERR_NOT_IN = -1;
  private static final int ERR_LIBRARY_NOT_FOUND = -2;
  public LibraryManager() {
      String[] libNames = Persistence.loadLibraryNames();
      libraries = new Library[libNames.length];
      for (int i = 0; i < libraries.length; i++) {
        libraries[i] = new Library(libNames[i]);
      }
      accounts = Persistence.loadAccounts();
  }
  public void close() {
    Persistence.saveToFile(accounts);
    for (Library lib : libraries) {
      try {
        lib.save();
      } catch (IOException e) {
        System.err.println("Error saving library data. ");
      }
    }
  }
  public int transfer(String libname1, String libname2, Borrowable item) {
      if (item.getInOut().equals("out")) {
          return LibraryManager.ERR_NOT_IN;
      }
      Library lib1 = null;
      Library lib2 = null;
      for (Library lib : libraries) {
          if (lib.getLibraryName().equals(libname1)) {
              lib1 = lib;
          }
      }
      if (lib1 == null) {
          return LibraryManager.ERR_LIBRARY_NOT_FOUND;
      }

      for (Library lib : libraries) {
          if (lib.getLibraryName().equals(libname2)) {
              lib2 = lib;
          }
      }
      if (lib2 == null) {
          return LibraryManager.ERR_LIBRARY_NOT_FOUND;
      }

      lib1.remove(item.getType(), item.getTitle(), item.getCreator());
      lib2.add(item.getTitle(), item.getTitle(), item.getCreator(), item.getGenre1(), item.getGenre2());
      return 0;
  }
}
