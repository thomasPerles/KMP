package kPackage;

import java.io.Serializable;

public class Triple extends KModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KRelation link;
	private KModel source, destination;
	
	public Triple(KModel source, KRelation link, KModel destination) {
		this.source = source;
		this.link = link;
		this.destination = destination;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("\n(").append(source.simpleToString()).append(' ').append(link.simpleToString()).append(' ').append(destination.simpleToString()).append(')');
		return res.toString();
	}

	public KModel getSource() {
		return source;
	}

	public KRelation getLink() {
		return link;
	}

	public KModel getDestination() {
		return destination;
	}
	
	//v2
	public boolean equal(Triple t) {
		return this.source == t.getSource() && this.link == t.getLink() && this.destination == t.getDestination();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof Triple)) return false;
		Triple triple = (Triple) obj;
		return this.source.equals(triple.source) && this.link.equals(triple.link) && this.destination.equals(triple.destination);
	}
	
}