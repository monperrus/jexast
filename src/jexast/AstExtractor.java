package jexast;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;

/** Extracts an AST tree from a set of files.
 * 
 * Can be used as an object or through the static method {@link AstExtractor#createAST(File...)} 
 */
public class AstExtractor {

  private final Set<File> files = new HashSet<File>();

  public void addInputSource(File file) {
    files.add(file);
  }

  public CompilationUnitDeclaration[] run() throws Throwable {
    AstCompilerRequestor compiler = new AstCompilerRequestor();
    return compiler.compileSrc(files);
  }

  public static CompilationUnitDeclaration[] createAST(String... fileNames) throws Throwable {
    File[] files = new File[fileNames.length];
    for (int i=0; i<fileNames.length;i++) {
      files[i]=new File(fileNames[i]);
    }
    return createAST(files);
  }
  
  public static CompilationUnitDeclaration[] createAST(File... files) throws Throwable {
    AstExtractor extractor = new AstExtractor();
    for (File f : files) {
      extractor.addInputSource(f);
    }
    return extractor.run();
  }
}
