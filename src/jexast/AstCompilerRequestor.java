package jexast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.util.Util;

/** Provides an ICompilerRequestor for use with AstCompiler */
public class AstCompilerRequestor implements ICompilerRequestor {

  private final Main jdtMain = new Main(new PrintWriter(System.out), new PrintWriter(System.err), false);
  
	public static int JAVA_COMPLIANCE = 6;
	
	public CompilationUnitDeclaration[] compileSrc(Set<File> set)
			throws Exception {
		if(set.isEmpty()) {
      return new CompilationUnitDeclaration[]{};
    }
		List<String> args = new ArrayList<String>();
		args.add("-1." + JAVA_COMPLIANCE);
		args.add("-preserveAllLocals");
		args.add("-enableJavadoc");
		args.add("-noExit");
		ClassLoader currentClassLoader = ClassLoader.getSystemClassLoader();
		
		if(currentClassLoader instanceof URLClassLoader){
			URL[] urls = ((URLClassLoader) currentClassLoader).getURLs();
			if(urls!=null && urls.length>0){
				String classpath = ".";
				for (URL url : urls) {
					classpath+=File.pathSeparator+url.getFile();
				}
				if(classpath!=null){
					args.add("-cp");
					args.add(classpath);
				}
			}
		}
		
		args.add(".");

		jdtMain.configure(args.toArray(new String[0]));
		CompilationUnitDeclaration[] units = compileUnits(set);
		return units;
	}


	final PrintWriter out = new PrintWriter(System.out);

	/*
	 * Build the set of compilation source units
	 */
	public CompilationUnit[] createCompilationUnits(Set<File> set)
			throws Exception {
		CompilationUnit[] units = new CompilationUnit[set.size()];
		int i = 0;
		for (File stream : set) {
			InputStream in = new FileInputStream(stream);
			units[i] = new CompilationUnit(Util.getInputStreamAsCharArray(in,
					-1, null), stream.getPath(), null);
			in.close();
			i++;
		}
		return units;
	}

	
	public CompilationUnitDeclaration[] compileUnits(Set<File> set)
			throws Exception {
	  INameEnvironment environment=null;
		if(environment == null) {
      environment = jdtMain.getLibraryAccess();
    }
		AstCompiler batchCompiler = new AstCompiler(environment, jdtMain.getHandlingPolicy(),
		    jdtMain.options, this, jdtMain.getProblemFactory(), this.out, false);
		return batchCompiler.compileUnits(createCompilationUnits(set));
	}
	
	
	@Override
  public void acceptResult(CompilationResult result) {
	}
}
