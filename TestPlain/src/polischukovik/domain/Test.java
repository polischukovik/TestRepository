package polischukovik.domain;

import java.util.List;

public class Test {
	private String caption;
	private List<Variant> variants;
	
	public Test(String caption, List<Variant> variants) {
		super();
		this.caption = caption;
		this.variants = variants;
	}

	public String getCaption() {
		return caption;
	}

	public List<Variant> getVariants() {
		return variants;
	}
	
	
}
