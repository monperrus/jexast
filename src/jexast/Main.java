package jexast;

import java.util.Arrays;

import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;

/** Demonstrates how to use {@link AstExtractor} */
public class Main {

  public static void main(String[] args) throws Throwable {
    CompilationUnitDeclaration[] units = AstExtractor.createAST("./src/jexast/AstCompiler.java");
    System.out.println(Arrays.toString(units));
  }

}
