package kPackage;

public class KCompositeModel extends KModel {
	private KModel source, destination;
	private KRelation link;

	
	public KCompositeModel(KModel source, KRelation link,  KModel destination) {
		this.source = source;
		this.destination = destination;
		this.link = link;
	}

//	@Override
//	public String simpleToString() {
//		StringBuilder res = new StringBuilder();
//		res.append(source.simpleToString()).append(' ').append(link.simpleToString()).append(' ').append(destination.simpleToString());
//		return res.toString();
//	}
	
	@Override
	public String toString() {
		return id;
	}

	public KModel getSource() {
		return source;
	}

	public KModel getDestination() {
		return destination;
	}

	public KRelation getLink() {
		return link;
	}

}
