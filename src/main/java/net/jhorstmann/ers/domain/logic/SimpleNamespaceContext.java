package net.jhorstmann.ers.domain.logic;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

import static java.util.Arrays.asList;

class SimpleNamespaceContext implements NamespaceContext {

    private final String prefix;
    private final String ns;

    SimpleNamespaceContext(String prefix, String ns) {
        this.prefix = prefix;
        this.ns = ns;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return this.prefix.equals(prefix) ? this.ns : null;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return this.ns.equals(namespaceURI) ? this.prefix : null;
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        return asList(this.prefix).iterator();
    }
}
