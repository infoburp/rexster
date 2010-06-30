package com.tinkerpop.rexster.traversals;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Element;
import com.tinkerpop.blueprints.pgm.Vertex;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ElementJSONObject extends JSONObject {

    private final Object id;
    public static final String ID = "_id";
    public static final String TYPE = "_type";
    public static final String LABEL = "_label";
    public static final String VERTEX = "vertex";
    public static final String EDGE = "edge";
    public static final String OUT_V = "_outV";
    public static final String IN_V = "_inV";

    public ElementJSONObject(Element element) {
        this(element, null);
    }

    public ElementJSONObject(Element element, List<String> propertyKeys) {
        this.id = element.getId();
        if (element instanceof Vertex) {
            this.put(TYPE, VERTEX);
        } else {
            this.put(TYPE, EDGE);
        }
        if (null == propertyKeys) {
            this.put(ID, this.id);
            for (String key : element.getPropertyKeys()) {
                this.put(key, element.getProperty(key));
            }
            if (element instanceof Edge) {
                Edge edge = (Edge) element;
                this.put(LABEL, edge.getLabel());
                this.put(IN_V, edge.getInVertex().getId());
                this.put(OUT_V, edge.getOutVertex().getId());
            }
        } else {
            for (String key : propertyKeys) {
                if (key.equals(ID)) {
                    this.put(ID, this.id);
                } else if (element instanceof Edge && key.equals(LABEL)) {
                    Edge edge = (Edge) element;
                    this.put(LABEL, edge.getLabel());
                } else if (element instanceof Edge && key.equals(IN_V)) {
                    Edge edge = (Edge) element;
                    this.put(IN_V, edge.getInVertex().getId());
                } else if (element instanceof Edge && key.equals(OUT_V)) {
                    Edge edge = (Edge) element;
                    this.put(IN_V, edge.getOutVertex().getId());
                } else {
                    Object temp = element.getProperty(key);
                    if (null != temp) {
                        this.put(key, temp);
                    }
                }
            }
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public Object getId() {
        return this.id;
    }

    public boolean equals(Object object) {
        if (object instanceof ElementJSONObject)
            return ((ElementJSONObject) object).getId().equals(this.id);
        else
            return false;
    }
}
