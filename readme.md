Jexast
======

Jexast allows creating Java AST Nodes with Plain JDT. Jexast stands for "Extraction of Java ASTs".

**Jexast has been superseeded by [https://github.com/INRIA/spoon](Spoon). Please use Spoon instead in noclasspath mode.**

To get the AST of Java files, there is the famous JDT class [ASTParser](http://help.eclipse.org/indigo/topic/org.eclipse.jdt.doc.isv/reference/api/org/eclipse/jdt/core/dom/ASTParser.html).

```java
ASTParser parser = ASTParser.newParser(AST.JLS3);
parser.setSource(file.getContent().toCharArray());
parser.setKind(ASTParser.K_COMPILATION_UNIT);
CompilationUnit unit = parser.createAST(null);
```

However, it requires to embed a large part of Eclipse to get it compiling and running (*). Otherwise one gets errors, for instance: "The type org.eclipse.core.runtime.IProgressMonitor cannot be resolved. It is indirectly referenced from required .class". It always seemed strange to me that one needs all Eclipse Jar files just to get an AST, and I had good reasons to believe one can overcome this. First, because Eclipse JDT can compile Java code in [command line](http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftask-using_batch_compiler.htm), only using ''jdt.core''. Second, [Spoon](http://spoon.gforge.inria.fr/) can build an advanced Java AST only using ''jdt.core''.
My PhD student Benoit Cornu did a great job to extract the essence of AST extraction from Spoon. 

Jexast can be used in one line:
`CompilationUnitDeclaration[] units = AstExtractor.createAST("./src/jexast/AstCompiler.java");`

or with an object:

```java
AstExtractor extractor = new AstExtractor();
extractor.addInputSource(new File("foo.java"));
CompilationUnitDeclaration[] units =  extractor.run();
```

Feedback, clone and pull requests welcome.

--Benoit and Martin

(*) org.eclipse.core.contenttype.jar, org.eclipse.core.jobs.jar, org.eclipse.core.resources.jar, org.eclipse.core.runtime.jar, org.eclipse.equinox.common.jar, org.eclipse.equinox.preferences.jar, org.eclipse.jdt.core.jar, org.eclipse.osgi.jar



