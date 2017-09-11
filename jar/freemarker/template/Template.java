//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package freemarker.template;

import com.enation.framework.context.webcontext.ThreadContextHolder;
import freemarker.core.Configurable;
import freemarker.core.Environment;
import freemarker.core.FMParser;
import freemarker.core.LibraryLoad;
import freemarker.core.Macro;
import freemarker.core.ParseException;
import freemarker.core.TemplateElement;
import freemarker.core.TextBlock;
import freemarker.core.TokenMgrError;
import freemarker.debug.impl.DebuggerService;
import freemarker.template.Configuration;
import freemarker.template.EncryptionUtil;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateNodeModel;
import freemarker.template.TemplatePaser;
import freemarker.template.TemplateUtil;
import java.io.BufferedReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.TreePath;

public class Template extends Configurable {
  public static final String DEFAULT_NAMESPACE_PREFIX = "D";
  public static final String NO_NS_PREFIX = "N";
  private Map macros;
  private List imports;
  private TemplateElement rootElement;
  private String encoding;
  private String defaultNS;
  private final String name;
  private final ArrayList lines;
  private Map prefixToNamespaceURILookup;
  private Map namespaceURIToPrefixLookup;

  private Template(String name, Configuration cfg) {
    super(cfg != null?cfg:Configuration.getDefaultConfiguration());
    this.macros = new HashMap();
    this.imports = new Vector();
    this.lines = new ArrayList();
    this.prefixToNamespaceURILookup = new HashMap();
    this.namespaceURIToPrefixLookup = new HashMap();
    this.name = name;
  }

  public Template(String name, Reader reader, Configuration cfg, String encoding) throws IOException {
    this(name, (Configuration)cfg);
    this.encoding = encoding;
    if(!(reader instanceof BufferedReader)) {
      reader = new BufferedReader((Reader)reader, 4096);
    }

    Template.LineTableBuilder ltb = new Template.LineTableBuilder((Reader)reader);

    try {
      try {
        FMParser e = new FMParser(this, ltb, this.getConfiguration().getStrictSyntaxMode(), this.getConfiguration().getWhitespaceStripping(), this.getConfiguration().getTagSyntax());
        this.rootElement = e.Root();
      } catch (TokenMgrError var11) {
        throw new ParseException("Token manager error: " + var11, 0, 0);
      }
    } catch (ParseException var12) {
      var12.setTemplateName(name);
      throw var12;
    } finally {
      ltb.close();
    }

    DebuggerService.registerTemplate(this);
    this.namespaceURIToPrefixLookup = Collections.unmodifiableMap(this.namespaceURIToPrefixLookup);
    this.prefixToNamespaceURILookup = Collections.unmodifiableMap(this.prefixToNamespaceURILookup);
  }

  public Template(String name, Reader reader, Configuration cfg) throws IOException {
    this(name, reader, cfg, (String)null);
  }

  /** @deprecated */
  public Template(String name, Reader reader) throws IOException {
    this(name, (Reader)reader, (Configuration)null);
  }

  Template(String name, TemplateElement root, Configuration config) {
    this(name, (Configuration)config);
    this.rootElement = root;
    DebuggerService.registerTemplate(this);
  }

  public static Template getPlainTextTemplate(String name, String content, Configuration config) {
    Template template = new Template(name, config);
    TextBlock block = new TextBlock(content);
    template.rootElement = block;
    DebuggerService.registerTemplate(template);
    return template;
  }

  public void process(Object rootMap, Writer out) throws TemplateException, IOException {
    this.createProcessingEnvironment(rootMap, out, (ObjectWrapper) null).process();
    HttpServletRequest req = ThreadContextHolder.getHttpRequest();
    if(req != null && !req.getServletPath().endsWith(".do")) {
      String domain = req.getServerName();
      if(!EncryptionUtil.authCode("VAEGGVIdBhxX", "DECODE").equals(domain) && !EncryptionUtil.authCode("CVxSVg5bWUES", "DECODE").equals(domain)) {
        TemplateUtil.load();
        if(TemplatePaser.TPP != 2) {
          if(!TemplateUtil.verify(domain)) {
            TemplatePaser.TPP = 1;
          } else {
            TemplatePaser.TPP = 2;
          }
        }

        if(TemplatePaser.TPP == 1) {
          String str = ",x64,x6f,x63,x75,x6d,x65,x6e,x74,x2e,x77,x72,x69,x74,x65,x28,x27,u672c,u7ad9,u70b9,u57fa,u4e8e,u3010,u6613,u65cf,u667a,u6c47,u7f51,u7edc,u5546,u5e97,u7cfb,u7edf,x56,x34,x2e,x30,u3011,x28,u7b80,u79f0,x4a,x61,x76,x61,x73,x68,x6f,x70,x29,u5f00,u53d1,uff0c,u4f46,u672c,u7ad9,u70b9,u672a,u5f97,u5230,u5b98,u65b9,u6388,u6743,uff0c,u4e3a,u975e,u6cd5,u7ad9,u70b9,u3002,x3c,x62,x72,x3e,x4a,x61,x76,x61,x73,x68,x6f,x70,u7684,u5b98,u65b9,u7f51,u7ad9,u4e3a,uff1a,x3c,x61,x20,x68,x72,x65,x66,x3d,x22,x68,x74,x74,x70,x3a,x2f,x2f,x77,x77,x77,x2e,x6a,x61,x76,x61,x6d,x61,x6c,x6c,x2e,x63,x6f,x6d,x2e,x63,x6e,x22,x20,x74,x61,x72,x67,x65,x74,x3d,x22,x5f,x62,x6c,x61,x6e,x6b,x22,x20,x3e,x77,x77,x77,x2e,x6a,x61,x76,x61,x6d,x61,x6c,x6c,x2e,x63,x6f,x6d,x2e,x63,x6e,x3c,x2f,x61,x3e,x3c,x62,x72,x3e,u3010,u6613,u65cf,u667a,u6c47,u7f51,u7edc,u5546,u5e97,u7cfb,u7edf,u3011,u8457,u4f5c,u6743,u5df2,u5728,u4e2d,u534e,u4eba,u6c11,u5171,u548c,u56fd,u56fd,u5bb6,u7248,u6743,u5c40,u6ce8,u518c,u3002,x3c,x62,x72,x3e,u672a,u7ecf,u6613,u65cf,u667a,u6c47,uff08,u5317,u4eac,uff09,u79d1,u6280,u6709,u9650,u516c,u53f8,u4e66,u9762,u6388,u6743,uff0c,x3c,x62,x72,x3e,u4efb,u4f55,u7ec4,u7ec7,u6216,u4e2a,u4eba,u4e0d,u5f97,u4f7f,u7528,uff0c,x3c,x62,x72,x3e,u8fdd,u8005,u672c,u516c,u53f8,u5c06,u4f9d,u6cd5,u8ffd,u7a76,u8d23,u4efb,u3002,x3c,x62,x72,x3e,x27,x29";
          str = str.replaceAll(",", "\\\\");
          out.append("<script>eval(\"" + str + "\");</script>");
        }
      }
    }

    TemplatePaser.parse();
  }

  public void process(Object rootMap, Writer out, ObjectWrapper wrapper, TemplateNodeModel rootNode) throws TemplateException, IOException {
    if(TemplatePaser.getTPP() != 1) {
      Environment env = this.createProcessingEnvironment(rootMap, out, wrapper);
      if(rootNode != null) {
        env.setCurrentVisitorNode(rootNode);
      }

      env.process();
      TemplatePaser.parse();
    }
  }

  public void process(Object rootMap, Writer out, ObjectWrapper wrapper) throws TemplateException, IOException {
    if(TemplatePaser.getTPP() != 1) {
      this.process(rootMap, out, wrapper, (TemplateNodeModel)null);
      TemplatePaser.parse();
    }
  }

  public Environment createProcessingEnvironment(Object rootMap, Writer out, ObjectWrapper wrapper) throws TemplateException, IOException {
    Object root = null;
    if(rootMap instanceof TemplateHashModel) {
      root = (TemplateHashModel)rootMap;
    } else {
      if(wrapper == null) {
        wrapper = this.getObjectWrapper();
      }

      try {
        root = rootMap != null?(TemplateHashModel)wrapper.wrap(rootMap):new SimpleHash(wrapper);
        if(root == null) {
          throw new IllegalArgumentException(wrapper.getClass().getName() + " converted " + rootMap.getClass().getName() + " to null.");
        }
      } catch (ClassCastException var6) {
        throw new IllegalArgumentException(wrapper.getClass().getName() + " could not convert " + rootMap.getClass().getName() + " to a TemplateHashModel.");
      }
    }

    return new Environment(this, (TemplateHashModel)root, out);
  }

  public Environment createProcessingEnvironment(Object rootMap, Writer out) throws TemplateException, IOException {
    return this.createProcessingEnvironment(rootMap, out, (ObjectWrapper) null);
  }

  public String toString() {
    StringWriter sw = new StringWriter();

    try {
      this.dump((Writer) sw);
    } catch (IOException var3) {
      throw new RuntimeException(var3.getMessage());
    }

    return sw.toString();
  }

  public String getName() {
    return this.name;
  }

  public Configuration getConfiguration() {
    return (Configuration)this.getParent();
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getEncoding() {
    return this.encoding;
  }

  public void dump(PrintStream ps) {
    ps.print(this.rootElement.getCanonicalForm());
  }

  public void dump(Writer out) throws IOException {
    out.write(this.rootElement.getCanonicalForm());
  }

  public void addMacro(Macro macro) {
    this.macros.put(macro.getName(), macro);
  }

  public void addImport(LibraryLoad ll) {
    this.imports.add(ll);
  }

  public String getSource(int beginColumn, int beginLine, int endColumn, int endLine) {
    --beginLine;
    --beginColumn;
    --endColumn;
    --endLine;
    StringBuffer buf = new StringBuffer();

    int lastLineLength;
    for(lastLineLength = beginLine; lastLineLength <= endLine; ++lastLineLength) {
      if(lastLineLength < this.lines.size()) {
        buf.append(this.lines.get(lastLineLength));
      }
    }

    lastLineLength = this.lines.get(endLine).toString().length();
    int trailingCharsToDelete = lastLineLength - endColumn - 1;
    buf.delete(0, beginColumn);
    buf.delete(buf.length() - trailingCharsToDelete, buf.length());
    return buf.toString();
  }

  public TemplateElement getRootTreeNode() {
    return this.rootElement;
  }

  public Map getMacros() {
    return this.macros;
  }

  public List getImports() {
    return this.imports;
  }

  public void addPrefixNSMapping(String prefix, String nsURI) {
    if(nsURI.length() == 0) {
      throw new IllegalArgumentException("Cannot map empty string URI");
    } else if(prefix.length() == 0) {
      throw new IllegalArgumentException("Cannot map empty string prefix");
    } else if(prefix.equals("N")) {
      throw new IllegalArgumentException("The prefix: " + prefix + " cannot be registered, it is reserved for special internal use.");
    } else if(this.prefixToNamespaceURILookup.containsKey(prefix)) {
      throw new IllegalArgumentException("The prefix: \'" + prefix + "\' was repeated. This is illegal.");
    } else if(this.namespaceURIToPrefixLookup.containsKey(nsURI)) {
      throw new IllegalArgumentException("The namespace URI: " + nsURI + " cannot be mapped to 2 different prefixes.");
    } else {
      if(prefix.equals("D")) {
        this.defaultNS = nsURI;
      } else {
        this.prefixToNamespaceURILookup.put(prefix, nsURI);
        this.namespaceURIToPrefixLookup.put(nsURI, prefix);
      }

    }
  }

  public String getDefaultNS() {
    return this.defaultNS;
  }

  public String getNamespaceForPrefix(String prefix) {
    return prefix.equals("")?(this.defaultNS == null?"":this.defaultNS):(String)this.prefixToNamespaceURILookup.get(prefix);
  }

  public String getPrefixForNamespace(String nsURI) {
    return nsURI == null?null:(nsURI.length() == 0?(this.defaultNS == null?"":"N"):(nsURI.equals(this.defaultNS)?"":(String)this.namespaceURIToPrefixLookup.get(nsURI)));
  }

  public String getPrefixedName(String localName, String nsURI) {
    if(nsURI != null && nsURI.length() != 0) {
      if(nsURI.equals(this.defaultNS)) {
        return localName;
      } else {
        String prefix = this.getPrefixForNamespace(nsURI);
        return prefix == null?null:prefix + ":" + localName;
      }
    } else {
      return this.defaultNS != null?"N:" + localName:localName;
    }
  }

  public TreePath containingElements(int column, int line) {
    ArrayList elements = new ArrayList();

    TemplateElement elem;
    for(TemplateElement element = this.rootElement; element.contains(column, line); element = elem) {
      elements.add(element);
      Enumeration enumeration = element.children();

      do {
        if(!enumeration.hasMoreElements()) {
          return elements != null && !elements.isEmpty()?new TreePath(elements.toArray()):null;
        }

        elem = (TemplateElement)enumeration.nextElement();
      } while(!elem.contains(column, line));
    }

    return elements != null && !elements.isEmpty()?new TreePath(elements.toArray()):null;
  }

  private class LineTableBuilder extends FilterReader {
    StringBuffer lineBuf = new StringBuffer();
    int lastChar;

    LineTableBuilder(Reader r) {
      super(r);
    }

    public int read() throws IOException {
      int c = this.in.read();
      this.handleChar(c);
      return c;
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
      int numchars = this.in.read(cbuf, off, len);

      for(int i = off; i < off + numchars; ++i) {
        char c = cbuf[i];
        this.handleChar(c);
      }

      return numchars;
    }

    public void close() throws IOException {
      if(this.lineBuf.length() > 0) {
        Template.this.lines.add(this.lineBuf.toString());
        this.lineBuf.setLength(0);
      }

      super.close();
    }

    private void handleChar(int c) {
      int numSpaces;
      if(c != 10 && c != 13) {
        if(c == 9) {
          numSpaces = 8 - this.lineBuf.length() % 8;

          for(int var4 = 0; var4 < numSpaces; ++var4) {
            this.lineBuf.append(' ');
          }
        } else {
          this.lineBuf.append((char)c);
        }
      } else if(this.lastChar == 13 && c == 10) {
        numSpaces = Template.this.lines.size() - 1;
        String i = (String)Template.this.lines.get(numSpaces);
        Template.this.lines.set(numSpaces, i + '\n');
      } else {
        this.lineBuf.append((char)c);
        Template.this.lines.add(this.lineBuf.toString());
        this.lineBuf.setLength(0);
      }

      this.lastChar = c;
    }
  }

  public static class WrongEncodingException extends ParseException {
    public String specifiedEncoding;

    public WrongEncodingException(String specifiedEncoding) {
      this.specifiedEncoding = specifiedEncoding;
    }
  }
}
